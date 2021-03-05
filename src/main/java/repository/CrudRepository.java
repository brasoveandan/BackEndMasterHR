package repository;

import java.util.List;

public interface CrudRepository<T, E> {
    E save(E entity);

    E delete(E entity);

    E update(E entity);

    E findOne(T id);

    List<E> findAll();
}