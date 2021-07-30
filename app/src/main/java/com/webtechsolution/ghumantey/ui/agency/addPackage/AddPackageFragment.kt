package com.webtechsolution.ghumantey.ui.agency.addPackage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.AddPackageFragmentBinding
import com.webtechsolution.ghumantey.helpers.FormUtils
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MultipartBody
import java.util.ArrayList

@AndroidEntryPoint
class AddPackageFragment : BaseFragment() {
    override val viewModel by viewModels<AddPackageViewModel>()
    lateinit var binding: AddPackageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPackageFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appToolbar.apply {
            toolbarTitle.text = "Add Packages"
            toolbarApp.apply {
                NavigationUI.setupWithNavController(this, findNavController())
                setNavigationIcon(R.drawable.ic_arrow_back)
            }

            binding.submitPackageDetail.setOnClickListener {
                val packageName = binding.icPackageName.text.toString()
                val packagePrice = binding.icPackagePrice.text.toString()
                val destinationName = binding.icDestinationName.text.toString()
                val destinationDesc = binding.icDestinationDesc.text.toString()
                val destinationIternary = binding.icDestinationDesc.text.toString()
                val destinationIncluded = binding.icDestinationIncluded.text.toString()
                val destinationExcluded = binding.icDestinationExcluded.text.toString()
                val destinationPhone = binding.icDestinationPhone.text.toString()
                val destinationEmail = binding.icDestinationEmail.text.toString()
                val destinationDays = binding.icDays.text.toString()
                if (packageName.isNotEmpty() && packagePrice.isNotEmpty() && destinationName.isNotEmpty() && destinationDesc.isNotEmpty()
                    && destinationIternary.isNotEmpty() && destinationIncluded.isNotEmpty() && destinationExcluded.isNotEmpty()
                    &&destinationPhone.isNotEmpty() && destinationEmail.isNotEmpty() && destinationDays.isNotEmpty()){
                    viewModel.updateNewPackageDetail(
                        packageName, packagePrice.toInt(), destinationName,
                        destinationDesc, destinationIternary, destinationIncluded, destinationExcluded,
                        destinationPhone.toLong(), destinationEmail, destinationDays
                    )
                }else{
                    toast("Please enter every detail")
                }
            }

            viewModel.state.observe(viewLifecycleOwner, Observer { uiState ->
                uiState.success.value?.also {
                    findNavController().popBackStack()
                }
                uiState.toast.value?.also {
                    toast(it)
                }

                Glide.with(requireContext())
                    .load(uiState.image)
                    .placeholder(R.drawable.ic_baseline_camera_alt_24)
                    .into(binding.icPackageImage)
            })

            binding.packageImage.setOnClickListener {
                ImagePicker.pickImage(disposeBag, this@AddPackageFragment, true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ImagePicker.onImagePickResult(this, requestCode, resultCode, data)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it -> it.isNotEmpty() }
            .map { it -> it[0] }
            .subscribe({ uri ->
                viewModel.addImage(uri)
            }
            ) { throwable -> toast("Error while loading image") }.isDisposed
    }

}