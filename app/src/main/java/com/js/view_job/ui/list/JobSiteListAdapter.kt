package com.js.view_job.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.js.view_job.databinding.ItemJobSiteBinding

class JobSiteListAdapter(
    val jobSiteClickListener: (JobSite) -> Unit,
    val jobSiteLongClickListener: (JobSite) -> Unit,
) : ListAdapter<JobSite, JobSiteListAdapter.JobSiteViewHolder>(diffUtils) {

    inner class JobSiteViewHolder(private val binding: ItemJobSiteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobSite) {
            val position = adapterPosition + 1
            binding.numberTextView.text = position.toString()
            binding.companyNameTextView.text = item.companyName
            binding.companyUrlTextView.text = item.companyUrl

            binding.root.setOnLongClickListener {
                jobSiteLongClickListener.invoke(item)
                false
            }

            binding.root.setOnClickListener {
                jobSiteClickListener.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobSiteViewHolder {
        val binding = ItemJobSiteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobSiteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobSiteViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtils = object : ItemCallback<JobSite>() {
            override fun areItemsTheSame(oldItem: JobSite, newItem: JobSite): Boolean {
                return oldItem.companyUrl == newItem.companyUrl
            }

            override fun areContentsTheSame(oldItem: JobSite, newItem: JobSite): Boolean {
                return oldItem == newItem
            }

        }
    }
}