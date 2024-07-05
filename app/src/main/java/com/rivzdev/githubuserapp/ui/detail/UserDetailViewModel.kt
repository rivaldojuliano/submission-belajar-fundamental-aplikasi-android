package com.rivzdev.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rivzdev.githubuserapp.data.source.remote.ItemsItem
import com.rivzdev.githubuserapp.data.source.remote.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<ItemsItem>()
    val detailUser: LiveData<ItemsItem> = _detailUser

    fun getDetailUser(username: String) {
        val client = ApiConfig.getApiService().getUserDetail(username)

        client.enqueue(object : Callback<ItemsItem> {
            override fun onResponse(call: Call<ItemsItem>, response: Response<ItemsItem>) {
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ItemsItem>, throwable: Throwable) {
                Log.e(TAG, "onFailure: ${throwable.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "UserDetailViewModel"
    }
}