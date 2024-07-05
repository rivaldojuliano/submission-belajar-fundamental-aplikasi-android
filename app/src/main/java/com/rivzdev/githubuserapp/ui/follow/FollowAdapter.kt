package com.rivzdev.githubuserapp.ui.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rivzdev.githubuserapp.data.source.remote.ItemsItem
import com.rivzdev.githubuserapp.databinding.ItemUserRowBinding

class FollowAdapter : ListAdapter<ItemsItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemUserRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .circleCrop()
                .apply(RequestOptions().override(100,100))
                .into(binding.ivProfile)

            binding.tvUsername.text = item.login.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowAdapter.MyViewHolder {
        val binding = ItemUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowAdapter.MyViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}