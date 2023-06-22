package com.parish.register.ui.inspector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.parish.register.R
import com.parish.register.databinding.FragmentDashboardBinding
import com.parish.register.ui.base.BaseFragment
import com.parish.register.ui.custom.dashboard.DashboardItem
import com.parish.register.ui.dashboard.DashboardUiState
import com.parish.register.ui.dashboard.DashboardViewModel

class InspectorFragment : BaseFragment() {

    private val viewModel by viewModels<DashboardViewModel>()
    private var binding: FragmentDashboardBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDashboard()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentDashboardBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSubscribers()
    }

    private fun initSubscribers() {
        viewModel.dashboardLiveData.observe(viewLifecycleOwner) { state ->
            bindState(state)
        }
    }

    private fun bindState(state: DashboardUiState) {
        binding?.apply {
            dashboardView.bind(
                DASHBOARD_THICKNESS_DP, listOf(
                    DashboardItem(state.bornCount.toFloat(), DASHBOARD_BORN_COLOR),
                    DashboardItem(state.marriageCount.toFloat(), DASHBOARD_MARRIAGES_COLOR),
                    DashboardItem(state.diedCount.toFloat(), DASHBOARD_DIED_COLOR)
                )
            )
            bornItem.bind(
                DASHBOARD_BORN_COLOR,
                getString(
                    R.string.dashboard_born,
                    state.bornCount
                )
            )
            marriagesItem.bind(
                DASHBOARD_MARRIAGES_COLOR,
                getString(
                    R.string.dashboard_marriages,
                    state.marriageCount
                )
            )
            diedItem.bind(
                DASHBOARD_DIED_COLOR,
                getString(
                    R.string.dashboard_died,
                    state.diedCount
                )
            )
        }
    }

    companion object {
        private const val DASHBOARD_THICKNESS_DP = 60
        private const val DASHBOARD_BORN_COLOR = R.color.yellow
        private const val DASHBOARD_MARRIAGES_COLOR = R.color.colorPrimary
        private const val DASHBOARD_DIED_COLOR = R.color.light_grey
    }
}