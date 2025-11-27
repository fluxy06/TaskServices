package com.example.task.task.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.task.task.Domain.Priority;
import com.example.task.task.Domain.Status;
import com.example.task.task.Entity.Task;
import com.example.task.task.Service.TaskService;

import java.time.LocalDate;
import java.util.Collection;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/tasks")
@RestController
public class TaskController {
        private static final Logger log = LoggerFactory.getLogger(TaskController.class);
        private final TaskService taskService;
        public TaskController(TaskService taskService) {
            this.taskService = taskService;
        }

        @GetMapping("{id}")
        public Task getTaskById(
            @PathVariable("id") Long id
        ) {
            try {
                    return taskService.getTaskByID(id);
            }catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }

        @GetMapping()
        public Collection<Task>  getAllTasks() {
            return taskService.getAllTasks();
        }
        
        
    @PostMapping
public Task createTask(@RequestBody Task request) {
    try {
        return taskService.postCreateTask(
            request.creatorId(),
            request.assignedUserId(),
            request.status(),
            request.createDateTime(),
            request.deadlineDate(),
            request.priority()
        );
    } catch (IllegalArgumentException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}


}
