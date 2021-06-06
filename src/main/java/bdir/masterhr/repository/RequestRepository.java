package bdir.masterhr.repository;

import bdir.masterhr.domain.Request;
import bdir.masterhr.domain.validators.RequestValidator;
import bdir.masterhr.domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import bdir.masterhr.utils.HibernateSession;

import java.util.List;

public class RequestRepository implements CrudRepository<String, Request> {
    SessionFactory sessionFactory;
    RequestValidator requestValidator;

    public RequestRepository() {
        this.sessionFactory = HibernateSession.getSessionFactory();
        this.requestValidator = new RequestValidator();
    }


    @Override
    public Request save(Request entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            requestValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(String.valueOf(entity.getIdRequest())) != null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public Request delete(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        Request request = findOne(id);
        if (request == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(request);
            session.getTransaction().commit();
            return request;
        }
    }

    @Override
    public Request update(Request entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(String.valueOf(entity.getIdRequest())) == null)
            return entity;
        try {
            requestValidator.validate(entity);
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
    public Request findOne(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Request> result = session.createQuery("select a from Request a where idRequest=:idRequest")
                    .setParameter("idRequest", Integer.parseInt(id))
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Request> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Request> result = session.createQuery("select a from Request a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
