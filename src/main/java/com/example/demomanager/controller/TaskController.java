package com.example.demomanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demomanager.dto.TaskRequest;
import com.example.demomanager.dto.TaskResponse;
import com.example.demomanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/api/tasks/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable int id) {
        return ResponseEntity.ok(toResponse(service.findById(id)));
    }

    @GetMapping("/api/tasks")
    public List<TaskResponse> getTasks(@RequestParam(required = false) String status) {
        List<TaskResponse> result = new ArrayList<>();
        for (Task task : service.findAll(status)) {
            result.add(toResponse(task));
        }
        return result;
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(toResponse(service.create(request.title(), request.status())));
    }

    @PutMapping("/api/tasks/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable int id,
                                                   @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(toResponse(service.update(id, request.title(), request.status())));
    }

    @DeleteMapping("/api/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getStatus());
    }
}