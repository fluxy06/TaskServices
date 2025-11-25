package com.example.task.task.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.task.Entity.Task;
import com.example.task.task.Service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TaskController {
        private final TaskService taskService;

        public TaskController(TaskService taskService) {
            this.taskService = taskService;
        }

        @GetMapping("/{id}")
        public Task getTaskById(
            @PathVariable("id") Long id
        ) {
            System.out.println("request get start");
            return taskService.getTaskByID(id);
        }
        

}
