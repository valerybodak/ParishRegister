package com.parish.register.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.parish.register.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment(){

    protected fun popBackStack(){
        findNavController().popBackStack()
    }

    protected fun showToast(message: String?) {
        val showMsg = message ?: getString(R.string.default_error_message)
        Toast.makeText(requireContext(), showMsg, Toast.LENGTH_LONG).show()
    }
}