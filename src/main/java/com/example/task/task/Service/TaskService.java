package com.example.task.task.Service;
import com.example.task.task.Domain.Status;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

import com.example.task.task.Entity.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.task.task.Domain.Priority;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final HashMap<Long, Task> tasks = new HashMap<>();
    private Long nextId = 1L;


    public Task postCreateTask(Long creatorId, Long assignedUserId, Status status,
                               LocalDate createDateTime, LocalDate deadlineDate, Priority priority) {
        Long id = nextId++;
        Task task = new Task(id, creatorId, assignedUserId, status, createDateTime, deadlineDate, priority);
        tasks.put(id, task);
        return task;
    }

    public Collection<Task> getAllTasks() {
            return tasks.values();
    }


    public Task getTaskByID(Long id) {
        log.info("method getTaskById is called with id =" + id);
        Task task = tasks.get(id);
        if (task == null) {
                log.warn("task with id: " + id + " is not find");
                return task;
        }else {
            return task;
        }

    }
}