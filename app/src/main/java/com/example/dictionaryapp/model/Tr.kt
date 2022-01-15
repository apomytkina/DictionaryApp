package com.example.dictionaryapp.model

data class Tr(
    val asp: String,
    val ex: List<Ex>,
    val fr: Int,
    val gen: String,
    val mean: List<Mean>,
    val pos: String,
    val syn: List<Syn>,
    val text: String
)