package com.parish.register.ui.filter

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.parish.register.R
import com.parish.register.databinding.FragmentFilterBinding
import com.parish.register.model.FilterType
import com.parish.register.model.ListFilter
import com.parish.register.ui.base.BaseFragment

class FilterFragment : BaseFragment() {

    private val args by navArgs<FilterFragmentArgs>()
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
        setupPeriodSlider()
    }

    private fun initSubscribers() {
        viewModel.filterLiveData.observe(viewLifecycleOwner) { filter ->
            bindFilter(filter)
        }
    }

    private fun bindFilter(filter: ListFilter) {
        bindChips(filter.filterType)
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
                        saveFilter()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun bindChips(filterType: FilterType) {
        when (filterType) {
            FilterType.BORN -> binding?.chipBorn?.isChecked = true
            FilterType.MARRIAGE -> binding?.chipMarriages?.isChecked = true
            FilterType.DIED -> binding?.chipDied?.isChecked = true
            else -> binding?.chipNoFilters?.isChecked = true
        }
    }

    private fun setupPeriodSlider() {
        binding?.periodSlider?.stepSize = SLIDER_PERIOD_STEP_SIZE
    }

    private fun saveFilter() {
        viewModel.saveFilter(
            when (binding?.chipGroupFilter?.checkedChipId) {
                R.id.chipBorn -> FilterType.BORN
                R.id.chipMarriages -> FilterType.MARRIAGE
                R.id.chipDied -> FilterType.DIED
                else -> FilterType.NO_FILTERS
            },
            binding?.periodSlider?.values?.get(0)?.toInt() ?: 0,
            binding?.periodSlider?.values?.get(1)?.toInt() ?: 0,
        )
        setFragmentResult(args.requestKey, bundleOf())
        popBackStack()
    }

    companion object {
        const val SLIDER_PERIOD_STEP_SIZE = 1f
    }
}