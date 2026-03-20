package com.example.backend.Service.ServiceIMPL;

import com.example.backend.Entity.TaskEntity;
import com.example.backend.Entity.UserEntity;
import com.example.backend.Repository.TaskRepo;
import com.example.backend.Repository.UserRepo;
import com.example.backend.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceIMPL implements TaskService {

    @Autowired private TaskRepo taskRepo;
    @Autowired private UserRepo userRepo;

    private UserEntity getUser(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    @Override
    public TaskEntity createTask(TaskEntity task, String email) {
        UserEntity user = getUser(email);
        task.setUser(user);
        task.setCreatedat(LocalDateTime.now());
        return taskRepo.save(task);
    }

    @Override
    public TaskEntity updateTask(Long id, TaskEntity task, String email) {
        UserEntity user = getUser(email);
        TaskEntity existing = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to update this task");
        }

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus());
        return taskRepo.save(existing);
    }

    @Override
    public void deleteTask(Long id, String email) {
        UserEntity user = getUser(email);
        TaskEntity existing = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this task");
        }

        taskRepo.deleteById(id);
    }

    @Override
    public List<TaskEntity> getAllTasks(String email) {
        UserEntity user = getUser(email);
        return taskRepo.findByUser(user);
    }

    @Override
    public TaskEntity getTaskById(Long id, String email) {
        UserEntity user = getUser(email);
        TaskEntity task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to view this task");
        }

        return task;
    }
}