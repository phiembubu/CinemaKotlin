package com.example.cinema.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.MyApplication
import com.example.cinema.adapter.ChatBotChatMessageAdapter
import com.example.cinema.databinding.ActivityChatBotGptBinding
import com.example.cinema.model.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ChatBotGPT : AppCompatActivity() {
    private var binding: ActivityChatBotGptBinding? = null

    private var mPromptMessages: MutableList<String>? = null

    private var mListChatMessages: MutableList<ChatMessage>? = null

    private var chatAdapter: ChatBotChatMessageAdapter? = null

    var client: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotGptBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initListener()
        getListChatMessages()
    }

    private fun initListener() {
        mListChatMessages = mutableListOf()
        mPromptMessages = mutableListOf()

        chatAdapter = ChatBotChatMessageAdapter(
            mListChatMessages!!,
            object : ChatBotChatMessageAdapter.IChatMessagesListener {
                override fun onClickItem(chatMessage: ChatMessage?) {

                }
            })

        binding?.recyclerViewChat?.apply {
            layoutManager = LinearLayoutManager(this@ChatBotGPT)
            adapter = chatAdapter
        }

        binding?.imageButtonSend?.setOnClickListener {

            sendMessage()
        }
    }

    private fun sendMessage() {
        val messageText = binding?.editTextTextMessage?.text.toString().trim()

        if (messageText.isEmpty()) {
            Toast.makeText(this, "Tin nhắn không được để trống", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show()
            return
        }

        val chatRef = MyApplication[this].getChatMessageDatabaseReference()

        if (chatRef == null) {
            Toast.makeText(this, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show()
            return
        }

        val messageId = chatRef.push().key
        Toast.makeText(this, "UID: " + currentUser.uid, Toast.LENGTH_SHORT).show()
        if (messageId != null) {
            val userMessage = ChatMessage(
                messageID = messageId,
                senderID = currentUser.uid,
                text = messageText,
                timeSent = System.currentTimeMillis()
            )

            chatRef.child(messageId).setValue(userMessage)
                .addOnSuccessListener {
                    binding?.editTextTextMessage?.setText("")
                    Log.d("ChatBotGPT", "Tin nhắn gửi thành công: $messageText")

                    getResponse(messageText) { response ->
                        runOnUiThread {
                            sendBotMessage(response)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("ChatBotGPT", "Lỗi gửi tin nhắn", e)
                    Toast.makeText(this, "Gửi tin nhắn thất bại", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun sendBotMessage(botResponse: String) {
        val chatRef = MyApplication[this].getChatMessageDatabaseReference()
        val botMessageId = chatRef?.push()?.key

        if (botMessageId != null) {
            val botMessage = ChatMessage(
                messageID = botMessageId,
                senderID = "ChatBotGPT", // Định danh bot
                text = botResponse,
                timeSent = System.currentTimeMillis()
            )

            chatRef.child(botMessageId).setValue(botMessage)
                .addOnSuccessListener {
                    Log.d("ChatBotGPT", "Phản hồi GPT đã được gửi lên Firebase: $botResponse")
                }
                .addOnFailureListener { e ->
                    Log.e("ChatBotGPT", "Lỗi gửi phản hồi GPT", e)
                }
        }
    }


    private fun getListChatMessages() {
        val chatRef = MyApplication[this].getChatMessageDatabaseReference()

        if (chatRef == null) {
            Toast.makeText(this, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show()
            return
        }

        chatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mListChatMessages?.clear()
                if (mListChatMessages == null) {
                    mListChatMessages = mutableListOf()
                }

                for (childSnapshot in snapshot.children) {
                    val chatMessage = childSnapshot.getValue(ChatMessage::class.java)
                    chatMessage?.let { mListChatMessages?.add(it) }
                }

                binding?.recyclerViewChat?.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatBotGPT, "Lỗi tải tin nhắn!", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun getResponse(question: String, callback: (String) -> Unit) {
        val url = "https://chatbot.codetifytech.io.vn/ask"

        val requestBody = JSONObject().apply {
            put("question", question) // Pass only a message
        }.toString()

        val request = Request.Builder()
            .url(url)
            .post(requestBody.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback("Lỗi khi gọi API!")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { responseBody ->
                    try {
                        val jsonResponse = JSONObject(responseBody)
                        val reply = jsonResponse.optString("answer", "Không có phản hồi từ máy chủ!")

                        callback(reply)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        callback("Lỗi khi xử lý dữ liệu từ API!")
                    }
                }
            }
        })

    }
}