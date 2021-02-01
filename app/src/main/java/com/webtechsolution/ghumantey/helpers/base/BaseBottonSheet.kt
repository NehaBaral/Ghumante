package com.webtechsolution.ghumantey.helpers.base

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.webtechsolution.ghumantey.R
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

@AndroidEntryPoint
open class BaseBottonSheet: BottomSheetDialogFragment(),DisposableContainer {
    private val disposeBag=CompositeDisposable()

    private var loadingProgressBar: Dialog? = null

    protected fun showLoadingDialog(title: String = "Loading...") {
        loadingProgressBar = (loadingProgressBar ?: ProgressDialog(requireContext())).apply {
            setCancelable(false)
            setTitle(title)
            show()
        }
    }

    protected fun hideLoadingDialog() {
        loadingProgressBar?.dismiss()
    }

    protected fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, duration).show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.BottomSheetDialog).apply {
            setCanceledOnTouchOutside(true)
        }
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