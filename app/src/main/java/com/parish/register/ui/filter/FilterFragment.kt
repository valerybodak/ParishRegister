package com.parish.register.ui.filter

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.parish.register.R
import com.parish.register.databinding.FragmentFilterBinding
import com.parish.register.model.ListFilter
import com.parish.register.ui.base.BaseFragment

class FilterFragment : BaseFragment() {

    private val viewModel by viewModels<FilterViewModel>()
    private var binding: FragmentFilterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFilter()
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

    private fun initViews() {
        setupMenu()
        setupRangeSlider()
    }

    private fun initSubscribers() {
        viewModel.filterLiveData.observe(viewLifecycleOwner) { filter ->
            bindFilter(filter)
        }
    }

    private fun bindFilter(filter: ListFilter) {
        val periodFrom = filter.periodFrom.toFloat()
        val periodTo = filter.periodTo.toFloat()
        binding?.periodSlider?.setValues(periodFrom, periodTo)
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_filter, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_apply -> {
                        sharedPrefsManager.saveListFilter(
                            ListFilter(
                                periodFrom = binding?.periodSlider?.values?.get(0)?.toInt() ?: 0,
                                periodTo = binding?.periodSlider?.values?.get(1)?.toInt() ?: 0,
                            )
                        )
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRangeSlider() {
        binding?.periodSlider?.stepSize = SLIDER_PERIOD_STEP_SIZE
    }

    companion object {
        const val SLIDER_PERIOD_STEP_SIZE = 1f
    }
}