package com.js.view_job.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.view_job.repository.JobSiteRepository
import com.js.view_job.ui.list.JobSite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewViewModel(private val repository: JobSiteRepository) : ViewModel() {

    val jobSites: LiveData<List<JobSite>> = repository.allJobSites.asLiveData()

    fun getJobSiteAll(callBack: (List<JobSite>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            callBack.invoke(repository.getAllList())
        }
    }
}