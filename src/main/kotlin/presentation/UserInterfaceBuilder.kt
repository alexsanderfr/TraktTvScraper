package org.example.presentation

import org.example.service.TraktScraper
import org.example.enums.ScraperType
import org.example.service.FanArtScraper
import java.awt.*
import java.awt.datatransfer.StringSelection
import javax.swing.*

class UserInterfaceBuilder {

    fun build(scraperType: ScraperType) {
        SwingUtilities.invokeLater { createUI(scraperType) }
    }

    private fun copyToClipboard(text: String) {
        val selection = StringSelection(text)
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(selection, selection)
    }


    private fun createUI(scraperType: ScraperType) {
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

        val inputUrlField = JTextField()
        val inputApiKeyField = JTextField()
        val inputIdField = JTextField()
        when (scraperType) {
            ScraperType.TRAKT -> {
                val inputUrlLabel = JLabel("Please enter a URL: ")
                inputUrlLabel.font = customFont
                c.gridx = 0
                c.gridy = 0
                c.weightx = 0.2
                panel.add(inputUrlLabel, c)

                inputUrlField.font = customFont
                c.gridx = 1
                c.gridy = 0
                c.weightx = 0.8
                panel.add(inputUrlField, c)
            }

            ScraperType.FAN_ART -> {
                val inputApiKeyLabel = JLabel("Please enter an API KEY: ")
                inputApiKeyLabel.font = customFont
                c.gridx = 0
                c.gridy = 0
                c.weightx = 0.2
                panel.add(inputApiKeyLabel, c)

                inputApiKeyField.font = customFont
                c.gridx = 1
                c.gridy = 0
                c.weightx = 0.8
                panel.add(inputApiKeyField, c)

                val inputIdLabel = JLabel("Please enter an id: ")
                inputIdLabel.font = customFont
                c.gridx = 0
                c.gridy = 1
                c.weightx = 0.2
                panel.add(inputIdLabel, c)

                inputIdField.font = customFont
                c.gridx = 1
                c.gridy = 1
                c.weightx = 0.8
                panel.add(inputIdField, c)
            }
        }

        val button = JButton("Scrape Image URL!")
        button.font = Font("SansSerif", Font.BOLD, 12)
        button.background = Color(130, 180, 210)
        c.gridx = 0
        c.gridy = 2
        c.gridwidth = 2
        panel.add(button, c)

        val resultLabel = JLabel()
        resultLabel.font = customFont
        c.gridx = 0
        c.gridy = 3
        c.gridwidth = 2
        panel.add(resultLabel, c)

        frame.contentPane = panel

        button.addActionListener {

            try {
                val scrapedImgUrl = when (scraperType) {
                    ScraperType.TRAKT -> {

                        val userInput = inputUrlField.text
                        TraktScraper.scrapeImg(userInput)
                    }

                    ScraperType.FAN_ART -> {
                        val apiKey = inputApiKeyField.text
                        val id = inputIdField.text
                        FanArtScraper.scrapeImgFromFanArt(id, apiKey)
                    }

                }
                copyToClipboard(scrapedImgUrl)
                resultLabel.text = "<html>Scraped Image URL: <br/><font color='blue'>$scrapedImgUrl</font></html>"
            } catch (e: Exception) {
                resultLabel.text = "Error: Invalid URL or Unable to Scrape Image"
                resultLabel.foreground = Color.RED
            }
        }

        frame.isVisible = true
    }
}