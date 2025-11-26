package com.example.task.task.Domain;

public record TaskCreateRequest(
    Long creatorId,
    Long assignedUserId,
    Status status,
    Priority priority
) {
    
}
