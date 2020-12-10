package com.webtechsolution.ghumantey.ui.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.SignInFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment

class SignInFragment : BaseFragment() {
    override val viewModel by viewModels<SignInViewModel>()
    lateinit var binding:SignInFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignInFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInBtn.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeScreenFragment())
        }
        binding.appbarToolbar.apply {
            appToolbar.apply {
                toolbarTitle.text = "Sign In "
                NavigationUI.setupWithNavController(this, findNavController())
                setNavigationIcon(R.drawable.ic_arrow_back)
            }
        }
    }
}