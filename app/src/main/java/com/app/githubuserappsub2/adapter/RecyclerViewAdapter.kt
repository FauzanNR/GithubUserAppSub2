package com.app.githubuserappsub2.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.githubuserappsub2.R
import com.app.githubuserappsub2.databinding.ItemLayoutBinding
import com.app.githubuserappsub2.datamodel.GithubUser
import com.app.githubuserappsub2.fragments.DetailUserFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.RecViewHolder>() {

    private val listData = ArrayList<GithubUser>()


    inner class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemLayoutBinding.bind(itemView)

        fun bindData(githubUser: GithubUser) {
            binding.apply {
                idNameItem.text = githubUser.login
                Glide.with(itemView).load(githubUser.avatar_url)
                    .apply(RequestOptions())
                    .into(idImageItem)
            }

        }
    }


    fun setDataForAdapter(data: List<GithubUser>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.RecViewHolder =
        RecViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerViewAdapter.RecViewHolder, position: Int) {
        val data = listData[position]
        holder.bindData(data)

        holder.binding.idItemLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(DetailUserFragment.LOGIN_NAME, data.login)
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.detailUserFragment, bundle)
        }
    }

    override fun getItemCount(): Int = listData.size

}