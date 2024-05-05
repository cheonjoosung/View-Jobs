package com.js.view_job.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.js.view_job.databinding.FragmentViewBinding

class ViewFragment : Fragment() {

    private var _binding: FragmentViewBinding? = null
    private val binding get() = _binding!!

    private val args: ViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val companyUrl = args.argsUrl
        Log.e(javaClass.simpleName, "companyUrl=$companyUrl")

        _binding = FragmentViewBinding.inflate(inflater, container, false)

        initWebView(companyUrl)

        initFloatingView()

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(companyUrl: String ) {
        binding.webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
        }

        if (companyUrl.isEmpty()) {
            binding.webView.loadUrl("https://www.samsungcareers.com/hr/")
        } else {
            binding.webView.loadUrl(companyUrl)
        }
    }

    // TODO ViewModel & repository 추가, DB에 있는 목록 보여주기
    private fun initFloatingView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}