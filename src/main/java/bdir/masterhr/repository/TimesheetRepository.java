package bdir.masterhr.repository;

import bdir.masterhr.domain.Timesheet;
import bdir.masterhr.domain.validators.TimesheetValidator;
import bdir.masterhr.domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import bdir.masterhr.utils.HibernateSession;

import java.util.List;

public class TimesheetRepository implements CrudRepository<String, Timesheet> {
    SessionFactory sessionFactory;
    TimesheetValidator timesheetValidator;

    public TimesheetRepository() {
        this.sessionFactory = HibernateSession.getSessionFactory();
        this.timesheetValidator = new TimesheetValidator();
    }

    @Override
    public Timesheet save(Timesheet entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            timesheetValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(entity.getIdTimesheet()) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public Timesheet delete(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        Timesheet timesheet = findOne(id);
        if (timesheet == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(timesheet);
            session.getTransaction().commit();
            return timesheet;
        }
    }

    @Override
    public Timesheet update(Timesheet entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getIdTimesheet()) == null)
            return entity;
        try {
            timesheetValidator.validate(entity);
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.update(entity);
                session.getTransaction().commit();
                return null;
            }
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
    }

    @Override
    public Timesheet findOne(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Timesheet> result = session.createQuery("select a from Timesheet a where idTimesheet=:idTimesheet")
                    .setParameter("idTimesheet", id)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Timesheet> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Timesheet> result = session.createQuery("select a from Timesheet a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
