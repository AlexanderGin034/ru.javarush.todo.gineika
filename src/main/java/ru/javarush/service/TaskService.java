package ru.javarush.service;

import org.springframework.stereotype.Service;
import ru.javarush.dao.TaskDao;
import ru.javarush.entity.Status;
import ru.javarush.entity.Task;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ServiceDao<Task> {
    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public List<Task> findAll(int offset, int limit) {
        return taskDao.findAll(offset, limit);
    }

    @Override
    public int findAllCount() {
        return taskDao.findAllCount();
    }

    @Override
    public Task update(int id, String description, Status status) {
        Optional<Task> taskOptional = taskDao.findById(id);
        if (!taskOptional.isPresent()) {
            throw new RuntimeException("Not found");
        }

        Task task = taskOptional.get();
        task.setDescription(description);
        task.setStatus(status);
        taskDao.saveOrUpdate(task);
        return task;
    }

    @Override
    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDao.saveOrUpdate(task);
        return task;
    }

    @Override
    public void delete(int id) {
        Optional<Task> taskOptional = taskDao.findById(id);
        if (!taskOptional.isPresent()) {
            throw new RuntimeException("Not found");
        }

        taskDao.delete(taskOptional.get());
    }

}
