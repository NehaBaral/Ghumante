package com.webtechsolution.ghumantey.ui.packageDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.PackageDetailFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.packageDetail.adapter.IternaryAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PackageDetailFragment : BaseFragment() {
    override val viewModel by viewModels<PackageDetailViewModel>()
    lateinit var binding:PackageDetailFragmentBinding

    @Inject
    lateinit var adapter: IternaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PackageDetailFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bookNowBtn.setOnClickListener {
            //findNavController().navigate(PackageDetailFragmentDirections.actionPackageDetailFragmentToPackageBookBottomSheet())
        }
        binding.iternariesRv.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        binding.appToolbar.apply {
            appToolbar.apply {
                toolbarTitle.text = "Package Detail"
                NavigationUI.setupWithNavController(this, findNavController())
                setNavigationIcon(R.drawable.ic_arrow_back)
            }
        }
        binding.includedRv.adapter = adapter
        binding.excludedRv.adapter = adapter
    }
}