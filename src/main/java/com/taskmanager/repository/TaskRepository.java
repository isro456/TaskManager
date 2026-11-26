
package com.taskmanager.repository;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.model.TaskPriority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Фильтрация без сортировки
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByStatusAndPriority(
            TaskStatus status,
            TaskPriority priority
    );

    // Фильтрация с сортировкой
    List<Task> findByStatus(TaskStatus status, Sort sort);

    List<Task> findByPriority(TaskPriority priority, Sort sort);

    List<Task> findByStatusAndPriority(
            TaskStatus status,
            TaskPriority priority,
            Sort sort
    );

    List<Task> findByTitleContainingIgnoreCase(String title, Sort sort);

    @Query("""

            SELECT t FROM Task t
    WHERE (:status IS NULL OR t.status = :status)
      AND (:priority IS NULL OR t.priority = :priority)
      AND (
          :title = ''
          OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))
      )
    """)
    List<Task> searchTasks(
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            @Param("title") String title,
            Sort sort
    );
}

