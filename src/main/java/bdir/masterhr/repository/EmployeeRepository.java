package bdir.masterhr.repository;

import bdir.masterhr.domain.Employee;
import bdir.masterhr.domain.validators.EmployeeValidator;
import bdir.masterhr.domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import bdir.masterhr.utils.HibernateSession;

import java.util.List;

public class EmployeeRepository implements CrudRepository<String, Employee> {
    SessionFactory sessionFactory;
    EmployeeValidator employeeValidator;

    public EmployeeRepository() {
        sessionFactory = HibernateSession.getSessionFactory();
        employeeValidator = new EmployeeValidator();
    }

    @Override
    public Employee save(Employee entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            employeeValidator.validateForAdmin(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(entity.getUsername()) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public Employee delete(String username) {
        if (username == null)
            throw new IllegalArgumentException();
        Employee employee = findOne(username);
        if (employee == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(employee);
            session.getTransaction().commit();
            return employee;
        }
    }

    @Override
    public Employee update(Employee entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getUsername()) == null)
            return entity;
        try {
            if (entity.getBankName().equals("ADMIN")) {
                entity.setBankName("");
                employeeValidator.validateForAdmin(entity);
            }
            else
                employeeValidator.validate(entity);
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.update(entity);
                session.getTransaction().commit();
                return null;
            }
        } catch (Validator.ValidationException e) {
            throw new Validator.ValidationException(e.getMessage());
        }
    }

    @Override
    public Employee findOne(String username) {
        if (username == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Employee> result = session.createQuery("select a from Employee a where username=:username")
                    .setParameter("username", username)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    public Employee findOneByEmail(String email) {
        if (email == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Employee> result = session.createQuery("select a from Employee a where mail=:mail")
                    .setParameter("mail", email)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Employee> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Employee> result = session.createQuery("select a from Employee a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}