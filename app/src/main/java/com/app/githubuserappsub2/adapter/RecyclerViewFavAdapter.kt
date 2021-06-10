package com.app.githubuserappsub2.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.githubuserappsub2.R
import com.app.githubuserappsub2.databinding.ItemLayoutBinding
import com.app.githubuserappsub2.fragments.DetailUserFragment
import com.app.githubuserappsub2.roomdb.GitUserDao
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RecyclerViewFavAdapter : RecyclerView.Adapter<RecyclerViewFavAdapter.RecViewHolder>() {

    private val listData = ArrayList<GitUserDao>()


    inner class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemLayoutBinding.bind(itemView)

        fun bindData(githubUser: GitUserDao) {
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


    fun setDataForAdapter(data: List<GitUserDao>) {
        listData.clear()
        listData.addAll(data)
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

        holder.binding.idItemLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(DetailUserFragment.LOGIN_NAME, data.login)

//            val mDetail = DetailUserFragment.newInstance(data.login.toString())
//            holder.itemView.findFragment<DetailUserFragment>().childFragmentManager
//                .beginTransaction().apply {
//                    add(
//                        holder.itemViewType,
//                        mDetail,
//                        DetailUserFragment::class.java.simpleName
//                    )
//                    addToBackStack(null)
//                    commit()
//                }

            Navigation.findNavController(holder.itemView)
                .navigate(R.id.detailUserFragment, bundle)
        }
    }

    override fun getItemCount(): Int = listData.size

}