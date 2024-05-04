package com.js.view_job.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.js.view_job.ui.list.JobSite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [JobSite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun jobSiteDao(): JobSiteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(JobSiteDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class JobSiteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        /**
         * Override the onCreate method to populate the database.
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // If you want to keep the data through app restarts,
            // comment out the following line.
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    Log.e(javaClass.simpleName, "DB 성공 이후 데이터 초기화")

                    val jobSites = mutableListOf<JobSite>().apply {
                        add(JobSite(companyName = "삼성", companyUrl = "https://www.samsungcareers.com/hr/"))
                        add(JobSite(companyName = "LG", companyUrl = "https://careers.lg.com/app/job/RetrieveJobNotices.rpi"))
                        add(JobSite(companyName = "현대차", companyUrl = "https://talent.hyundai.com/apply/applyList.hc?nfGubnC=ac85892205b92e8cecdc87185a3fbf039f04b2a7751ccf0e8a1f547d53b9945a"))
                        add(JobSite(companyName = "기아차", companyUrl = "https://career.kia.com/apply/applyList.kc?nfGubnC=37268bb0020007468bbc5a78ee13a6539b0a0a79bbfcdd7ad08406e8d43cd55f"))
                        add(JobSite(companyName = "SK", companyUrl = "https://www.skcareers.com/Recruit"))
                        add(JobSite(companyName = "CJ", companyUrl = "https://recruit.cj.net/"))
                    }.toTypedArray()

                    val dao = database.jobSiteDao()
                    dao.insertAll(*jobSites)
                }
            }
        }
    }
}