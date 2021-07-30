package com.webtechsolution.ghumantey.ui.agency.agencyHome

import android.content.Intent
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
import com.webtechsolution.ghumantey.MainActivity
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.AgencyHomeFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.helpers.into
import com.webtechsolution.ghumantey.ui.agency.agencyHome.adapter.AdaptorAction
import com.webtechsolution.ghumantey.ui.agency.agencyHome.adapter.AgencyHomeAdapter
import com.webtechsolution.ghumantey.ui.packageDetail.PackageDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgencyHomeFragment : BaseFragment() {
    override val viewModel by viewModels<AgencyHomeViewModel>()
    lateinit var binding:AgencyHomeFragmentBinding
    lateinit var adapter:AgencyHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AgencyHomeAdapter()
        viewModel.updateAgencyPackage()
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
            it.logout.value?.let {
                val intent: Intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

          binding.appToolbar.apply {
                toolbarTitle.text = "Packages"
                toolbarApp.apply {
                    inflateMenu(R.menu.booked_package)
                    setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.logout ->{
                                viewModel.logOut()
                            }
                        }
                        true
                    }
                }
        }

        binding.addPackages.setOnClickListener {
            findNavController().navigate(AgencyHomeFragmentDirections.actionAgencyHomeFragmentToAddPackageFragment(""))
        }

        adapter.clicks().subscribe ({
            when(it){
                is AdaptorAction.UserDetailClicked -> {
                    findNavController().navigate(AgencyHomeFragmentDirections.actionAgencyHomeFragmentToUserDetailFragment(it.agencyPackage._id))
                }
            }
        },{
            it.printStackTrace()
        }).into(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateAgencyPackage()
    }
}