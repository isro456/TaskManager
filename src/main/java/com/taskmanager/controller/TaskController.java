
package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.model.TaskPriority;
import com.taskmanager.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String showTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false, defaultValue = "newest") String sort,
            @RequestParam(required = false) String title,
            Model model
    ) {
        List<Task> allTasks = taskService.getAllTasks();

        Sort sorting = switch (sort) {
            case "oldest" ->
                    Sort.by(Sort.Direction.ASC, "createdAt");
            case "dueSoon" ->
                    Sort.by(Sort.Direction.ASC, "dueDate");
            case "dueLate" ->
                    Sort.by(Sort.Direction.DESC, "dueDate");
            default ->
                    Sort.by(Sort.Direction.DESC, "createdAt");
        };

        if (title == null || title.isBlank()) {
            title = "";
        }

        // Единый вызов для поиска и фильтрации
        List<Task> tasks = taskService.searchTasks(
                status,
                priority,
                title,
                sorting
        );

        model.addAttribute("tasks", tasks);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedPriority", priority);
        model.addAttribute("selectedSort", sort);
        model.addAttribute("selectedTitle", title);

        // Счётчики задач
        model.addAttribute("todoCount", allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.TODO)
                .count());

        model.addAttribute("inProgressCount", allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
                .count());

        model.addAttribute("doneCount", allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE)
                .count());

        return "tasks/board";
    }

    @GetMapping("/tasks/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());

        return "tasks/create";
    }

    @PostMapping("/tasks")
    public String createTask(
            @Valid @ModelAttribute("task") Task task,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", TaskStatus.values());
            model.addAttribute("priorities", TaskPriority.values());

            return "tasks/create";
        }

        taskService.createTask(task);

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/edit")
    public String showEditForm(
            @PathVariable Long id,
            Model model
    ) {
        Task task = taskService.getTaskById(id);

        model.addAttribute("task", task);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());

        return "tasks/edit";
    }

    @PostMapping("/tasks/{id}/edit")
    public String updateTask(
            @PathVariable Long id,
            @Valid @ModelAttribute("task") Task task,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", TaskStatus.values());
            model.addAttribute("priorities", TaskPriority.values());

            return "tasks/edit";
        }

        taskService.updateTask(id, task);

        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

        return "redirect:/tasks";
    }
}
