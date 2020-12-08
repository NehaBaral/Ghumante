package com.webtechsolution.ghumantey.helpers.base

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.webtechsolution.ghumantey.app.Ghumantey
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

abstract class BaseFragment : Fragment(), DisposableContainer {

    protected abstract val viewModel: BaseViewModel

    private val disposeBag = CompositeDisposable()

    private var loadingProgressBar: Dialog? = null

    protected fun showLoadingDialog(title: String = "Loading...") {
        loadingProgressBar = (loadingProgressBar ?: ProgressDialog(requireContext())).apply {
            setCancelable(false)
            setTitle(title)
            show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.unAuthStatus.observe(this, Observer {
            AlertDialog.Builder(requireContext())
                .setTitle("You are not logged in")
                .setCancelable(false)
                .setNeutralButton("Okay") { dialog, _ ->
                    (requireActivity().applicationContext as? Ghumantey)?.restartProcess(true)
                    dialog.dismiss()
                }
                .show()
        })

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