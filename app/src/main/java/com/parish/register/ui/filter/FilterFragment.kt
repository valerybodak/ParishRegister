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
import com.parish.register.model.SortingType
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

    private fun setupPeriodSlider() {
        binding?.periodSlider?.stepSize = SLIDER_PERIOD_STEP_SIZE
    }

    private fun bindFilter(filter: ListFilter) {
        bindFilterType(filter.type)
        val periodFrom = filter.periodFrom.toFloat()
        val periodTo = filter.periodTo.toFloat()
        binding?.periodSlider?.setValues(periodFrom, periodTo)
        bindSortingType(filter.sortingType)
    }

    private fun bindFilterType(type: FilterType) {
        when (type) {
            FilterType.BORN -> binding?.chipBorn?.isChecked = true
            FilterType.MARRIAGES -> binding?.chipMarriages?.isChecked = true
            FilterType.DIED -> binding?.chipDied?.isChecked = true
            else -> binding?.chipAll?.isChecked = true
        }
    }

    private fun bindSortingType(type: SortingType) {
        when (type) {
            SortingType.BY_DATE_ASC -> binding?.sortingByDateAsc?.isChecked = true
            SortingType.BY_DATE_DESC -> binding?.sortingByDateDesc?.isChecked = true
            else -> binding?.sortingByName?.isChecked = true
        }
    }

    private fun saveFilter() {
        viewModel.saveFilter(
            getSelectedFilterType(),
            binding?.periodSlider?.values?.get(0)?.toInt() ?: 0,
            binding?.periodSlider?.values?.get(1)?.toInt() ?: 0,
            getSelectedSortingType()
        )
        setFragmentResult(args.requestKey, bundleOf())
        popBackStack()
    }

    private fun getSelectedFilterType(): FilterType {
        return when (binding?.chipGroupFilter?.checkedChipId) {
            R.id.chipBorn -> FilterType.BORN
            R.id.chipMarriages -> FilterType.MARRIAGES
            R.id.chipDied -> FilterType.DIED
            else -> FilterType.ALL
        }
    }

    private fun getSelectedSortingType(): SortingType {
        return when (binding?.sortingRadioGroup?.checkedRadioButtonId) {
            R.id.sortingByDateAsc -> SortingType.BY_DATE_ASC
            R.id.sortingByDateDesc -> SortingType.BY_DATE_DESC
            else -> SortingType.BY_NAME
        }
    }

    companion object {
        const val SLIDER_PERIOD_STEP_SIZE = 1f
    }
}