package com.example.wbinternw4.message.messageRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wbinternw4.R
import com.example.wbinternw4.data.Chat
import com.example.wbinternw4.data.MessageData
import com.github.javafaker.Faker

class MessageAdapter(private val onLongListItemClickListener: OnLongListItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: ArrayList<MessageData> = arrayListOf()

    fun setData(data: ArrayList<MessageData>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_SENDER) {
            SenderViewHolder(
                inflater.inflate(R.layout.sender_recycler_item, parent, false) as View
            )
        } else {
            RecipientViewHolder(
                inflater.inflate(R.layout.recipient_recycler_item, parent, false) as View
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_SENDER) {
            holder as SenderViewHolder
            holder.bind(data[position])
        } else {
            holder as RecipientViewHolder
            holder.bind(data[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].type == 0) TYPE_SENDER else TYPE_RECIPIENT
    }

    override fun getItemCount() = data.size

    inner class SenderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(messageData: MessageData) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.message).text = messageData.message

                itemView.setOnLongClickListener {
                    onLongListItemClickListener.onItemClick(
                        itemView,
                        data,
                        layoutPosition
                    )
                }
            }
        }
    }

    inner class RecipientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(messageData: MessageData) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.message).text = messageData.message

                itemView.setOnLongClickListener {
                    onLongListItemClickListener.onItemClick(
                        itemView,
                        data,
                        layoutPosition
                    )
                }
            }
        }
    }

    fun updateData() {
        val faker = Faker.instance()
        val newData = MessageData(
            (0..100).random(),
            (0..1).random(),
            faker.company().name()
        )
        data.add(0, newData)
        notifyItemInserted(0)
    }


    interface OnLongListItemClickListener {
        fun onItemClick(
            messageData: View,
            data: ArrayList<MessageData>,
            layoutPosition: Int
        ): Boolean
    }

    companion object {
        private const val TYPE_SENDER = 0
        private const val TYPE_RECIPIENT = 1
    }
}