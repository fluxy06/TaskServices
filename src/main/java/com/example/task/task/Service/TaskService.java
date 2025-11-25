package com.example.task.task.Service;
import com.example.task.task.Domain.Status;
import java.time.LocalDate;
import com.example.task.task.Entity.Task;
import org.springframework.stereotype.Service;
import com.example.task.task.Domain.Priority;

@Service
public class TaskService {
    public Task getTaskByID(Long id) {
        return new Task(
            id,
            100L,
            100L,
            Status.CREATED,
            LocalDate.now(),
            LocalDate.now().plusDays(5),
            Priority.HEIGH
        );
    }
}
