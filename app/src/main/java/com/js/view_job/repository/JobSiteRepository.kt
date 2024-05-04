package com.js.view_job.repository

import androidx.annotation.WorkerThread
import com.js.view_job.db.JobSiteDao
import com.js.view_job.ui.list.JobSite
import kotlinx.coroutines.flow.Flow

class JobSiteRepository(private val jobSiteDao: JobSiteDao) {

    val allJobSites: Flow<List<JobSite>> = jobSiteDao.getAll()

    @WorkerThread
    suspend fun insertOrUpdate(jobSite: JobSite) {
        jobSiteDao.insertOrUpdate(jobSite)
    }

    @WorkerThread
    suspend fun delete(jobSite: JobSite) {
        jobSiteDao.delete(jobSite)
    }
}