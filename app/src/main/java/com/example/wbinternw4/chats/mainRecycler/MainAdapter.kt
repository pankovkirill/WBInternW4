package com.example.wbinternw4.chats.mainRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wbinternw4.R
import com.example.wbinternw4.data.Chat

class MainAdapter(private val onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: ArrayList<Chat> = arrayListOf()

    fun setData(newData: List<Chat>) {
        val diffUtilsCallBack = DiffUtilsCallBack(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffUtilsCallBack)
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun updateData(chat: Chat) {
        data.add(0, chat)
        notifyItemInserted(0)
    }

    override fun getItemCount() = data.size

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Chat?) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.name).text = data?.name
                itemView.findViewById<TextView>(R.id.date).text = data?.date

                Glide.with(itemView)
                    .load(data?.avatar)
                    .into(itemView.findViewById(R.id.avatar))

                itemView.setOnClickListener { openInNewWindow(data!!) }
            }
        }

        private fun openInNewWindow(chat: Chat) {
            onListItemClickListener.onItemClick(chat)
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(chat: Chat)
    }
}
