package com.webtechsolution.ghumantey.ui.packages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.PackagesFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.packages.adapter.PackageListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PackagesFragment : BaseFragment() {
    override val viewModel by viewModels<DestinationViewModel>()
    private val args by navArgs<PackagesFragmentArgs>()
    @Inject
    lateinit var adapter:PackageListAdapter
    lateinit var binding:PackagesFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDestinationList(args)
      //  viewModel.getDestinationList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PackagesFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.packageListRv.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.packagesList)
        })
        binding.toolbar.apply {
            appToolbar.apply {
                toolbarTitle.text = "Destination "
                NavigationUI.setupWithNavController(this, findNavController())
                setNavigationIcon(R.drawable.ic_arrow_back)
            }
        }

        adapter.clicks().subscribe {
            findNavController().navigate(PackagesFragmentDirections.actionDestinationFragmentToPackageDetailFragment())
        }.isDisposed
    }
}