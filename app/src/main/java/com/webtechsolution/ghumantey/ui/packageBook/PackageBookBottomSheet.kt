package com.webtechsolution.ghumantey.ui.packageBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.webtechsolution.ghumantey.databinding.PackageBookBottomsheetBinding
import com.webtechsolution.ghumantey.helpers.base.BaseBottomSheet
import com.webtechsolution.ghumantey.ui.packageDetail.PackageDetailFragmentArgs

class PackageBookBottomSheet : BaseBottomSheet() {
    private val viewModel by viewModels<PackageBookViewModel>()
    lateinit var binding: PackageBookBottomsheetBinding
    private val args by navArgs<PackageBookBottomSheetArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PackageBookBottomsheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bookPackage.setOnClickListener {
            viewModel.bookPackage(args.packageId,binding.icNumOfTrav.text.toString(),binding.icDate.text.toString(),binding.icContactInfo.text.toString())
        }
    }
}
