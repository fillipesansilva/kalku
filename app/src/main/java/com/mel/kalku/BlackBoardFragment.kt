package com.mel.kalku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.mel.kalku.databinding.BlackBoardFragmentBinding


class BlackBoardFragment : Fragment(R.layout.black_board_fragment), View.OnClickListener {

    private var _binding: BlackBoardFragmentBinding? = null
    private val binding get() = _binding!!

    private val ans1: Button by lazy { binding.ans1 }
    private val ans2: Button by lazy { binding.ans2 }
    private val ans3: Button by lazy { binding.ans3 }
    private val ans4: Button by lazy { binding.ans4 }
    private val ans5: Button by lazy { binding.ans5 }
    private val ans6: Button by lazy { binding.ans6 }

    private val playAgain: Button by lazy { binding.playAgain }

    private val result: TextView by lazy { binding.result }
    private val operator: TextView by lazy { binding.operator }

    private lateinit var answersBtnList : ArrayList<Button>

    private val numerator: TextView by lazy { binding.numerator }
    private val denominator: TextView by lazy { binding.denominator }

    lateinit var operation : OperatorEnum

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BlackBoardFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()

        val blackBoardViewModel = ViewModelProvider(this).get(BlackBoardViewModelFragment::class.java)

        val blackBoardDialogFragmentViewModel =
            ViewModelProvider(this).get(BlackBoardDialogFragmentViewModel::class.java)
        blackBoardDialogFragmentViewModel.dialogCreate(parentFragmentManager, this, "Dialog Title")
        blackBoardDialogFragmentViewModel.operation.observe(viewLifecycleOwner, Observer {
            operation = it
            Toast.makeText(context, "" + operation, Toast.LENGTH_SHORT).show()
            showOperationSymbol()
            blackBoardViewModel.start(operation)
        })

        blackBoardViewModel.numerator.observe(viewLifecycleOwner, Observer {
            numerator.text = it
        })
        blackBoardViewModel.denominator.observe(viewLifecycleOwner, Observer {
            numerator.text = it
        })
        blackBoardViewModel.randomWrongAnswers.observe(viewLifecycleOwner, Observer { number : ArrayList<Int> ->
            answersBtnList.forEachIndexed{ index, button ->
                button.text = number.get(index).toString()
            }
        })
    }

    private fun setOnClickListener(){
        ans1.setOnClickListener(this)
        ans2.setOnClickListener(this)
        ans3.setOnClickListener(this)
        ans4.setOnClickListener(this)
        ans5.setOnClickListener(this)
        ans6.setOnClickListener(this)
        playAgain.setOnClickListener(this)

        answersBtnList = arrayListOf<Button>()
        answersBtnList.add(ans1); answersBtnList.add(ans2); answersBtnList.add(ans3)
        answersBtnList.add(ans4); answersBtnList.add(ans5); answersBtnList.add(ans6)
    }


    fun showOperationSymbol(){
        //textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon, 0, 0, 0);
        operator.text = when(operation){
            OperatorEnum.SUM ->  "+"
            OperatorEnum.MIN ->  "-"
            OperatorEnum.MUL ->  "X"
            OperatorEnum.DIV -> "/*/"
        }
    }

    override fun onClick(view: View?) {

        var bnt : Button = view as Button

        when(view.id){
            R.id.playAgain -> {
                setFragmentResult("resetKey", bundleOf("reset" to true))
                Toast.makeText(context, "kkkk", Toast.LENGTH_SHORT).show()
            }
            else -> {
                if (validateAnswer((bnt.text as String).toInt())) {
                    result.text = getText(R.string.correct_answer)
                    playAgain.visibility  = View.VISIBLE
                    setEnableDisableBtn(false)
                }else{
                    val transition: Transition = Fade()
                    transition.duration = 600
                    transition.addTarget(bnt)
                    TransitionManager.beginDelayedTransition(binding.answers, transition)
                    bnt.visibility = if (bnt.isShown) View.INVISIBLE else View.VISIBLE
                    result.text = getText(R.string.wrong_answer)
                }
            }
        }



    }

    fun setEnableDisableBtn(value: Boolean) {
        answersBtnList.forEach {
            it.setEnabled(value)
        }
    }

    private fun validateAnswer(answer : Int) : Boolean {
        val numerator : Int = Integer.parseInt(numerator.text as String)
        val denominator : Int = Integer.parseInt(denominator.text as String)
        when(operation){
            OperatorEnum.SUM -> return numerator + denominator == answer
            OperatorEnum.MIN -> return numerator - denominator == answer
            OperatorEnum.MUL -> return numerator * denominator == answer
            OperatorEnum.DIV -> return numerator / denominator == answer
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}