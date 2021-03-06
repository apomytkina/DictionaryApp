package com.example.dictionaryapp.ui

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.R
import com.example.dictionaryapp.adapters.listAdapter.ListDefAdapter
import com.example.dictionaryapp.databinding.FragmentListBinding
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.broadcastreceivers.AlarmReceiver
import com.example.dictionaryapp.util.Constants.Companion.HOUR_FOR_NOTIFICATION
import com.example.dictionaryapp.util.Constants.Companion.INFO_BUNDLE_ID
import com.example.dictionaryapp.viewmodel.DictionaryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ListFragment : Fragment() {
    private var pendingIntent: PendingIntent? = null
    private var alarmManager: AlarmManager? = null
    private val calendar = Calendar.getInstance()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var listDefAdapter: ListDefAdapter
    private val viewModel: DictionaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        createNotificationChannel()
        setAlarm()

        listDefAdapter = ListDefAdapter(
            object : ListDefAdapter.OnItemClickListener {
                override fun onItemClick(def: Def) {
                    val bundle = Bundle()
                    bundle.putStringArray(INFO_BUNDLE_ID, parseDefToArray(def))
                    val wordFragment = WordFragment()
                    wordFragment.arguments = bundle
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.listFragment, wordFragment)
                        commit()
                    }
                }
            },
            object : ListDefAdapter.OnItemClickListener {
                override fun onItemClick(def: Def) {
                    viewModel.deleteWord(def)
                    Toast.makeText(context, "Word ${def.text} is deleted from learning list", Toast.LENGTH_SHORT).show()
                }
            }
//            object : ListDefAdapter.OnItemClickListener {
//                override fun onItemClick(def: Def) {
//                    Toast.makeText(context, "NotificationBroadcastReceiver is set", Toast.LENGTH_SHORT)
//                    viewModel.updateNotificationStatus(true, def.id)
//                }
//            }
        )

        setUpRecyclerView(listDefAdapter)

        viewModel.getAllWords().observe(viewLifecycleOwner, { words ->
            binding.apply {
                listDefAdapter.submitList(words)
            }
        })

        viewModel.getSize().observe(viewLifecycleOwner, { size ->
            binding.learningWordsTv.text = "Count of learning words: $size"
        })

        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpRecyclerView(listDefAdapter: ListDefAdapter){
        binding.listRv.apply {
            adapter = listDefAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun parseDefToArray(def: Def): Array<String>{
        var translation = "No Translation"
        var example = "No Example"
        var partOfSpeech = ""
        var transcription = "No Transcription"
        var word = def.text
        if (!def.pos.isNullOrEmpty())
            partOfSpeech = def.pos
        if (!def.ts.isNullOrEmpty())
            transcription = def.ts
        if (!def.tr.isNullOrEmpty())
            translation = def.tr[0].text
        if (!def.tr[0].ex.isNullOrEmpty())
            example = def.tr[0].ex[0].text
        return arrayOf(word, partOfSpeech, transcription, translation, example)
    }

    private fun createNotificationChannel(){
       if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
           val name: CharSequence = "ReminderChannel"
           val description = "Channel For Alarm Manager"
           val importance = NotificationManager.IMPORTANCE_HIGH
           val channel = NotificationChannel("channelID", name, importance)
           channel.description = description
           val notificationManager = context?.getSystemService(NotificationManager::class.java)
           notificationManager?.createNotificationChannel(channel)
       }
    }

    private fun setAlarm(){
        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        calendar[Calendar.HOUR_OF_DAY] = HOUR_FOR_NOTIFICATION
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun cancelAlarm(){
        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        alarmManager!!.cancel(pendingIntent)
        Toast.makeText(context, "Alarm is canceled!", Toast.LENGTH_LONG).show()
    }
}