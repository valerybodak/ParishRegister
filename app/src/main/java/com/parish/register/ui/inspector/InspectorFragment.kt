package com.parish.register.ui.inspector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.parish.register.R
import com.parish.register.databinding.FragmentInspectorBinding
import com.parish.register.ui.base.BaseFragment

class InspectorFragment : BaseFragment() {

    private val viewModel by viewModels<InspectorViewModel>()
    private var binding: FragmentInspectorBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDuplicates()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentInspectorBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSubscribers()
    }

    private fun initSubscribers() {
        viewModel.duplicatesLiveData.observe(viewLifecycleOwner) { duplicates ->
            bindDuplicates(duplicates)
        }
    }

    private fun bindDuplicates(duplicates: List<DuplicateItem>) {

    }
}