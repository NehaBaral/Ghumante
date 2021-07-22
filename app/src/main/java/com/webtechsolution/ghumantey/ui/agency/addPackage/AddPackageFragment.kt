package com.webtechsolution.ghumantey.ui.agency.addPackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.webtechsolution.ghumantey.databinding.AddPackageFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPackageFragment : BaseFragment(){
    override val viewModel by viewModels<AddPackageViewModel>()
    lateinit var binding:AddPackageFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AddPackageFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitPackageDetail.setOnClickListener {
            val packageName = binding.icPackageName.text.toString()
            val packagePrice = binding.icPackagePrice.text.toString().toInt()
            val destinationName = binding.icDestinationName.text.toString()
            val destinationDesc = binding.icDestinationDesc.text.toString()
            val destinationIternary = binding.icDestinationDesc.text.toString()
            val destinationIncluded = binding.icDestinationIncluded.text.toString()
            val destinationExcluded = binding.icDestinationExcluded.text.toString()
            val destinationPhone = binding.icDestinationPhone.text.toString().toLong()
            val destinationEmail = binding.icDestinationEmail.text.toString()
            viewModel.updateNewPackageDetail(packageName,packagePrice,destinationName,
            destinationDesc,destinationIternary,destinationIncluded,destinationExcluded,
            destinationPhone,destinationEmail)
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {uiState->
            uiState.success.value?.also {
                findNavController().popBackStack()
            }
            uiState.toast.value?.also {
                toast(it)
            }
        })
    }
}