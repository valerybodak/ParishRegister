package com.parish.register.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.parish.register.R
import com.parish.register.databinding.FragmentDashboardBinding
import com.parish.register.ui.base.BaseFragment
import com.parish.register.ui.custom.dashboard.DashboardItem

class DashboardFragment : BaseFragment() {

    private val viewModel by viewModels<DashboardViewModel>()
    private var binding: FragmentDashboardBinding? = null

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
            binding = FragmentDashboardBinding.inflate(inflater, container, false)
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
        binding?.dashboardView?.bind(listOf(
            DashboardItem(80F, R.color.white),
            DashboardItem(17F, R.color.black),
            DashboardItem(3F, R.color.light_grey)
        ))
    }

    private fun initListeners() {

    }

    private fun initSubscribers() {

    }
}