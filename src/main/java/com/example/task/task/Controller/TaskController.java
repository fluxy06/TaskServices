package com.example.task.task.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.task.Domain.Priority;
import com.example.task.task.Domain.Status;
import com.example.task.task.Domain.TaskCreateRequest;
import com.example.task.task.Entity.Task;
import com.example.task.task.Service.TaskService;

import java.time.LocalDate;
import java.util.Collection;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
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
            log.info("/get tasks/{id=" + id + "}");
            return taskService.getTaskByID(id);
        }

        @GetMapping()
        public Collection<Task>  getAllTasks() {
            return taskService.getAllTasks();
        }
        
        @PostMapping()
public Task createTask(@RequestBody TaskCreateRequest request) {
    return taskService.postCreateTask(
        request.creatorId(),
        request.assignedUserId(),
        request.status(),
        LocalDate.now(),
        LocalDate.now().plusDays(5),
        request.priority()
    );
}

        

}
