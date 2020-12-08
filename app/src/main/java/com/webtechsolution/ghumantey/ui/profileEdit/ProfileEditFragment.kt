package com.webtechsolution.ghumantey.ui.profileEdit

import android.Manifest
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.mlsdev.rximagepicker.RxImagePicker
import com.mlsdev.rximagepicker.Sources
import com.tbruyelle.rxpermissions2.RxPermissions
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.ProfileEditFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditFragment : BaseFragment() {
    override val viewModel by viewModels<ProfileEditViewModel>()
    lateinit var binding:ProfileEditFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileEditFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appToolbar.apply {
          //  NavigationUI.setupWithNavController(this, findNavController())
        }
        binding.editImageCard.setOnClickListener {
            getDialog()
        }

    }

    private fun getDialog() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.image_dialog)
        val camera = dialog.findViewById(R.id.camera_title) as TextView
        val gallary = dialog.findViewById(R.id.gallary_title) as TextView
        camera.setOnClickListener {
           pickLocalImage(Sources.CAMERA)
        }
        gallary.setOnClickListener {
            pickLocalImage(Sources.GALLERY)
        }
        dialog.show()
    }

    private fun pickLocalImage(sources: Sources) {
        RxPermissions(requireActivity()).request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).filter { aBoolean -> aBoolean }
            .flatMap {
                RxImagePicker.Companion.with(childFragmentManager).requestImage(sources)
            }.subscribe {
                viewModel.setImage(it.toString())
            }.isDisposed
    }
}