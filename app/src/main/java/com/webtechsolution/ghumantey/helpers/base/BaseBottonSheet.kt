package com.webtechsolution.ghumantey.helpers.base

import android.app.Dialog
import android.app.ProgressDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.webtechsolution.ghumantey.R
import com.webtechsolution.ghumantey.helpers.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import kotlin.math.roundToInt

@AndroidEntryPoint
open class BaseBottomSheet : BottomSheetDialogFragment(), DisposableContainer {

    private val disposeBag = CompositeDisposable()
    private var loadingProgressBar: Dialog? = null

    protected fun showLoadingDialog(title: String = "Loading...") {
        loadingProgressBar = (loadingProgressBar ?: ProgressDialog(requireContext())).apply {
            setCancelable(false)
            setTitle(title)
            show()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme).apply {
            setCanceledOnTouchOutside(true)
            this.setOnShowListener {
                val dialog = it as BottomSheetDialog
                val bottomSheet =
                    dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!
                BottomSheetBehavior.from(bottomSheet).apply {
                    isDraggable = true
                    peekHeight = 300.dpToPx()
                    state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    protected fun hideLoadingDialog() {
        loadingProgressBar?.dismiss()
    }

    protected fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, duration).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposeBag.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
    }

    override fun add(d: Disposable) = disposeBag.add(d)
    override fun remove(d: Disposable) = disposeBag.remove(d)
    override fun delete(d: Disposable) = disposeBag.delete(d)
}