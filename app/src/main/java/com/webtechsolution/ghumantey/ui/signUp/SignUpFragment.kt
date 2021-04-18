package com.webtechsolution.ghumantey.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.jakewharton.rxbinding3.widget.textChanges
import com.webtechsolution.ghumantey.databinding.SignUpFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {
    override val viewModel by viewModels<SignUpViewModel>()
    lateinit var binding:SignUpFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignUpFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarApp.apply {
            toolbarApp.apply {
                toolbarTitle.text = "Sign Up "
                NavigationUI.setupWithNavController(this, findNavController())
            }
        }
        binding.apply {
            signupBtn.setOnClickListener {
                val username = icSignupUname.text.toString()
                val password = icSignupPassword.text.toString()
                if (username.isBlank()){
                    icSignupUnameField.error = "Please enter valid name"
                }
                if (username.length < 2){
                    icSignupUnameField.error = "Please enter valid name"
                }
                if (password.isBlank()){
                    icSignupPasswordField.error = "Please enter valid password"
                }
                if (password.length <4){
                    icSignupPasswordField.error = "Please enter valid password"
                }
                if (username.isNotBlank()){
                    val agencySwitch = binding.agencyPacSwitch.isChecked
                    viewModel.userRegister(username,password,agencySwitch)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, Observer {uiState->
            if (uiState.loadingDialog) showLoadingDialog("Signing you up")
            else hideLoadingDialog()
            uiState.toast.value?.also {
                toast(it)
            }
            uiState.success.value?.let {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
            }
        })

        binding.icSignupUname.textChanges().subscribe { binding.icSignupUnameField.error = null }.isDisposed
        binding.icSignupPassword.textChanges().subscribe { binding.icSignupPasswordField.error = null }.isDisposed
    }
}