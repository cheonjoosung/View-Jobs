package com.js.view_job.ui.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JobSite(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "companyName") var companyName: String?,
    @ColumnInfo(name = "companyUrl") var companyUrl: String?
) {
    constructor(companyName: String?, companyUrl: String?) : this(0, companyName, companyUrl)
}
