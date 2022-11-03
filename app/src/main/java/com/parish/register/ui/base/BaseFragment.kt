package com.parish.register.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment(){

    protected fun popBackStack(){
        findNavController().popBackStack()
    }
}