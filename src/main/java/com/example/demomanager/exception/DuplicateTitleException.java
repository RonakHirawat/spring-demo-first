package com.example.demomanager.exception;

public class DuplicateTitleException extends RuntimeException{
    public DuplicateTitleException(String title){
        super("A task with the title  "+title+" Already exists ");
    }
}
