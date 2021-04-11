package com.webtechsolution.ghumantey.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.webtechsolution.ghumantey.MainActivity
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.HomeScreenFragmentBinding
import com.webtechsolution.ghumantey.helpers.base.BaseFragment
import com.webtechsolution.ghumantey.ui.home.adapter.DestinationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_screen_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreenFragment : BaseFragment() {
    override val viewModel by viewModels<HomeScreenViewModel>()
    lateinit var binding:HomeScreenFragmentBinding

    @Inject
    lateinit var adapter: DestinationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDestinationList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeScreenFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DestinationAdapter()
        binding.destinationRv.adapter = adapter
        binding.recommededRv.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.destinationList)
        })
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.recommededRv.layoutManager = gridLayoutManager

        adapter.clicks().subscribe {
            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToDestinationFragment(it._id))
        }.isDisposed

        binding.apply {
            binding.profileBtn.setOnClickListener {
                findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToMyProfileFragment())
            }
        }
        viewModel.state.observe(viewLifecycleOwner, Observer {uiState->
            if (uiState.loading) showLoadingDialog("Loading destination")
            else hideLoadingDialog()
            uiState.toast.value?.let { toast(it) }
            adapter.submitList(uiState.destinationList)
        })

        binding.searchButton.setOnClickListener {
            if (binding.icSearch.text.toString() != null){
                findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToDestinationFragment(binding.icSearch.text.toString()))
            }
        }

    }
}
