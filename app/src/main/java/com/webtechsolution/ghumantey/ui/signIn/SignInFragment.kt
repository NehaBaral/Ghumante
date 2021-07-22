package com.webtechsolution.ghumantey.ui.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.jakewharton.rxbinding3.widget.textChanges
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.SignInFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        binding.appbarToolbar.apply {
            toolbarApp.apply {
                toolbarTitle.text = "Sign In "
                NavigationUI.setupWithNavController(this, findNavController())
                setNavigationIcon(R.drawable.ic_arrow_back)
            }
        }
        binding.apply {
            signInBtn.setOnClickListener {
          //      findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeScreenFragment())
                val email = icEmail.text.toString()
                val password = icPassword.text.toString()

                if (password.isBlank()) {
                    icPasswordField.error = "Please enter valid password"
                }
                if (email.isBlank()) {
                    icEmailField.error = "Please enter valid email"
                }
                if (email.isNotBlank() && password.isNotBlank()) {
                    val agencySwitch = binding.agencyPacSwitch.isChecked
                    viewModel.userSignIn(email, password,agencySwitch)
                } else {
                    icEmailField.error = "Please enter valid email"
                }
                viewModel.state.observe(viewLifecycleOwner, Observer { uiState ->
                    /*if (uiState.loadingDialog) showLoadingDialog("Signing you up")
                    else hideLoadingDialog()*/
                    //uiState.toast.value.let { toast(it!!) }
                    binding.icEmail.error = uiState.emailError
                    binding.icPassword.error = uiState.passwordError
                    uiState.success.value?.let {
                        //hideLoadingDialog()
                        if (!uiState.signInResponse?.agency!!){
                            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeScreenFragment())
                        }else{
                            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToAgencyHomeFragment())
                        }
                    }
                })
            }

            binding.icEmail.textChanges().subscribe { binding.icEmailField.error = null }.isDisposed
            binding.icPassword.textChanges().subscribe { binding.icPasswordField.error = null }.isDisposed
        }
    }
}