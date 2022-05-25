package com.example.wbinternw4.chats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wbinternw4.message.MessageActivity
import com.example.wbinternw4.data.Chat
import com.example.wbinternw4.databinding.ActivityMainBinding
import com.example.wbinternw4.chats.mainRecycler.MainAdapter
import com.example.wbinternw4.withoutRecycler.WithOutRecyclerActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(chat: Chat) {
                startActivity(
                    Intent(
                        this@MainActivity,
                        MessageActivity::class.java
                    ).putExtra("Name", chat.name)
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupList()


        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.updateData(viewModel.updateData())
            binding.activityMainRecycler.smoothScrollToPosition(0)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.open.setOnClickListener {
            startActivity(Intent(this, WithOutRecyclerActivity::class.java))
        }
    }

    private fun setupList() {
        binding.activityMainRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.activityMainRecycler.adapter = adapter

        val layoutManager = binding.activityMainRecycler.layoutManager as LinearLayoutManager

        binding.activityMainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount <=
                    lastVisibleItem + 2
                ) {
                    viewModel.getData()
                }
            }
        })
    }


    private fun setupViewModel() {
        viewModel.subscribe().observe(this) { adapter.setData(it) }
        viewModel.getData()
    }
}