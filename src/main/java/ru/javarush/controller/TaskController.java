package ru.javarush.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javarush.entity.Task;
import ru.javarush.service.TaskService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String tasks(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> tasks = taskService.findAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.findAllCount() / limit);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }

    @PostMapping("/{id}")
    public String update(Model model, @PathVariable Integer id, @RequestBody TaskInfo info) {
        if (Objects.isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }

        Task task = taskService.update(id, info.getDescription(), info.getStatus());
        return tasks(model, 1, 10);
    }

    @PostMapping("/")
    public String add(Model model, @RequestBody TaskInfo info) {
        Task task = taskService.create(info.getDescription(), info.getStatus());
        return tasks(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }

        taskService.delete(id);
        return tasks(model, 1, 10);
    }
}
