package com.parish.register.ui.inspector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.parish.register.R
import com.parish.register.databinding.FragmentInspectorBinding
import com.parish.register.ui.base.BaseFragment
import com.parish.register.ui.custom.CommonItemDecoration

class InspectorFragment : BaseFragment() {

    private val viewModel by viewModels<InspectorViewModel>()
    private var binding: FragmentInspectorBinding? = null

    private var duplicatesAdapter: DuplicatesAdapter? = null

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
        initViews()
        initSubscribers()
    }

    private fun initViews() {
        if (duplicatesAdapter == null) {
            duplicatesAdapter = DuplicatesAdapter()
            binding?.rvDuplicates?.apply {
                layoutManager = LinearLayoutManager(context)
                val dividerDecoration =
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                dividerDecoration.setDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.list_divider
                    )!!
                )
                addItemDecoration(dividerDecoration)
                addItemDecoration(CommonItemDecoration(requireContext(), false))
                adapter = duplicatesAdapter
            }
        }
    }

    private fun initSubscribers() {
        viewModel.duplicatesLiveData.observe(viewLifecycleOwner) { duplicates ->
            bindDuplicates(duplicates)
        }
    }

    private fun bindDuplicates(duplicates: List<DuplicateItem>) {
        binding?.tvTitle?.text = getString(R.string.possible_duplicates_format, duplicates.size)
        duplicatesAdapter?.update(duplicates)
    }
}