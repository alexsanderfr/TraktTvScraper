package org.example

import java.net.URL

fun main() {
    val url = URL("https://trakt.tv/shows/breaking-bad")
    val bannerImg = scrapeImg(url)
    println(bannerImg)
}

fun scrapeImg(url: URL): String {
    val apiResponse = url.readText()
    val matcher = "<section data-fanart=\"[^\"]*\"".toRegex()
    val regexFind = matcher.findAll(apiResponse).map{it.value}.toList().toString()
    val img = regexFind.replace("[<section data-fanart=\"", "").replace("\"]", "")
    return img
}