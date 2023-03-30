package ru.javarush.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javarush.entity.Task;
import ru.javarush.service.TaskService;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String tasks(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> tasks = taskService.findAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping("/{id}")
    public void update(Model model, @PathVariable Integer id, @RequestBody TaskInfo info) {
        if (Objects.isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }

        taskService.update(id, info.getDescription(), info.getStatus());
    }

    @PostMapping("/")
    public void add(Model model, @RequestBody TaskInfo info) {
        Task task = taskService.create(info.getDescription(), info.getStatus());
    }

    @DeleteMapping("/{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }

        taskService.delete(id);
        return "tasks";
    }
}
