package bdir.masterhr.repository;

import bdir.masterhr.domain.validators.ClockingValidator;
import bdir.masterhr.domain.validators.Validator;
import bdir.masterhr.utils.HibernateSession;
import bdir.masterhr.domain.Clocking;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ClockingRepository implements CrudRepository<String, Clocking> {
    SessionFactory sessionFactory;
    ClockingValidator clockingValidator;

    public ClockingRepository() {
        this.sessionFactory = HibernateSession.getSessionFactory();
        this.clockingValidator = new ClockingValidator();
    }

    @Override
    public Clocking save(Clocking entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            clockingValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (entity.getReason() != null && !entity.getReason().equals(""))
            delete(entity.getIdClocking());
        if (findOne(entity.getIdClocking()) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public Clocking delete(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        Clocking clocking = findOne(id);
        if (clocking == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(clocking);
            session.getTransaction().commit();
            return clocking;
        }
    }

    @Override
    public Clocking update(Clocking entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        Clocking clocking = findOne(entity.getIdClocking());
        if (clocking == null)
            return entity;
        try {
            clocking.setToHour(entity.getToHour());
            clockingValidator.validate(clocking);
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.update(clocking);
                session.getTransaction().commit();
                return null;
            }
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
    }

    @Override
    public Clocking findOne(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Clocking> result = session.createQuery("select a from Clocking a where idClocking=:idClocking")
                    .setParameter("idClocking", id)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Clocking> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Clocking> result = session.createQuery("select a from Clocking a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
