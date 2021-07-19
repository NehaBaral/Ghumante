package com.webtechsolution.ghumantey.ui.packageBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ibotta.android.support.pickerdialogs.SupportedDatePickerDialog
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.databinding.PackageBookBottomsheetBinding
import com.webtechsolution.ghumantey.helpers.base.BaseBottomSheet
import com.webtechsolution.ghumantey.ui.packageDetail.PackageDetailFragmentArgs
import java.util.*

class PackageBookBottomSheet : BaseBottomSheet(), SupportedDatePickerDialog.OnDateSetListener {
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
        binding.icDate.setOnClickListener {
            openDatePicker()
        }
        binding.bookPackage.setOnClickListener {
            viewModel.bookPackage(args.packageId,binding.icNumOfTrav.text.toString(),binding.icDate.text.toString(),binding.icContactInfo.text.toString())
        }
    }

    private fun openDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        SupportedDatePickerDialog(
            requireContext(),
            R.style.SpinnerDatePickerDialogTheme, this, year, month, day
        ).show()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        binding.icDate.setText(dayOfMonth.toString() + "/" + (month + 1).toString() + "/" + year.toString())
    }
}
