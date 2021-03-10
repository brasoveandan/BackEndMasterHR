package repository;

import domain.Contract;
import domain.validators.ContractValidator;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateSession;

import java.util.List;

public class ContractRepository implements CrudRepository<String, Contract> {
    SessionFactory sessionFactory;
    ContractValidator contractValidator;

    public ContractRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
        contractValidator = new ContractValidator();
    }

    @Override
    public Contract save(Contract entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            contractValidator.validate(entity);
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
    public Contract delete(String usernameEmployee) {
        if (usernameEmployee == null)
            throw new IllegalArgumentException();
        Contract contract = findOne(usernameEmployee);
        if (contract == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(contract);
            session.getTransaction().commit();
            return contract;
        }
    }

    @Override
    public Contract update(Contract entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getUsernameEmployee()) == null)
            return entity;
        try {
            contractValidator.validate(entity);
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
    public Contract findOne(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Contract> result = session.createQuery("select a from Contract a where usernameEmployee=:usernameEmployee")
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
    public List<Contract> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Contract> result = session.createQuery("select a from Contract a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}