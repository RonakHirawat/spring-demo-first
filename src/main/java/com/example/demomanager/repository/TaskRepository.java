package com.example.demomanager.repository;

import com.example.demomanager.controller.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}