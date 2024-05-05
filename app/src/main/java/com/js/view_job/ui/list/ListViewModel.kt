package com.js.view_job.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.view_job.repository.JobSiteRepository
import com.js.view_job.ui.view.ViewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(private val repository: JobSiteRepository) : ViewModel() {

    val jobSites: LiveData<List<JobSite>> = repository.allJobSites.asLiveData()

    fun insert(jobSite: JobSite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(jobSite)
        }
    }

    fun update(jobSite: JobSite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(jobSite)
        }
    }

    fun delete(jobSite: JobSite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(jobSite)
        }
    }
}

class MyViewModelFactory(private val repository: JobSiteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ViewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}