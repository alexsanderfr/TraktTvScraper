package org.example.service

import java.net.URL

object TraktScraper {
    fun scrapeImg(string: String): String {
        val url = URL(string)
        val responseText = readApiUrlContent(url)
        val fanArtLinks = extractFanArtLinks(responseText)
        return getFanArtLink(fanArtLinks)
    }

    private fun readApiUrlContent(url: URL): String {
        return url.readText()
    }

    private fun extractFanArtLinks(text: String): List<String> {
        val matcher = "<section data-fanart=\"[^\"]*\"".toRegex()
        return matcher.findAll(text).map { it.value }.toList()
    }

    private fun getFanArtLink(links: List<String>): String {
        return links.toString().replace("[<section data-fanart=\"", "").replace("\"]", "")
    }
}