package com.app.githubuserappsub2.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuserappsub2.R
import com.app.githubuserappsub2.adapter.RecyclerViewFavAdapter
import com.app.githubuserappsub2.databinding.FragmentFavoriteBinding
import com.app.githubuserappsub2.viewmodel.DataBaseAndroidViewModel
import com.app.githubuserappsub2.viewmodel.FragmentModel


class FavoriteFragment : FragmentModel() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var recyclerViewFavAdapter: RecyclerViewFavAdapter
    private lateinit var viewModel: DataBaseAndroidViewModel

    override fun onDisconnected() {}

    override fun onConnected() {}

    override fun queryApi(q: String) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        binding = FragmentFavoriteBinding.bind(view)
        textViewInfo = binding.idTextEmptyFav
        progressBar = binding.idProgressFav

        viewModel = ViewModelProvider(this).get(DataBaseAndroidViewModel::class.java)
        recyclerViewFavAdapter = RecyclerViewFavAdapter()
        recyclerViewFavAdapter.notifyDataSetChanged()
        binding.idHomeRecyclerFav.apply {
            layoutManager = LinearLayoutManager(this@FavoriteFragment.context)
            adapter = recyclerViewFavAdapter
        }

        viewModel.selectData.observe(viewLifecycleOwner, {
            recyclerViewFavAdapter.setDataForAdapter(it)
        })

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {

        menu.findItem(R.id.fav_btn_detail).isVisible = false
        menu.findItem(R.id.setting_btn).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().invalidateOptionsMenu()
        super.onCreateOptionsMenu(menu, inflater)
    }

}