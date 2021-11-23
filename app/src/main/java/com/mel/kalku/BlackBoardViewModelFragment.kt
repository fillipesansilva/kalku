package com.mel.kalku

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class BlackBoardViewModelFragment : ViewModel() {

    private lateinit var operator : OperatorEnum

    private var _randomWrongAnswers = MutableLiveData<ArrayList<Int>>()
    val randomWrongAnswers : LiveData<ArrayList<Int>>
        get() = _randomWrongAnswers

    private var _denominator = MutableLiveData<String>()
    val denominator : LiveData<String>
        get() = _denominator

    private var _numerator = MutableLiveData<String>()
    val numerator : LiveData<String>
        get() = _numerator

    fun start(operator: OperatorEnum) {
        this.operator = operator
        fillOut()
    }

    //Return a List of random numbers to the blackboard.
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
        print("Fillipe " + numbers)
        _numerator.value = numeratorRandom.toString()
        _denominator.value = denominatorRandom.toString()
        _randomWrongAnswers.value = numbers
    }

    fun getAnswer(numerator : Int, denominator : Int) : Int {
        return when(operator){
            OperatorEnum.SUM ->  numerator + denominator
            OperatorEnum.MIN ->  numerator - denominator
            OperatorEnum.MUL -> numerator * denominator
            OperatorEnum.DIV -> numerator / denominator
        }
    }

    //random number generator between 2 and 12
    fun random() : Int {
        var ran = Random
        return ran.nextInt(2,12)
    }

}