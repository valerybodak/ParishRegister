package com.parish.register.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.parish.register.R
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
            dividerDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.list_divider)!!)
            binding?.rvRegister?.addItemDecoration(dividerDecoration)
            binding?.rvRegister?.addItemDecoration(CommonItemDecoration(requireContext()))
            binding?.rvRegister?.adapter = adapter
        }
    }

    private fun setupMenu(){
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.home_menu, menu)
                menu.findItem(R.id.action_search).let {
                    val searchView = it.actionView as SearchView
                    setupSearchView(searchView)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
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

    private fun setupSearchView(searchView: SearchView){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //TODO("Not yet implemented")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.getFilter().filter(newText);
                return false
            }
        })
    }


    private fun initSubscribers(){
        viewModel.parishRegisterLiveData.observe(viewLifecycleOwner){ list ->
            bindRegister(list)
        }
    }

    private fun bindRegister(list: List<ListItem>){
        adapter?.update(list)
    }
}