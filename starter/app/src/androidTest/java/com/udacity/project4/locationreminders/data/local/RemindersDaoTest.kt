package com.udacity.project4.locationreminders.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test
import java.io.IOException
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

//    TODO: Add testing implementation to the RemindersDao.kt
    private lateinit var remindersDao: RemindersDao
    private lateinit var db: RemindersDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, RemindersDatabase::class.java).build()
        remindersDao = db.reminderDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeReminderAndReadInList(){
        val reminderId = UUID.randomUUID().toString()

        val reminder = ReminderDTO("Call a friend","calling ...",
                "Parc de La Victoire",
                36.74208242672705,3.072958588600159,
                reminderId)
        var reminderTitle :String? = ""
        runBlocking {
            remindersDao.saveReminder(reminder)

            reminderTitle = remindersDao.getReminderById(reminderId)?.title
        }
        assertThat(reminderTitle, equalTo("Call a friend"))

    }

}