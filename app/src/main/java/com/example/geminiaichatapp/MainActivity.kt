package com.example.geminiaichatapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chatRecyclerView = findViewById<RecyclerView>(R.id.chatRecyclerView)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(mutableListOf())
        chatRecyclerView.adapter = chatAdapter

        val eTPrompt = findViewById<EditText>(R.id.chatInput)
        val btnSubmit = findViewById<ImageButton>(R.id.sendButton)

        btnSubmit.setOnClickListener {
            val prompt = eTPrompt.text.toString()

            chatAdapter.addMessage(ChatMessage(prompt, true))

            val generativeModel = GenerativeModel(
                modelName = "gemini-2.0-flash",
                apiKey = " " // ENTER YOUR KEY
            )

            runBlocking {
                val response = generativeModel.generateContent(prompt)
                chatAdapter.addMessage(ChatMessage(response.text ?: "No response", false))
            }

            eTPrompt.text.clear()
        }
    }
}
