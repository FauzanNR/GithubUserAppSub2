package com.app.githubuserappsub2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuserappsub2.R
import com.app.githubuserappsub2.databinding.FragmentFollowerBinding
import com.app.githubuserappsub2.viewmodel.FragmentModel
import com.app.githubuserappsub2.viewmodel.IFollowFragmentData


class FollowerFragment : FragmentModel(), IFollowFragmentData {

    private lateinit var binding: FragmentFollowerBinding

    companion object {
        lateinit var userName: String
    }


    override fun onDisconnected() {
        isConnected = false
        binding.idRecyclerFollower.visibility = View.GONE
        textViewInfo.visibility = View.VISIBLE
    }

    override fun onConnected() {
        queryApi(userName)
    }

    override fun onResume() {
        super.onResume()
        queryApi(userName)
    }

    override fun queryApi(q: String) {
        if (isConnected) {
            progressBar.visibility = View.VISIBLE
            setViewModel.setResponseLiveDataFollow(q, "followers")
                .observe(viewLifecycleOwner, {
                    if (it.isSuccessful) {
                        it.body()?.let { itbody -> recyclerViewAdapter.setDataForAdapter(itbody) }
                        binding.idRecyclerFollower.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    } else {
                        Log.d("FOLLOWER", it.body().toString())
                    }
                })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_follower, container, false)
        binding = FragmentFollowerBinding.bind(view)

        textViewInfo = binding.idTextEmptyFollower
        progressBar = binding.idProgressFollower
        binding.apply {
            idRecyclerFollower.layoutManager = LinearLayoutManager(view.context)
            idRecyclerFollower.adapter = recyclerViewAdapter
        }

        return view
    }

    override fun setData(s: String) {
        userName = s
    }
}