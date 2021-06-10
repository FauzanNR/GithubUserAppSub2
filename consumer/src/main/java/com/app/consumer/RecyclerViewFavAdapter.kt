package com.app.consumer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.consumer.databinding.ItemLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecyclerViewFavAdapter : RecyclerView.Adapter<RecyclerViewFavAdapter.RecViewHolder>() {

    private val listData = ArrayList<GitUserFav>()

    inner class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemLayoutBinding.bind(itemView)

        fun bindData(githubUser: GitUserFav) {
            binding.apply {
                idNameItem.text = githubUser.name
                idCompanyItem.text = githubUser.company
                idLocationItem.text = githubUser.location
                Glide.with(itemView).load(githubUser.avatar_url)
                    .apply(RequestOptions())
                    .into(idImageItem)
            }
        }
    }

    fun setDataForAdapter(data: List<GitUserFav>) {
        listData.clear()
        listData.addAll(data)
        Log.d("Adapter list", listData.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewFavAdapter.RecViewHolder =
        RecViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerViewFavAdapter.RecViewHolder, position: Int) {
        val data = listData[position]

        holder.bindData(data)
    }

    override fun getItemCount(): Int = listData.size

}