package com.webtechsolution.ghumantey.ui.packageBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.hilt.lifecycle.ViewModelInject
import com.webtechsolution.ghumantey.databinding.PackageBookBottomsheetBinding
import com.webtechsolution.ghumantey.helpers.base.BaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class PackageBookBottomSheet : BaseBottomSheet() {

    private val viewModel by viewModels<PackageBookViewModel>()
    lateinit var binding:PackageBookBottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= PackageBookBottomsheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
