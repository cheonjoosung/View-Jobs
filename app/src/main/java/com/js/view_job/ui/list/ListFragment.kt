package com.js.view_job.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.js.view_job.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val listViewModel =
            ViewModelProvider(this)[ListViewModel::class.java]

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initSearchView()

        initJobSiteListView()

        return root
    }

    private fun initSearchView() {
        binding.searchView.apply {
            isSubmitButtonEnabled = false

            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return if (query.isNullOrEmpty())  false
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
        val jobSiteListAdapter = JobSiteListAdapter(
            jobSiteClickListener = { jobSite ->
                Log.e("CJS", "item clicked $jobSite")
            },
            jobSiteLongClickListener = { jobSite ->
                Log.e("CJS", "item clicked $jobSite")
            }
        )

        binding.jobRecyclerView.adapter = jobSiteListAdapter
        jobSiteListAdapter.submitList(getSampleList())
    }

    private fun getSampleList(): List<JobSite> {
        val mutableList = mutableListOf<JobSite>()
        repeat(5) {
            mutableList.add(JobSite(companyName = "삼성 $it", companyUrl = "삼성 URL $it"))
        }

        return mutableList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}