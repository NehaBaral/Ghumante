package com.webtechsolution.ghumantey.ui.agency.agencyBooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.webtechsolution.ghumantey.databinding.AgencyBookingFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.agency.agencyHome.adapter.AgencyHomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgencyBookingFragment : BaseFragment() {
    override val viewModel by viewModels<AgencyBookingViewModel>()
    lateinit var binding:AgencyBookingFragmentBinding
    lateinit var adapter: AgencyHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AgencyHomeAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AgencyBookingFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.agencyBookingRv.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.agencyPackageList)
        })
    }
}