package org.example.service

import com.google.gson.Gson
import org.example.model.FanArtResponse
import java.net.HttpURLConnection
import java.net.URL

object FanArtScraper {

    private fun fetchFanArtData(id: String, apiKey: String): FanArtResponse {
        val url = URL("http://webservice.fanart.tv/v3/tv/$id&api_key=$apiKey")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        return connection.inputStream.use { inputStream ->
            val responseText = inputStream.bufferedReader().readText()
            Gson().fromJson(responseText, FanArtResponse::class.java)
        }
    }

    fun scrapeImgFromFanArt(id: String, apiKey: String): String {
        return try {
            val fanArtData = fetchFanArtData(id, apiKey)
            println(fanArtData)
            fanArtData.background.first().url
        } catch (e: Exception) {
            ("Error fetching data: ${e.message}")
        }
    }
}