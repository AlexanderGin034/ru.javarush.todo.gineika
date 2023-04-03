package ru.javarush.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.entity.Task;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class TaskDao implements Dao<Task> {
    private SessionFactory sessionFactory;

    public TaskDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Task> findAll(int offset, int limit) {
        Query<Task> query = getSession().createQuery("select t from Task t", Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public int findAllCount() {
        Query<Long> query = getSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(getSession().find(Task.class, id));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveOrUpdate(Task task) {
        getSession().saveOrUpdate(task);
    }

    @Transactional
    @Override
    public void delete(Task task) {
        getSession().remove(task);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
