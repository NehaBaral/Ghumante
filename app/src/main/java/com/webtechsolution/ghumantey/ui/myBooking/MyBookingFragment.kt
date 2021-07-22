package com.webtechsolution.ghumantey.ui.myBooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.MyBookingFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.myBooking.adapter.MyBookingAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyBookingFragment : BaseFragment() {
    override val viewModel by viewModels<MyBookingViewModel>()
    lateinit var binding:MyBookingFragmentBinding

    @Inject
    lateinit var adapter:MyBookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBookingPackage()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyBookingFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myBookingRv.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.myBookingList)
        })
        binding.toolbarApp.apply {
            toolbarApp.apply {
                toolbarTitle.text = "My Booking "
                NavigationUI.setupWithNavController(this, findNavController())
                setNavigationIcon(R.drawable.ic_arrow_back)
            }
        }
    }
}