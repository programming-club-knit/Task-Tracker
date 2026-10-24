package com.hacktheweb.tasktrack.repository;

import com.hacktheweb.tasktrack.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    int deleteByStatus(Task.Status status);
}
