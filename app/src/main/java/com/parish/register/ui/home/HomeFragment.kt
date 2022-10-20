package com.parish.register.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.parish.register.databinding.FragmentHomeBinding
import kotlinx.coroutines.Job

class HomeFragment : Fragment() {

    //private val args by navArgs<QuizFragmentArgs>()
    private val viewModel by viewModels<HomeViewModel>()
    private var binding: FragmentHomeBinding? = null


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initArgs(Category.getById(args.categoryId))
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupToolbar(binding?.toolbarDefault?.toolbar, false, getString(R.string.books))
        //hideNavigationIcon(binding?.toolbarDefault?.toolbar)
        // if (isNeedToInitViews()) {
        //initViews()
        //}
        //initFields()
        //initSubscribers()
        //viewModel.getQuiz()
    }
}