package com.example.cinema.adapter

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cinema.databinding.ItemChatMessageBinding

import com.example.cinema.model.ChatMessage;

import kotlin.collections.List;

class ChatBotChatMessageAdapter(
    private val mListChatMessages: List<ChatMessage>?,
    private val iChatMessagesListener: IChatMessagesListener
) : RecyclerView.Adapter<ChatBotChatMessageAdapter.ChatBotChatMessageViewHolder>() {
    interface IChatMessagesListener {
        fun onClickItem(chatMessage: ChatMessage?)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatBotChatMessageViewHolder {
        val mItemChatMessageBinding =
            ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatBotChatMessageViewHolder(mItemChatMessageBinding)
    }

    override fun onBindViewHolder(
        holder: ChatBotChatMessageViewHolder,
        position: Int
    ) {
        val chatMessage = mListChatMessages!![position];
        holder.mItemChatMessageBinding.chatMessage = chatMessage;
        holder.mItemChatMessageBinding.layoutChatMessage.setOnClickListener {
            iChatMessagesListener.onClickItem(
                chatMessage
            )
        }
    }


    override fun getItemCount(): Int {
        return mListChatMessages?.size ?: 0
    }

    class ChatBotChatMessageViewHolder(val mItemChatMessageBinding: ItemChatMessageBinding) :
        RecyclerView.ViewHolder(mItemChatMessageBinding.root)
}