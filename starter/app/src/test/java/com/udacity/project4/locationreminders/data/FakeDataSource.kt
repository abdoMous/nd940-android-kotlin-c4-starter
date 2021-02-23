package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import java.lang.Exception

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderDataSource {

    var reminders = mutableListOf<ReminderDTO>()

    private var shouldReturnError = false

    fun setReturnError (value: Boolean){
        shouldReturnError = value
    }

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if(shouldReturnError){
            return Result.Error("Exception getReminders")
        }
        return Result.Success(ArrayList(reminders))
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {

        if(shouldReturnError){
            return Result.Error("Exception getReminder")
        }

        val reminder = reminders.find { it.id == id }
        return if(reminder != null){
            Result.Success(reminder)
        }else {
            Result.Error("Did not find Reminder")
        }
    }

    override suspend fun deleteAllReminders() {
        reminders.clear()
    }

}