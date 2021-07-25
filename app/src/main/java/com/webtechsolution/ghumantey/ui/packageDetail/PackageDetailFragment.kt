package com.webtechsolution.ghumantey.ui.packageDetail

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
import com.webtechsolution.ghumantey.databinding.PackageDetailFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.packageBook.PackageBookBottomSheet
import com.webtechsolution.ghumantey.ui.packageDetail.adapter.IternaryAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PackageDetailFragment : BaseFragment() {
    override val viewModel by viewModels<PackageDetailViewModel>()
    lateinit var binding:PackageDetailFragmentBinding
    private val args by navArgs<PackageDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPackageDetail(args)
    }

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
        binding.toolbar.apply {
            toolbarApp.apply {
                toolbarTitle.text = "Detail "
                NavigationUI.setupWithNavController(this, findNavController())
                setNavigationIcon(R.drawable.ic_arrow_back)
            }
        }
        binding.bookNowBtn.setOnClickListener {
            findNavController().navigate(PackageDetailFragmentDirections.actionPackageDetailFragmentToPackageBookBottomSheet(args.packageId))
        }

        viewModel.pState.observe(viewLifecycleOwner, Observer {uiState->
            uiState.packageDetail.let {item->
                binding.packageAmount.text = item?.price.toString()
                binding.packageDesc.text = item?.description.toString()
                binding.packageDays.text = item?.updatedAt
                binding.excludedRv.text = item?.excluded
                binding.includedRv.text = item?.included
                binding.includedRv.text = item?.iternaries
                binding.contactNumber.text = item?.phone.toString()
                binding.contactEmail.text = item?.email
                val isBooked:Boolean? = item?.bookings?.find {
                    it.author._id == uiState.loginData?.userId
                }?.booking
                if (isBooked == true){
                    binding.bookNowBtn.isEnabled = false
                    binding.bookNowBtn.text = "Already Booked"
                }else{
                    binding.bookNowBtn.isEnabled = true
                    binding.bookNowBtn.text = "Book Now"
                }
            }
        })

        binding.writeReviewBtn.setOnClickListener {
            findNavController().navigate(PackageDetailFragmentDirections.actionPackageDetailFragmentToReviewFragment(args.packageId,""))
        }

        binding.reviewList.setOnClickListener {
            findNavController().navigate(PackageDetailFragmentDirections.actionPackageDetailFragmentToPackageReviewFragment(args.packageId))
        }
    }
}