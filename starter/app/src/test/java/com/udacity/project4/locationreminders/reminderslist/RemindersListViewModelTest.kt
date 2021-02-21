package com.udacity.project4.locationreminders.reminderslist

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    //TODO: provide testing to the RemindersListViewModel and its live data objects

    private lateinit var remindersListViewModel: RemindersListViewModel
    private lateinit var dataSource: FakeDataSource

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        dataSource = FakeDataSource()
        dataSource.reminders = createRemindersForTesting()
        remindersListViewModel = RemindersListViewModel(
            ApplicationProvider.getApplicationContext(),
            dataSource)
    }

    private fun createRemindersForTesting(): MutableList<ReminderDTO> {

        val r1 = ReminderDTO("Call a friend","calling ...",
                "Parc de La Victoire",
                36.74208242672705,3.072958588600159)

        val r2 = ReminderDTO("Do this task","the task",
                "camp international de scouts musulmans…",
                36.74985838811116,2.8545945882797237)

        return mutableListOf(r1, r2)
    }

    @Test
    fun loadReminders_loadingData()= runBlockingTest {

        //Given - adding 3 reminders in setupViewModel function

        // When - calling loadReminds
        remindersListViewModel.loadReminders()
        // Then - the viewModel have some data
        assert(remindersListViewModel.remindersList.value?.size!! > 0)

    }

    @Test
    fun invalidateShowNoData_noData_UpdateShowNoDataValue() = runBlockingTest {

        // Given - repo with 3 reminders

        // When - remove all reminders
        dataSource.deleteAllReminders()
        remindersListViewModel.loadReminders()

        // Then - showNoData should be true
        assert(remindersListViewModel.showNoData.value!!)

    }

}