package com.example.task.task.Entity;
import java.time.LocalDate;

import com.example.task.task.Domain.Priority;
import com.example.task.task.Domain.Status;

// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;

// @Entity
public record Task(
    Long id,
    Long creatorId,
    Long assignedUserId,
    Status status,
    LocalDate createDateTime,
    LocalDate deadlineDate,
    Priority priority
) {
    
}

