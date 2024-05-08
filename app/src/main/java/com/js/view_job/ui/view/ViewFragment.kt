package com.js.view_job.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.js.view_job.JobSiteApplication
import com.js.view_job.common.JobSiteInputDialogFragment
import com.js.view_job.common.JobSiteListDialogFragment
import com.js.view_job.databinding.FragmentViewBinding
import com.js.view_job.ui.list.JobSite
import com.js.view_job.ui.list.MyViewModelFactory

class ViewFragment : Fragment() {

    private var _binding: FragmentViewBinding? = null
    private val binding get() = _binding!!

    private val args: ViewFragmentArgs by navArgs()

    private val viewViewModel: ViewViewModel by viewModels { MyViewModelFactory((requireActivity().application as JobSiteApplication).jobSiteRepository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val companyUrl = args.argsUrl
        Log.e(javaClass.simpleName, "companyUrl=$companyUrl")

        _binding = FragmentViewBinding.inflate(inflater, container, false)

        initWebView(companyUrl)

        initFloatingView()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(companyUrl: String) {
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

    private fun initFloatingView() {

        binding.siteFloatingActionButton.setOnClickListener {
            viewViewModel.getJobSiteAll { list ->
                showListDialog(list)
            }
        }
    }

    private fun showListDialog(jobSites: List<JobSite>) {
        val dialog = JobSiteListDialogFragment(
            jobSites = jobSites,
            dialogPositiveClick = { input, _ ->
                input.companyUrl?.let { url ->
                    binding.webView.loadUrl(url)
                }
            }
        )
        dialog.show(parentFragmentManager, "JobSiteListDialogFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}