package com.example.demomanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demomanager.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demomanager.controller.Task;
import com.example.demomanager.repository.TaskRepository;
import com.example.demomanager.exception.DuplicateTitleException;
import com.example.demomanager.exception.TaskNotFoundException;
@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll(String status) {
        List<Task> all = repository.findAll();
        if (status == null) {
            return all;
        }
        List<Task> result = new ArrayList<>();
        for (Task task : all) {
            if (task.getStatus().equalsIgnoreCase(status)) {
                result.add(task);
            }
        }
        return result;
    }

    public Task findById(int id) {
        Optional<Task> found=repository.findById(id);
        if (found.isPresent()){
            return found.get();
        }
//        return repository.findById(id).orElse(null);
        throw new TaskNotFoundException(id);
    }

    public Task create(String title, String status) {
        for (Task existing : repository.findAll()) {
            if (existing.getTitle().equalsIgnoreCase(title)) {
                throw new DuplicateTitleException(title);
            }
        }
        Task task = new Task(title, status);
        return repository.save(task);
    }

    public Task update(int id, String title, String status) {
        Optional<Task> found = repository.findById(id);
        if (found.isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        Task task = found.get();
        task.setTitle(title);
        task.setStatus(status);
        return repository.save(task);
    }
    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }
}