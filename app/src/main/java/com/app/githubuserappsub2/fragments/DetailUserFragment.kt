package com.app.githubuserappsub2.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.githubuserappsub2.R
import com.app.githubuserappsub2.databinding.FragmentDetailUserBinding
import com.app.githubuserappsub2.roomdb.GitUserDao
import com.app.githubuserappsub2.viewmodel.DataBaseAndroidViewModel
import com.app.githubuserappsub2.viewmodel.FragmentModel
import com.app.githubuserappsub2.viewmodel.IFollowFragmentData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class DetailUserFragment : FragmentModel(), View.OnClickListener, IFollowFragmentData {

    private var loginName: String? = null
    private lateinit var binding: FragmentDetailUserBinding
    private lateinit var androidViewModel: DataBaseAndroidViewModel
    lateinit var gitUserDao: GitUserDao


    override fun onDisconnected() {
        Toast.makeText(binding.root.context, getString(R.string.no_connection), Toast.LENGTH_SHORT)
            .show()
        isConnected = false
    }

    override fun onConnected() {
        isConnected = true
    }

    override fun queryApi(q: String) {
        if (isConnected) {
            binding.idProgressDetail.visibility = View.VISIBLE
            setViewModel.apply {
                setResponseLiveDataUser(q)
                    .observe(viewLifecycleOwner, {
                        binding.apply {
                            val body = it.body()

                            gitUserDao = GitUserDao(
                                0,
                                loginName,
                                body?.name ?: "no name",
                                body?.company ?: "no company",
                                body?.avatar_url,
                                body?.location ?: "no location"
                            )
                            idTvUsernameDetail.text = q
                            idNamaDetail.text = body?.name ?: "no name"
                            idCompany.text = body?.company ?: "no company"
                            idLocation.text = body?.location ?: "no location"
                            idRepo.text = body?.public_repos?.toString() ?: "0"
                            idFollowerDetail.text = (body?.followers ?: "no follower").toString()
                            idFollowingDetail.text = (body?.following ?: "no following").toString()
                            Glide.with(this@DetailUserFragment)
                                .load(body?.avatar_url)
                                .apply(RequestOptions())
                                .into(idImgDetailUser)
                        }
                        changeIcon()
                        binding.idProgressDetail.visibility = View.GONE
                    })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            loginName = it.getString(LOGIN_NAME)
        }
        androidViewModel = ViewModelProvider(this).get(DataBaseAndroidViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.fav_btn_detail).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().invalidateOptionsMenu()
        inflater.inflate(R.menu.share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.id_share_btn -> {
            val intent: Intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, "https://github.com/FauzanNR/Apps")
            startActivity(Intent.createChooser(intent, "Share Via"))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_detail_user, container, false)
        binding = FragmentDetailUserBinding.bind(view)
        queryApi(loginName.toString())
        binding.apply {
            idFabDetail.setOnClickListener(this@DetailUserFragment)
            idFollowerLayout.setOnClickListener(this@DetailUserFragment)
            idFollowingLayout.setOnClickListener(this@DetailUserFragment)
            idRepoLayout.setOnClickListener(this@DetailUserFragment)
        }
        return view
    }

    companion object {
        var isEmpty = true
        val LOGIN_NAME = "login"

        @JvmStatic
        fun newInstance(param1: String) =
            DetailUserFragment().apply {
                arguments = Bundle().apply {
                    loginName = param1
                    putString(LOGIN_NAME, param1)
                }
            }
    }

    override fun onClick(v: View?) {
        var a = 0
        when (v) {
            binding.idFollowerLayout -> {
                this@DetailUserFragment.setData(loginName.toString())
                val mContainer = FollowContainerFragment()
                this.childFragmentManager.beginTransaction().apply {
                    add(
                        R.id.id_container,
                        mContainer,
                        FollowContainerFragment::class.java.simpleName
                    )
                    addToBackStack("${a++}")
                    commit()
                }
            }
            binding.idFollowingLayout -> {

                this@DetailUserFragment.setData(loginName.toString())
                val mContainer = FollowContainerFragment()
                this.childFragmentManager.beginTransaction().apply {
                    add(
                        R.id.id_container,
                        mContainer,
                        FollowContainerFragment::class.java.simpleName
                    )
                    addToBackStack("${a++}")
                    commit()
                }
            }
//            binding.idRepoLayout ->
//                Navigation.findNavController(v)
//                .navigate(R.id.followContainerFragment)

            binding.idFabDetail -> {
                addDelete()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        this@DetailUserFragment.setData(loginName.toString())
        changeIcon()
    }

    override fun setData(s: String) {
        FollowerFragment().setData(s)
        FollowingFragment().setData(s)
    }

    private fun addDelete() {
        if (!TextUtils.isEmpty(loginName)) {
            if (checkIfItEmptyOnDataBase(loginName.toString())) {
                androidViewModel.addData(gitUserDao)
                Toast.makeText(this.context, "Added to favorite", Toast.LENGTH_SHORT).show()
                binding.idFabDetail.setIconResource(R.drawable.ic_baseline_favorite_24)
            } else {
                androidViewModel.deleteData(loginName.toString())
                Toast.makeText(this.context, "Delete from favorite", Toast.LENGTH_SHORT).show()
                binding.idFabDetail.setIconResource(R.drawable.ic_baseline_favorite_border_24)
            }
        } else {
            Toast.makeText(this.context, "Failed to add!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkIfItEmptyOnDataBase(s: String): Boolean {
        androidViewModel.searchData(s).observe(viewLifecycleOwner, {
            cekEmptyness(it.isEmpty())
        })
        return isEmpty
    }

    fun cekEmptyness(boolean: Boolean) {
        isEmpty = boolean
    }

    private fun changeIcon() {
        if (checkIfItEmptyOnDataBase(loginName.toString())) {
            binding.idFabDetail.setIconResource(R.drawable.ic_baseline_favorite_border_24)
        } else {
            binding.idFabDetail.setIconResource(R.drawable.ic_baseline_favorite_24)
        }

    }

}