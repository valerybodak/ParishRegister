package com.parish.register.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.parish.register.R
import com.parish.register.common.Resource
import com.parish.register.databinding.FragmentHomeBinding
import com.parish.register.model.ListItem
import com.parish.register.ui.base.BaseFragment
import com.parish.register.ui.custom.CommonItemDecoration
import com.parish.register.utils.goneView
import com.parish.register.utils.showView

class HomeFragment : BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private var binding: FragmentHomeBinding? = null

    private var adapter: RegisterAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_KEY_FILTER) { _, _ ->
            viewModel.getLists()
        }
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
        initListeners()
        initSubscribers()
    }

    private fun initViews() {
        setupMenu()
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
            dividerDecoration.setDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.list_divider
                )!!
            )
            binding?.rvRegister?.addItemDecoration(dividerDecoration)
            binding?.rvRegister?.addItemDecoration(CommonItemDecoration(requireContext(), true))
            binding?.rvRegister?.adapter = adapter
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_register, menu)
                menu.findItem(R.id.action_search).let {
                    val searchView = it.actionView as SearchView
                    setupSearchView(searchView)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        // todo
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //TODO("Not yet implemented")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter(newText ?: "")
                return false
            }
        })
    }

    private fun initListeners() {
        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel.getLists(forceSync = true)
        }

        binding?.fab?.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionRegisterFragmentToFilterFragment(
                    requestKey = REQUEST_KEY_FILTER
                )
            )
        }
    }

    private fun initSubscribers() {
        viewModel.parishRegisterLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success ->
                    resource.data?.let {
                        bindRegister(it)
                    }
                is Resource.Error -> {
                    hideLoading()
                    showToast(resource.message)
                    resource.data?.let {
                        bindRegister(it)
                    }
                }
            }
        }
    }

    private fun bindRegister(list: List<ListItem>) {
        hideLoading()
        if (list.isEmpty()) {
            binding?.noItemsView?.showView()
            binding?.rvRegister?.goneView()
        } else {
            binding?.noItemsView?.goneView()
            binding?.rvRegister?.showView()
            adapter?.update(list)
        }
    }

    private fun showLoading() {
        binding?.swipeRefresh?.isRefreshing = true
    }

    private fun hideLoading() {
        binding?.swipeRefresh?.isRefreshing = false
    }

    companion object {
        const val REQUEST_KEY_FILTER = "REQUEST_KEY_FILTER"
    }
}