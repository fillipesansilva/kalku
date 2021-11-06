package com.mel.kalku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mel.kalku.databinding.FragmentMainBinding
import kotlin.random.Random

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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

        var randomNumbers = getListOfRandomNumbers()
        binding.textView15.text =

    }

    private fun getListOfRandomNumbers() {
        val ran = Random
        val  numbers = (1..7).map { ran.nextInt() } as ArrayList<Int>
        numbers.add(numbers.get(0) + numbers.get(1))
        return numbers.shuffle()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}