package com.example.wbinternw4.withoutRecycler

import android.os.Bundle
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wbinternw4.R
import com.example.wbinternw4.chats.MainViewModel
import com.example.wbinternw4.data.Chat
import com.example.wbinternw4.databinding.ActivityWithOutRecyclerBinding


class WithOutRecyclerActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityWithOutRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithOutRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.subscribe().observe(this) { setData(it) }
        viewModel.getData()

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            if (binding.scrollView.getChildAt(0).bottom <= (binding.scrollView.height + binding.scrollView.scrollY))
                viewModel.getData()
            binding.coordinates.text = binding.scrollView.getChildAt(0).bottom.toString()
        }
    }

    private fun setData(list: List<Chat>?) {
        for (chat in list!!) {
            val view = layoutInflater.inflate(R.layout.main_recycler_item, null, false)
            view.findViewById<TextView>(R.id.name).text = chat.name
            view.findViewById<TextView>(R.id.date).text = chat.date

            Glide.with(this)
                .load(chat.avatar)
                .into(view.findViewById(R.id.avatar))
            binding.containerForChats.addView(view)
        }

    }
}