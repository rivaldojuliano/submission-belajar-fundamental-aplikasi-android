package com.rivzdev.githubuserapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rivzdev.githubuserapp.data.source.remote.ItemsItem
import com.rivzdev.githubuserapp.data.source.remote.UserResponse
import com.rivzdev.githubuserapp.data.source.remote.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel : ViewModel() {

    private val _items = MutableLiveData<List<ItemsItem>>()
    val items: LiveData<List<ItemsItem>> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getSearchUser(username)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _items.value = response.body()?.items
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, throwable: Throwable) {
                _isLoading.value = false

                Log.e(TAG, "onFailure: ${throwable.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "SearchUserViewModel"
    }
}