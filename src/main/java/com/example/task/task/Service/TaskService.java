package com.example.task.task.Service;
import com.example.task.task.Domain.Status;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.example.task.task.Entity.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.task.task.Domain.Priority;


@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final HashMap<Long, Task> tasks = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);


    public Task postCreateTask(Long creatorId, Long assignedUserId, Status status,
                               LocalDate createDateTime, LocalDate deadlineDate, Priority priority) {
        try {
            if (deadlineDate.isBefore(createDateTime)) {
                throw new IllegalArgumentException(
                    "deadline date: " + deadlineDate + " cannot be before create date: " + createDateTime
                );} else if (status != status.CREATED) {
                    throw new IllegalArgumentException(
                            "status of created task then you created she should be 'created', but this tusk has status: " + status 
                    );
                }
            
            Long id = nextId.getAndIncrement();
            Task task  = new Task(
                id, 
                creatorId,
                assignedUserId, 
                status,
                createDateTime,
                deadlineDate,
                priority
            );
            tasks.put(id, task);
            return task;
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public Collection<Task> getAllTasks() {
            return tasks.values();
    }


    public Task getTaskByID(Long id) {
        try {
        log.info("method getTaskById is called with id = " + id);
        Task task = tasks.get(id);
        if (task == null) {
                throw new IllegalArgumentException("Task with id: " + id + ", is't find");
        }
        return task;
    } catch(IllegalArgumentException e ) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    }
}