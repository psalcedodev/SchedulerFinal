package com.example.schedulerfinal.repository

import androidx.lifecycle.LiveData
import com.example.schedulerfinal.data.TaskDao
import com.example.schedulerfinal.model.Task

class TaskRepository(private var taskDao: TaskDao) {
    val readAllTask: LiveData<List<Task>> = taskDao.readAllTask()

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }
    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks(){
        taskDao.deleteAllTasks()
    }
}