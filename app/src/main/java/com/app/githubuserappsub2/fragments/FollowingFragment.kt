package com.app.githubuserappsub2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuserappsub2.R
import com.app.githubuserappsub2.databinding.FragmentFollowingBinding
import com.app.githubuserappsub2.viewmodel.FragmentModel
import com.app.githubuserappsub2.viewmodel.IFollowFragmentData

class FollowingFragment : FragmentModel(), IFollowFragmentData {

    private lateinit var binding: FragmentFollowingBinding

    override fun onDisconnected() {
        isConnected = false
        binding.idRecyclerFollowing.visibility = View.GONE
        textViewInfo.visibility = View.VISIBLE
    }

    override fun onConnected() {
        queryApi(FollowerFragment.userName)
    }

    override fun onResume() {
        super.onResume()
        queryApi(FollowerFragment.userName)
    }

    override fun queryApi(q: String) {
        if (isConnected) {
            progressBar.visibility = View.VISIBLE
            setViewModel.setResponseLiveDataFollow(q, "following")
                .observe(viewLifecycleOwner, {
                    if (it.isSuccessful) {
                        it.body()?.let { itbody -> recyclerViewAdapter.setDataForAdapter(itbody) }
                        binding.idRecyclerFollowing.visibility = View.VISIBLE
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
        val view = inflater.inflate(R.layout.fragment_following, container, false)
        binding = FragmentFollowingBinding.bind(view)

        textViewInfo = binding.idTextEmptyFollowing
        progressBar = binding.idProgressFollowing
        binding.apply {
            idRecyclerFollowing.layoutManager = LinearLayoutManager(view.context)
            idRecyclerFollowing.adapter = recyclerViewAdapter
        }

        return view
    }

    override fun setData(s: String) {
        FollowerFragment.userName = s
    }

    companion object {
        lateinit var userName: String
    }
}