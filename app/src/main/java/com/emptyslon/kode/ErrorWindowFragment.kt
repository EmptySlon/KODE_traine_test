package com.emptyslon.kode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emptyslon.kode.contract.navigator
import com.emptyslon.kode.databinding.FragmentErrorWindowBinding


class ErrorWindowFragment : Fragment() {

    lateinit var binding: FragmentErrorWindowBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentErrorWindowBinding.inflate(inflater, container, false)

        binding.errTxRebut.setOnClickListener {
            navigator().restartFragment()
        }


        return binding.root
    }

}