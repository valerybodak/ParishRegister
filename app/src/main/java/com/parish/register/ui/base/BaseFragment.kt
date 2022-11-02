package com.parish.register.ui.base

import androidx.fragment.app.Fragment
import com.parish.register.common.SharedPrefsManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment(){

    @Inject
    lateinit var sharedPrefsManager: SharedPrefsManager
}