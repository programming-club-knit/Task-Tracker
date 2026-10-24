package com.hacktheweb.tasktrack.service;

import com.hacktheweb.tasktrack.exception.InvalidTaskException;
import com.hacktheweb.tasktrack.exception.TaskNotFoundException;
import com.hacktheweb.tasktrack.model.Task;
import com.hacktheweb.tasktrack.repository.TaskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    /** Newest tasks first. */
    public List<Task> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Task findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    /** Create: status defaults to PENDING and priority to MEDIUM when omitted. */
    public Task create(Task input) {
        Task task = new Task();
        task.setTitle(input.getTitle().trim());
        task.setDescription(cleanDescription(input.getDescription()));
        task.setStatus(input.getStatus() != null ? input.getStatus() : Task.Status.PENDING);
        task.setPriority(input.getPriority() != null ? input.getPriority() : Task.Priority.MEDIUM);
        task.setDueDate(input.getDueDate());
        return repository.save(task);
    }

    /**
     * Update (PUT = full replacement of editable fields). title, status and
     * priority are required. description and dueDate may be null to clear them.
     * id and createdAt are never changed.
     */
    public Task update(Long id, Task input) {
        if (input.getStatus() == null) {
            throw new InvalidTaskException("status", "Status is required");
        }
        if (input.getPriority() == null) {
            throw new InvalidTaskException("priority", "Priority is required");
        }
        Task task = findById(id);
        task.setTitle(input.getTitle().trim());
        task.setDescription(cleanDescription(input.getDescription()));
        task.setStatus(input.getStatus());
        task.setPriority(input.getPriority());
        task.setDueDate(input.getDueDate());
        return repository.save(task);
    }

    public void delete(Long id) {
        Task task = findById(id);
        repository.delete(task);
    }

    @Transactional
    public int deleteCompleted() {
        return repository.deleteByStatus(Task.Status.COMPLETED);
    }

    private String cleanDescription(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }
        return description.trim();
    }
}