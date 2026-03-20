package com.example.backend.Repository;

import com.example.backend.Entity.TaskEntity;
import com.example.backend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<TaskEntity,Long> {
    List<TaskEntity> findByUser(UserEntity user);
}
