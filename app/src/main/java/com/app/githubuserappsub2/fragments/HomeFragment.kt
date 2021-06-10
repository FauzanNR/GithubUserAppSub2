package com.app.githubuserappsub2.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuserappsub2.R
import com.app.githubuserappsub2.databinding.FragmentHomeBinding
import com.app.githubuserappsub2.viewmodel.FragmentModel
import java.util.*

class HomeFragment : FragmentModel(), TextWatcher {

    private lateinit var binding: FragmentHomeBinding
    var isText = true

    override fun onDisconnected() {
        textViewInfo.visibility = View.VISIBLE
        textViewInfo.text = "No Connection!"
        Toast.makeText(binding.root.context, R.string.no_connection, Toast.LENGTH_SHORT).show()
        isConnected = false
    }

    override fun onConnected() {
        textViewInfo.text = "Find someone!"
        isConnected = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)

        textViewInfo = binding.idTextEmpty
        progressBar = binding.idProgress
        binding.apply {
            idSearchView.addTextChangedListener(this@HomeFragment)
            idHomeRecycler.layoutManager = LinearLayoutManager(view.context)
            idHomeRecycler.adapter = recyclerViewAdapter
        }
        return view
    }

    override fun queryApi(q: String) {
        if (isConnected) {
            setViewModel.getDataSearch().observe(viewLifecycleOwner, {
                if (it.isSuccessful) {
                    if (it.body()?.total_count == 0) {
                        binding.idHomeRecycler.visibility = View.INVISIBLE
                        textViewInfo.text = "Not Found!"
                        textViewInfo.visibility = View.VISIBLE
                    } else {
                        it.body()?.let { it1 ->
                            recyclerViewAdapter.setDataForAdapter(it1.items)
                        }
                        textViewInfo.text = "Find someone!"
                    }
                    progressBar.visibility = View.GONE
                } else {
                    Log.d("ERROR_M", it.body().toString())
                }
            })
        }
    }

    var timer = Timer()
    override fun afterTextChanged(s: Editable?) {
        val b = s.isNullOrBlank()
        if (!b and isConnected) {
//            textViewInfo.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
//          waiting input finish in background thread, fix skipping frame, to much work on main thread
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    setViewModel.setResponseLiveDataSearch(s.toString())
                }
            }, 500L)
//            call observer. observer only can be call on main thread
            queryApi(s.toString())
            textViewInfo.visibility = View.GONE
            binding.idHomeRecycler.visibility = View.VISIBLE
            Log.d("search", s.toString())
        } else {
            binding.idHomeRecycler.visibility = View.GONE
            textViewInfo.visibility = View.VISIBLE
            isTextEmpty(b)
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        timer.cancel()
        timer.purge()
    }

    private fun isTextEmpty(b: Boolean) {
        isText = b
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

}