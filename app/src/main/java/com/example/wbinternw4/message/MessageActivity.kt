package com.example.wbinternw4.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wbinternw4.R
import com.example.wbinternw4.data.MessageData
import com.example.wbinternw4.databinding.ActivityMessageBinding
import com.example.wbinternw4.message.messageRecycler.MessageAdapter

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding

    private val viewModel: MessageViewModel by lazy {
        ViewModelProvider(this).get(MessageViewModel::class.java)
    }

    private val adapter: MessageAdapter by lazy { MessageAdapter(onLongListItemClickListener) }

    private val onLongListItemClickListener: MessageAdapter.OnLongListItemClickListener =
        object : MessageAdapter.OnLongListItemClickListener {
            override fun onItemClick(
                view: View,
                list: ArrayList<MessageData>,
                layoutPosition: Int
            ): Boolean {
                showPopupMenu(view, list, layoutPosition)
                return true
            }
        }

    private fun showPopupMenu(
        view: View,
        list: ArrayList<MessageData>,
        layoutPosition: Int
    ) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.popup_menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.menuDelete -> {
                    list.removeAt(layoutPosition)
                    binding.activityMessageRecycler.adapter?.notifyItemRemoved(layoutPosition)
                }
            }
            true
        }
        popupMenu.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.extras?.get("Name").toString()
        supportActionBar?.title = name

        setupViewModel()
        setupView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.updateData()
            binding.activityMessageRecycler.smoothScrollToPosition(0)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupViewModel() {
        viewModel.subscribe().observe(this) { adapter.setData(it) }
        viewModel.getData()
    }

    private fun setupView() {
        binding.activityMessageRecycler.layoutManager = LinearLayoutManager(this)
        binding.activityMessageRecycler.adapter = adapter

        val layoutManager = binding.activityMessageRecycler.layoutManager as LinearLayoutManager

        binding.activityMessageRecycler.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
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
}