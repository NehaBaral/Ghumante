package com.webtechsolution.ghumantey.ui.myProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.MyProfileFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_screen_fragment.*

@AndroidEntryPoint
class MyProfileFragment : BaseFragment() {
    override val viewModel by viewModels<MyProfileViewModel>()
    lateinit var binding:MyProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyProfileFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.apply {
            appToolbar.inflateMenu(R.menu.edit)
            appToolbar.setOnMenuItemClickListener {
                findNavController().navigate(MyProfileFragmentDirections.actionMyProfileFragmentToProfileEditFragment())
                true
            }
            binding.toolbar.apply {
                appToolbar.apply {
                    toolbarTitle.text = "Profile "
                    NavigationUI.setupWithNavController(this, findNavController())
                    setNavigationIcon(R.drawable.ic_arrow_back)
                }
            }
            binding.myBooking.setOnClickListener {
                findNavController().navigate(MyProfileFragmentDirections.actionMyProfileFragmentToMyBookingFragment())
            }
        }
    }
}