package com.parish.register.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.parish.register.R
import com.parish.register.common.Resource
import com.parish.register.databinding.FragmentHomeBinding
import com.parish.register.model.ListItem
import com.parish.register.ui.base.BaseFragment
import com.parish.register.ui.custom.CommonItemDecoration

class HomeFragment : BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private var binding: FragmentHomeBinding? = null

    private var adapter: RegisterAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLists()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initSubscribers()
        viewModel.getLists()
    }

    private fun initViews() {
        if (adapter == null) {
            adapter = RegisterAdapter(object :
                RegisterAdapter.RegisterAdapterListener {
                override fun onItemClick(item: ListItem) {
                    //("Not yet implemented")
                }
            })
            binding?.rvRegister?.apply {
                layoutManager = LinearLayoutManager(context)
            }
            val dividerDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            dividerDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.list_divider)!!)
            binding?.rvRegister?.addItemDecoration(dividerDecoration)
            binding?.rvRegister?.addItemDecoration(CommonItemDecoration(requireContext()))
            binding?.rvRegister?.adapter = adapter
        }
    }

    private fun initSubscribers(){
        viewModel.parishRegisterLiveData.observe(viewLifecycleOwner){ resource ->
            when (resource) {
                is Resource.Loading -> {
                    //showLoading()
                    resource.data?.let {
                        //bindStorage(it)
                    }
                }
                is Resource.Success -> {
                    //hideLoading()
                    resource.data?.let {
                        bindRegister(it)
                    }
                }
                is Resource.Error -> {
                    /*hideLoading()
                    showSnackBar(getString(R.string.storage_connection_error))
                    resource.data?.let {
                        bindStorage(it)
                    }*/
                }
            }
        }
    }

    private fun bindRegister(list: List<ListItem>){
        adapter?.update(list)
    }
}