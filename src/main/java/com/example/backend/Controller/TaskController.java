package com.example.backend.Controller;

import com.example.backend.Entity.TaskEntity;
import com.example.backend.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    @Autowired private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskEntity> create(@RequestBody TaskEntity task,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.createTask(task, userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskEntity> update(@PathVariable Long id,
                                             @RequestBody TaskEntity task,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.updateTask(id, task, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        taskService.deleteTask(id, userDetails.getUsername());
        return ResponseEntity.ok("Task deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<TaskEntity>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.getAllTasks(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getById(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.getTaskById(id, userDetails.getUsername()));
    }
}