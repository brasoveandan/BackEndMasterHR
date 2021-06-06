package bdir.masterhr.repository;

import bdir.masterhr.domain.Payslip;
import bdir.masterhr.domain.validators.PayslipValidator;
import bdir.masterhr.domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import bdir.masterhr.utils.HibernateSession;

import java.util.List;

public class PayslipRepository implements CrudRepository<String, Payslip> {
    SessionFactory sessionFactory;
    PayslipValidator payslipValidator;

    public PayslipRepository() {
        this.sessionFactory = HibernateSession.getSessionFactory();
        this.payslipValidator = new PayslipValidator();
    }

    @Override
    public Payslip save(Payslip entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        try {
            payslipValidator.validate(entity);
        } catch (Validator.ValidationException exception) {
            throw new Validator.ValidationException(exception.getMessage());
        }
        if (findOne(entity.getIdPayslip()) != null)
            return entity;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return null;
        }
    }

    @Override
    public Payslip delete(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        Payslip payslip = findOne(id);
        if (payslip == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(payslip);
            session.getTransaction().commit();
            return payslip;
        }
    }

    @Override
    public Payslip update(Payslip entity) throws Validator.ValidationException {
        if (entity == null)
            throw new IllegalArgumentException();
        if (findOne(entity.getIdPayslip()) == null)
            return entity;
        try {
            payslipValidator.validate(entity);
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
    public Payslip findOne(String id) {
        if (id == null)
            throw new IllegalArgumentException();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Payslip> result = session.createQuery("select a from Payslip a where idPayslip=:idPayslip")
                    .setParameter("idPayslip", id)
                    .list();
            session.getTransaction().commit();
            if (!result.isEmpty())
                return result.get(0);
            else
                return null;
        }
    }

    @Override
    public List<Payslip> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Payslip> result = session.createQuery("select a from Payslip a").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
