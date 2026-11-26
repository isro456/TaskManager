
package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.model.TaskPriority;
import com.taskmanager.repository.TaskRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Получить все задачи
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Получить все задачи с сортировкой
    public List<Task> getAllTasks(Sort sort) {
        return taskRepository.findAll(sort);
    }

    // Создать задачу
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Получить задачу по ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found: " + id));
    }

    // Обновить задачу
    public Task updateTask(Long id, Task updatedTask) {

        Task task = getTaskById(id);

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());
        task.setDueDate(updatedTask.getDueDate());

        return taskRepository.save(task);
    }

    // Удалить задачу
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Фильтрация без сортировки
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }

    public List<Task> getTasksByStatusAndPriority(
            TaskStatus status,
            TaskPriority priority
    ) {
        return taskRepository.findByStatusAndPriority(status, priority);
    }

    // Фильтрация с сортировкой
    public List<Task> getTasksByStatus(TaskStatus status, Sort sort) {
        return taskRepository.findByStatus(status, sort);
    }

    public List<Task> getTasksByPriority(TaskPriority priority, Sort sort) {
        return taskRepository.findByPriority(priority, sort);
    }

    public List<Task> getTasksByStatusAndPriority(
            TaskStatus status,
            TaskPriority priority,
            Sort sort
    ) {
        return taskRepository.findByStatusAndPriority(
                status, priority, sort
        );
    }
    public List<Task> searchTasksByTitle(String title, Sort sort) {
        return taskRepository.findByTitleContainingIgnoreCase(title, sort);
    }
    public List<Task> searchTasks(
            TaskStatus status,
            TaskPriority priority,
            String title,
            Sort sort
    ) {
        return taskRepository.searchTasks(status, priority, title, sort);
    }
}
