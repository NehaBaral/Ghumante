package com.webtechsolution.ghumantey.ui.agency.userDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.webtechsolution.ghumantey.databinding.UserDetailFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.agency.userDetail.adapter.UserDetailAdapter
import com.webtechsolution.ghumantey.ui.packageDetail.PackageDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : BaseFragment(){
    override val viewModel by viewModels<UserDetailViewModel>()
    lateinit var binding:UserDetailFragmentBinding
    lateinit var adapter:UserDetailAdapter
    private val args by navArgs<UserDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPackage(args.packageId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserDetailFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserDetailAdapter()
        binding.userDetailRv.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.agencyDetail?.bookings)
        })
    }
}