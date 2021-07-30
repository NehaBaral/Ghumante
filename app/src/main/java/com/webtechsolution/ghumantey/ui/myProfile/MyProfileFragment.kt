package com.webtechsolution.ghumantey.ui.myProfile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.webtechsolution.ghumantey.MainActivity
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.MyProfileFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : BaseFragment() {
    override val viewModel by viewModels<MyProfileViewModel>()
    lateinit var binding: MyProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyProfileFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.apply {
            toolbarApp.apply {
                toolbarTitle.text = "Profile "
                NavigationUI.setupWithNavController(this, findNavController())
                setNavigationIcon(R.drawable.ic_arrow_back)
            }
        }
        viewModel.state.observe(viewLifecycleOwner, Observer {
            binding.profileName.text = it.username
            it.signUp.value?.let {
                val intent:Intent = Intent(requireActivity(),MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })
        binding.myBooking.setOnClickListener {
            findNavController().navigate(MyProfileFragmentDirections.actionMyProfileFragmentToMyBookingFragment2())
        }

        binding.logOut.setOnClickListener {
            viewModel.signUp()
        }
    }
}
