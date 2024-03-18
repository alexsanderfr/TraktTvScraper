package org.example

import java.net.URL

fun main() {
    val apiResponse = URL("https://trakt.tv/shows/breaking-bad").readText()
    val matcher = "<section data-fanart=\"[^\"]*\"".toRegex()
    val regexFind = matcher.findAll(apiResponse).map{it.value}.toList().toString()
    val string = regexFind.replace("[<section data-fanart=\"", "").replace("\"]", "")
    println(string)
}