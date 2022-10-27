package com.parish.register.ui.filter

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.parish.register.databinding.FragmentFilterBinding
import com.parish.register.model.ListItem
import com.parish.register.ui.base.BaseFragment

class FilterFragment : BaseFragment() {

    private val viewModel by viewModels<FilterViewModel>()
    private var binding: FragmentFilterBinding? = null

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
            binding = FragmentFilterBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initSubscribers()
    }

    private fun initViews(){

    }

    private fun initSubscribers(){
        viewModel.parishRegisterLiveData.observe(viewLifecycleOwner){ list ->
            bindRegister(list)
        }
    }

    private fun bindRegister(list: List<ListItem>){
        //adapter?.update(list)
    }
}