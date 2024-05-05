package com.js.view_job.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.js.view_job.JobSiteApplication
import com.js.view_job.common.JobSiteDialogFragment
import com.js.view_job.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: ListViewModel by viewModels { ListViewModelFactory((requireActivity().application as JobSiteApplication).jobSiteRepository) }

    private lateinit var jobSiteListAdapter: JobSiteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initSearchView()

        initJobSiteListView()

        initObserveLiveData()

        binding.floatingActionButton.setOnClickListener {
            showInputDialog(null, false)
        }

        return root
    }

    private fun initSearchView() {
        binding.searchView.apply {
            isSubmitButtonEnabled = false

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return if (query.isNullOrEmpty()) false
                    else {
                        Log.e("CJS", "onQueryTextSubmit query=$query")
                        true
                    }
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) return false
                    else {
                        Log.e("CJS", "onQueryTextChange newText=$newText")
                        return true
                    }
                }

            })
        }
    }

    private fun initJobSiteListView() {
        jobSiteListAdapter = JobSiteListAdapter(
            jobSiteClickListener = { jobSite ->
                val action = ListFragmentDirections.goToViewFragment(jobSite.companyUrl ?: "")
                findNavController().navigate(action)
            },
            jobSiteLongClickListener = { jobSite ->
                showInputDialog(jobSite, true)
            }
        )

        binding.jobRecyclerView.adapter = jobSiteListAdapter
    }

    private fun initObserveLiveData() {
        listViewModel.jobSites.observe(viewLifecycleOwner) { jobSites ->
            if (::jobSiteListAdapter.isInitialized) {
                jobSiteListAdapter.submitList(jobSites)
            }
        }
    }

    private fun showInputDialog(jobSite: JobSite?, isUpdate: Boolean = false) {
        val dialog = JobSiteDialogFragment(
            jobSite = jobSite,
            dialogNegativeClick = {
                Log.e("CJS", "negative clicked")
            },
            dialogPositiveClick = { input, _ ->
                Log.e("CJS", "positive clicked jobSite=$input")
                if (isUpdate) {
                    listViewModel.update(input)
                } else {
                    listViewModel.insert(input)
                }
            }
        )

        dialog.show(parentFragmentManager, "JobSiteDialogFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}