package com.webtechsolution.ghumantey.ui.agency.agencyHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.AgencyHomeFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.agency.agencyHome.adapter.AgencyHomeAdapter
import com.webtechsolution.ghumantey.ui.packageDetail.PackageDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgencyHomeFragment : BaseFragment() {
    override val viewModel by viewModels<AgencyHomeViewModel>()
    lateinit var binding:AgencyHomeFragmentBinding
    private val args by navArgs<AgencyHomeFragmentArgs>()
    lateinit var adapter:AgencyHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AgencyHomeAdapter()
        viewModel.updateAgencyPackage(args.token)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AgencyHomeFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.agencyPackageRv.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.agencyPackageList)
        })

          binding.appToolbar.apply {
                toolbarTitle.text = "Packages"
                toolbarApp.apply {
                    NavigationUI.setupWithNavController(this, findNavController())
                    setNavigationIcon(R.drawable.ic_arrow_back)
                }
        }

        binding.addPackages.setOnClickListener {
            findNavController().navigate(AgencyHomeFragmentDirections.actionAgencyHomeFragmentToAddPackageFragment(args.token))
        }
    }
}