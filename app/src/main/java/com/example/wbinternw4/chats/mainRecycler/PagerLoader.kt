package com.example.wbinternw4.chats.mainRecycler

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wbinternw4.data.Chat
import com.example.wbinternw4.data.Repository

class PagerLoader(
    private val repository: Repository
) : PagingSource<Int, Chat>() {
    override fun getRefreshKey(state: PagingState<Int, Chat>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chat> {
        val pageIndex = params.key ?: 1
        val pageSize = params.loadSize
        val chats = repository.getData()
        val nextKey = if (chats.size < pageSize) null else pageIndex + 1
        val prevKey = if (pageIndex == 1) null else pageIndex - 1
        return LoadResult.Page(chats, prevKey, nextKey)
    }
}