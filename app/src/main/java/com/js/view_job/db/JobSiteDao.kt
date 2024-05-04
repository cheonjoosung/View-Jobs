package com.js.view_job.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.js.view_job.ui.list.JobSite
import kotlinx.coroutines.flow.Flow

@Dao
interface JobSiteDao {

    @Query("SELECT * FROM JobSite")
    fun getAll(): Flow<List<JobSite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(jobSite: JobSite)

    @Delete
    fun delete(jobSite: JobSite)
}