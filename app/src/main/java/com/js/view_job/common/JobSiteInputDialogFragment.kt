package com.js.view_job.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.js.view_job.R
import com.js.view_job.databinding.DialogInputJobSiteBinding
import com.js.view_job.ui.list.JobSite

class JobSiteInputDialogFragment(
    val jobSite: JobSite?,
    var dialogPositiveClick: (JobSite, DialogFragment) -> Unit,
    var dialogNegativeClick: (DialogFragment) -> Unit,
) : DialogFragment() {

    private var _binding: DialogInputJobSiteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            activity
            val builder = AlertDialog.Builder(activity)

            _binding = DialogInputJobSiteBinding.inflate(layoutInflater)

            jobSite?.let {
                binding.jobSiteNameEditText.setText(it.companyName)
                binding.jobSiteUrlEditText.setText(it.companyUrl)
            }

            builder.setView(binding.root)
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    val jobSiteName = binding.jobSiteNameEditText.text?.toString() ?: ""
                    val jobSiteUrl = binding.jobSiteUrlEditText.text?.toString() ?: ""

                    if (jobSiteName.isEmpty() || jobSiteUrl.isEmpty()) {
                        Toast.makeText(
                            activity,
                            getString(R.string.message_input_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    if (jobSite == null) {
                        val inputData = JobSite(jobSiteName, jobSiteUrl)
                        dialogPositiveClick(inputData, this)
                    } else {
                        val inputData = jobSite.copy(
                            uid = jobSite.uid,
                            companyName = jobSiteName,
                            companyUrl = jobSiteUrl
                        )
                        dialogPositiveClick(inputData, this)
                    }
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                    dialogNegativeClick(this)
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}