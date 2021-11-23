package com.mel.kalku

import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlackBoardDialogFragmentViewModel: ViewModel() {

    private var _operation = MutableLiveData<OperatorEnum>()
    val operation : MutableLiveData<OperatorEnum>
        get() = _operation

    fun dialogCreate(parentFragmentManager: FragmentManager, blackBoardFragment: BlackBoardFragment, title: String) {

        BlackBoardDialogFragment().show(parentFragmentManager, "Kk")
        parentFragmentManager.setFragmentResultListener("requestKey", blackBoardFragment) { key, bundle ->
            _operation.value = bundle.get("operation") as OperatorEnum
        }

    }

}