package org.example.model

data class Image(
    val id: String,
    val url: String,
    val lang: String,
    val likes: String,
    val season: String? = null
)