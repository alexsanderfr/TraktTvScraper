package org.example

import java.awt.*
import java.net.URL
import java.awt.datatransfer.StringSelection
import javax.swing.*

fun main() {
    SwingUtilities.invokeLater { createUI() }
}

fun scrapeImg(url: URL): String {
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

fun copyToClipboard(text: String) {
    val selection = StringSelection(text)
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(selection, selection)
}


fun createUI() {
    val frame = JFrame("Image Scraper")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(600, 300)

    val panel = JPanel()
    panel.layout = GridBagLayout()
    panel.border = BorderFactory.createEmptyBorder(30, 30, 30, 30)
    panel.background = Color(210, 210, 210)

    val c = GridBagConstraints()
    c.fill = GridBagConstraints.HORIZONTAL
    c.insets = Insets(10, 10, 10, 10)

    val customFont = Font("Serif", Font.PLAIN, 16)

    val inputHintLabel = JLabel("Please enter a URL: ")
    inputHintLabel.font = customFont
    c.gridx = 0
    c.gridy = 0
    c.weightx = 0.2
    panel.add(inputHintLabel, c)

    val inputField = JTextField()
    inputField.font = customFont
    c.gridx = 1
    c.gridy = 0
    c.weightx = 0.8
    panel.add(inputField, c)

    val button = JButton("Scrape Image URL!")
    button.font = Font("SansSerif", Font.BOLD, 12)
    button.background = Color(130, 180, 210)
    c.gridx = 0
    c.gridy = 1
    c.gridwidth = 2
    panel.add(button, c)

    val resultLabel = JLabel()
    resultLabel.font = customFont
    c.gridx = 0
    c.gridy = 2
    c.gridwidth = 2
    panel.add(resultLabel, c)

    frame.contentPane = panel

    button.addActionListener {
        val userInput = inputField.text
        try {
            val url = URL(userInput)
            val scrapedImgUrl = scrapeImg(url)
            copyToClipboard(scrapedImgUrl)
            resultLabel.text = "<html>Scraped Image URL: <br/><font color='blue'>$scrapedImgUrl</font></html>"
        } catch (e: Exception) {
            resultLabel.text = "Error: Invalid URL or Unable to Scrape Image"
            resultLabel.foreground = Color.RED
        }
    }

    frame.isVisible = true
}