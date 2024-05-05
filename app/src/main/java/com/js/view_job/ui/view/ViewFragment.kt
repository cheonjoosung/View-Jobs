package com.js.view_job.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.js.view_job.databinding.FragmentViewBinding

class ViewFragment : Fragment() {

    private var _binding: FragmentViewBinding? = null
    private val binding get() = _binding!!

    val args: ViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val companyUrl = args.argsUrl
        Log.e(javaClass.simpleName, "companyUrl=$companyUrl")

        _binding = FragmentViewBinding.inflate(inflater, container, false)

        initWebView()

        initFloatingView()

        return binding.root
    }

    private fun initWebView() {

    }

    private fun initFloatingView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}