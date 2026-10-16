package com.example.demomanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demomanager.controller.Task;
import com.example.demomanager.exception.DuplicateTitleException;
import com.example.demomanager.exception.TaskNotFoundException;
import com.example.demomanager.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void findById_returnsTask_whenPresent() {
        Task task = new Task("Z", "TODO");
        when(repository.findById(1)).thenReturn(Optional.of(task));

        Task result = service.findById(1);

        assertEquals("Z", result.getTitle());
    }

    @Test
    void findById_throws_whenMissing() {
        when(repository.findById(9)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> service.findById(9));
    }

    @Test
    void create_throws_whenTitleAlreadyExists() {
        Task existing = new Task("A", "TODO");
        when(repository.findAll()).thenReturn(List.of(existing));

        assertThrows(DuplicateTitleException.class, () -> service.create("a", "TODO"));
    }

    @Test
    void create_saves_whenTitleIsNew() {
        Task saved = new Task("B", "TODO");
        when(repository.findAll()).thenReturn(List.of());
        when(repository.save(any(Task.class))).thenReturn(saved);

        Task result = service.create("B", "TODO");

        assertEquals("B", result.getTitle());
    }

    @Test
    void delete_throws_whenIdDoesNotExist() {
        when(repository.existsById(5)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> service.delete(5));
    }
    @Test
    void findAll_returnsOnlyMatchingTasks_whenStatusGiven() {
        Task first = new Task("Java", "TODO");
        Task second = new Task("Spring", "DONE");

        when(repository.findAll()).thenReturn(List.of(first, second));

        List<Task> result = service.findAll("todo");

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getTitle());
    }
}