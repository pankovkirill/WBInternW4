package com.example.wbinternw4.chats.mainRecycler

import androidx.recyclerview.widget.DiffUtil
import com.example.wbinternw4.data.Chat

class DiffUtilsCallBack(
    private val oldChatList: List<Chat>,
    private val newChatList: List<Chat>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldChatList.size

    override fun getNewListSize() = newChatList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldChatList[oldItemPosition].id == newChatList[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldChat = oldChatList[oldItemPosition]
        val newChat = newChatList[newItemPosition]
        return oldChat == newChat
    }
}