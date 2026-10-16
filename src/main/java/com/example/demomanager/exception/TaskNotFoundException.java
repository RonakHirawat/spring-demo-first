package com.example.demomanager.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(int id){
        super("Task"+id+" not found");
    }
}
