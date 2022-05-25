package com.example.wbinternw4.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wbinternw4.data.CHATS
import com.example.wbinternw4.data.Chat
import com.example.wbinternw4.data.Repository

class MainViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {

    private val _data = MutableLiveData<List<Chat>>()

    private val liveDataForViewToObserve: LiveData<List<Chat>> = _data

    fun subscribe() = liveDataForViewToObserve

    fun getData() {
        _data.postValue(repository.getData())
    }

    fun updateData(): Chat {
        val i = (0..11).random()
        return CHATS[i]
    }
}