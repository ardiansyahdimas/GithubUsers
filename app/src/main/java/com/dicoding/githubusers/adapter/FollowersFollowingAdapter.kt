package com.dicoding.githubusers.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubusers.data.User
import com.dicoding.githubusers.databinding.ItemFollowersFollowingBinding

class FollowersFollowingAdapter: RecyclerView.Adapter<FollowersFollowingAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    var listUser = arrayListOf<User>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            listUser.clear()
            listUser.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemFollowersFollowingBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private val binding: ItemFollowersFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataUser: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataUser.avatar)
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemName.text = dataUser.name
                tvUsername.text = dataUser.username

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(dataUser) }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}