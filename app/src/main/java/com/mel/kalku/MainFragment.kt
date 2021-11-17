package com.mel.kalku

import android.os.Bundle
import android.os.UserManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.mel.kalku.databinding.FragmentMainBinding
import kotlin.random.Random


class MainFragment : Fragment(R.layout.fragment_main), View.OnClickListener {

    private var _binding: FragmentMainBinding? = null
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

    var operation : OperationEnum = OperationEnum.MUL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ans1.setOnClickListener(this)
        ans2.setOnClickListener(this)
        ans3.setOnClickListener(this)
        ans4.setOnClickListener(this)
        ans5.setOnClickListener(this)
        ans6.setOnClickListener(this)

        answersBtnList = arrayListOf<Button>()
        answersBtnList.add(ans1); answersBtnList.add(ans2); answersBtnList.add(ans3)
        answersBtnList.add(ans4); answersBtnList.add(ans5); answersBtnList.add(ans6)

        MyCustomDialog().show(parentFragmentManager, "Kk")

        init()
    }





    fun init() {
        fillOut()
        showOperationSymbol()
    }

    fun showOperationSymbol(){
        //textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon, 0, 0, 0);
        when(operation){
            OperationEnum.SUM -> {
                operator.text = "+"
            }
            OperationEnum.MIN -> {
                operator.text = "-"
            }
            OperationEnum.MUL -> {
                operator.text = "X"
            }
            OperationEnum.DIV -> {
                operator.text = "/*/"
            }
        }
    }

    //random number generator between 2 and 12
    fun random() : Int {
        var ran = Random
        return ran.nextInt(2,12)
    }

    fun getAnswer(numerator : Int, denominator : Int) : Int {
        when(operation){
            OperationEnum.SUM -> {
                return numerator + denominator
            }
            OperationEnum.MIN -> {
                return numerator - denominator
            }
            OperationEnum.MUL -> {
                return numerator * denominator
            }
            OperationEnum.DIV -> {
                return numerator / denominator
            }
        }
    }


    fun fillOut() {

        var numeratorRandom = random()
        var denominatorRandom = random()
        var answer = getAnswer(numeratorRandom, denominatorRandom)

        //TODO: Don't get duplicated numbers
        var numbers = (1..5).map {
            if (it % 2 == 0) { answer + random() }
            else answer - random()
        } as ArrayList<Int>
        numbers.add(answer)
        numbers.shuffle()

        numerator.text = numeratorRandom.toString()
        denominator.text = denominatorRandom.toString()

        var i = 0
        answersBtnList.forEach{
            it.setText(numbers.get(i).toString())
            i++
        }
    }

    fun setEnableDisableBtn(value: Boolean) {
        answersBtnList.forEach {
            it.setEnabled(value)
        }
    }

    override fun onClick(view: View?) {

        var bnt : Button = view as Button

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

    private fun validateAnswer(answer : Int) : Boolean {

        val numerator : Int = Integer.parseInt(numerator.text as String)
        val denominator : Int = Integer.parseInt(denominator.text as String)

        when(operation){
            OperationEnum.SUM -> {
                return numerator + denominator == answer
            }
            OperationEnum.MIN -> {
                return numerator - denominator == answer
            }
            OperationEnum.MUL -> {
                return numerator * denominator == answer
            }
            OperationEnum.DIV -> {
                return numerator / denominator == answer
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}