package com.js.view_job

import android.app.Application
import com.js.view_job.db.AppDatabase
import com.js.view_job.repository.JobSiteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class JobSiteApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { AppDatabase.getInstance(applicationContext, applicationScope)}

    val jobSiteRepository by lazy { JobSiteRepository(database.jobSiteDao()) }
}