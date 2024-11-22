package com.example.doubtsolvereasylearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val promptTXT = findViewById<EditText>(R.id.prompt)
        val btnSub = findViewById<Button>(R.id.submit)
        val output = findViewById<TextView>(R.id.Output)

        btnSub.setOnClickListener {
            val prompt = """
        You are an expert academic assistant. Explain the topic "${promptTXT.text.toString()}" to a student in simple and clear terms, assuming they are at a college level. Address common doubts or misconceptions and provide step-by-step explanations if the concept involves calculations or technical terms. Keep your response concise yet informative, and use examples or analogies where appropriate.

        Format the response with:
        - Bold headers for key sections
        - Numbered lists instead of bullet points where applicable
        - Code snippets enclosed in backticks (```) for technical terms or examples
        - Line breaks between sections for readability
    """.trimIndent()

            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = "AIzaSyBCzm52HTAHrfgxgFaGqw42e5AGLRYfkFE"
            )

            runBlocking {
                val response = generativeModel.generateContent(prompt)
                var rawText = response.text.toString()

                // Replace bullet points with numbered lists
                var counter = 1
                rawText = rawText.replace(Regex("•")) { "${counter++}. " }

                // Other formatting if necessary
                val formattedText = rawText
                    .replace("**", "") // Remove redundant Markdown symbols
                    .replace("```", "\n") // Ensure code snippets are separated cleanly

                output.text = formattedText
            }
        }


    }
}