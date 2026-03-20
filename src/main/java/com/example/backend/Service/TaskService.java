package com.example.backend.Service;

import com.example.backend.Entity.TaskEntity;
import java.util.List;

public interface TaskService {
    TaskEntity createTask(TaskEntity task, String email);
    TaskEntity updateTask(Long id, TaskEntity task, String email);
    void deleteTask(Long id, String email);
    List<TaskEntity> getAllTasks(String email);
    TaskEntity getTaskById(Long id, String email);
}