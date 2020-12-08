package com.webtechsolution.ghumantey.ui.packageDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
        binding.iternariesRv.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        binding.includedRv.adapter = adapter
        binding.excludedRv.adapter = adapter
    }
}