package com.js.view_job.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.js.view_job.R
import com.js.view_job.ui.list.JobSite

class JobSiteListDialogFragment(
    val jobSites: List<JobSite>,
    var dialogPositiveClick: (JobSite, DialogFragment) -> Unit,
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            activity
            val builder = AlertDialog.Builder(activity)
            builder
                .setTitle(getString(R.string.title_job_site_list))
                .setSingleChoiceItems(
                    jobSites.map { it.companyName }.toTypedArray(), 0
                ) { _, checkItem ->
                    dialogPositiveClick(jobSites[checkItem], this)
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}