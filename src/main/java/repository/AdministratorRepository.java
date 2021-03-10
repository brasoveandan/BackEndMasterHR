package repository;

import domain.Administrator;
import domain.validators.AdministratorValidator;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class AdministratorRepository implements CrudRepository<String, Administrator> {
    SessionFactory sessionFactory;
    AdministratorValidator administratorValidator;

    public AdministratorRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
        administratorValidator = new AdministratorValidator();
    }

    @Override
    public Administrator save(Administrator entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            administratorValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(entity.getUsernameEmployee()) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public Administrator delete(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        Administrator administrator = findOne(id);
        if (administrator == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(administrator);
            session.getTransaction().commit();
            return administrator;
        }
    }

    @Override
    public Administrator update(Administrator entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getUsernameEmployee()) == null )
            return entity;
        try {
            administratorValidator.validate(entity);
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
    public Administrator findOne(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Administrator> result = session.createQuery("select a from Administrator a where usernameEmployee=:usernameEmployee")
                    .setParameter("usernameEmployee", id)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Administrator> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Administrator> result = session.createQuery("select a from Administrator a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
