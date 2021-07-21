package com.webtechsolution.ghumantey.ui.packageReview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.PackageReviewFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.packageReview.adapter.PackageReviewAdapter
import com.webtechsolution.ghumantey.ui.review.ReviewFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PackageReviewFragment : BaseFragment() {
    override val viewModel by viewModels<PackageReviewViewModel>()
    lateinit var binding: PackageReviewFragmentBinding
    private val args by navArgs<PackageReviewFragmentArgs>()

    @Inject
    lateinit var adapter: PackageReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PackageReviewAdapter()
        viewModel.updateReview(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PackageReviewFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reviewRv.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner, Observer {
            it.commentItem?.size?.let {
                binding.emptyView.isVisible = it <= 0
            }
            adapter.submitList(it.commentItem)
            if (it.loading) showLoadingDialog("Loading...")
            else hideLoadingDialog()
        })

        binding.appbarToolbar.apply {
            toolbarApp.apply {
                toolbarTitle.text = "Package Review "
                NavigationUI.setupWithNavController(this, findNavController())
                setNavigationIcon(R.drawable.ic_arrow_back)
            }
        }
    }
}