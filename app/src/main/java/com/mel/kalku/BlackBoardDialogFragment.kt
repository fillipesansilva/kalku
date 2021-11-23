package com.mel.kalku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.mel.kalku.databinding.OperationsDialogBinding

class BlackBoardDialogFragment(): DialogFragment(), View.OnClickListener {

    companion object {
        fun newInstance(title: String): BlackBoardDialogFragment {
            val frag = BlackBoardDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.setArguments(args)
            return frag
        }
    }

    private var _binding: OperationsDialogBinding? = null
    private val binding get() = _binding!!

    private val sum: Button by lazy { binding.sum }
    private val min: Button by lazy { binding.min }
    private val mul: Button by lazy { binding.mul }
    private val div: Button by lazy { binding.div }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OperationsDialogBinding.inflate(layoutInflater)
        val view = binding.root
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        inflater.inflate(R.layout.operations_dialog, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sum.setOnClickListener(this)
        min.setOnClickListener(this)
        mul.setOnClickListener(this)
        div.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onClick(v: View?) {
        var operator = when (v?.id) {
            R.id.sum -> OperatorEnum.SUM
            R.id.min -> OperatorEnum.MIN
            R.id.mul -> OperatorEnum.MUL
            R.id.div -> OperatorEnum.DIV
            else -> null
        }
        setFragmentResult("requestKey", bundleOf("operation" to operator))
        dialog?.dismiss()
    }

}