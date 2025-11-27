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
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.task.task.Domain.Priority;


@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final HashMap<Long, Task> tasks = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);


    public ResponseEntity<String> postCreateTask(Task task) {
        try {
            if (task.deadlineDate().isBefore(task.createDateTime())) {
                throw new IllegalArgumentException(
                    "deadline date: " + task.deadlineDate() + " cannot be before create date: " + task.createDateTime()
                );} else if (task.status() != null && task.status() != Status.CREATED) {
                    throw new IllegalArgumentException(
                            "status of created task, then you created should be have 'status: created', but this tusk has status: " + task.status()
                            + " you can don't use field of status, she generated automatically"
                    );
                }else if(task.id() != null) {
                    throw new IllegalArgumentException("id of task is generated automatically");
                }
            var newTask = new Task(
                nextId.getAndIncrement(),
                task.creatorId(),
                task.assignedUserId(),
                Status.CREATED,
                task.createDateTime(),
                task.deadlineDate(),
                task.priority()
            );

            tasks.put(newTask.id(), newTask);
            return ResponseEntity.ok("task with id: " + newTask.id() + "  was created");
            
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
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
    }

    
    public Task updateTask(Long id, Task task) {
            try {
                if (!tasks.containsKey(id)) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with this id: " + id + ", is't find");
                }
                Task updatedTask = new Task(
                    id,
                    task.creatorId(),
                    task.assignedUserId(),
                    task.status(),
                    task.createDateTime(),
                    task.deadlineDate(),
                    task.priority()
                );
                if (validDate(task.createDateTime(), task.deadlineDate())) {
                    tasks.put(id, updatedTask);
                    return updatedTask;
                }else  {
                    throw new IllegalArgumentException("create date is not can be more then deadline date");
                }
            }catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
                catch(Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
    }

    public void deleteTask(Long id) {
        try {
                if (!tasks.containsKey(id)) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "don't found task with id: " + id);
                }
                tasks.remove(id);
        }
        catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error when /delete/tasks with id: " + id);
        }
    }

    public boolean validDate(LocalDate start, LocalDate end) {
        if(!end.isBefore(start)) {
            return true;
        }else {
            return false;
        }
    }
}