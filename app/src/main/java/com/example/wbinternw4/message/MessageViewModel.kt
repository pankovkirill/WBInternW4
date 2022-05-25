package com.example.wbinternw4.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wbinternw4.data.MessageData
import com.example.wbinternw4.data.Repository

class MessageViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {

    private val _data = MutableLiveData<ArrayList<MessageData>>()

    private val liveDataForViewToObserve: LiveData<ArrayList<MessageData>> = _data

    fun subscribe() = liveDataForViewToObserve

    fun getData() {
        _data.postValue(repository.getMessage())
    }
}