package com.example.demomanager.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
        @NotBlank(message = "title must not be blank") String title,
        @NotBlank(message = "status must not be blank") String status) {
}