package com.webtechsolution.ghumantey.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.webtechsolution.ghumantey.databinding.ReviewFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment : BaseFragment() {
    override val viewModel by viewModels<ReviewViewModel>()
    lateinit var binding:ReviewFragmentBinding
    private val args by navArgs<ReviewFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ReviewFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ratingSubmit.setOnClickListener {
            if (binding.icReview.text.toString() != null){
                showLoadingDialog("Updating review...")
                viewModel.postReview(binding.icReview.text.toString(),binding.packageRating.rating,args)
            }
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            toast(it.toast.value.toString())
            if (it.reviewSuccess){
                hideLoadingDialog()
                findNavController().popBackStack()
            }
        })
    }
}