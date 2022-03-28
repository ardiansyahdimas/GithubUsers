package com.dicoding.githubusers.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubusers.data.User
import com.dicoding.githubusers.databinding.ItemRowUserBinding


class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    var listUser = arrayListOf<User>()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        listUser.clear()
        listUser.addAll(value)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataUser: User) {
            with(binding){
                var imgAvatar:Any=dataUser.avatar
                if (dataUser.avatar.isEmpty()){
                    imgAvatar= dataUser.avatarOf
                }
                Glide.with(itemView.context)
                    .load(imgAvatar)
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemName.text = dataUser.name
                tvUsername.text = dataUser.username

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(dataUser)}
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