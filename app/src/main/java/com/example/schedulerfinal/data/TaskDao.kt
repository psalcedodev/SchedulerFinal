package com.example.schedulerfinal.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.schedulerfinal.model.Task

@Dao
interface TaskDao {
    //This will ignore the data if there is a new similar task added to the Database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun readAllTask(): LiveData<List<Task>>

}