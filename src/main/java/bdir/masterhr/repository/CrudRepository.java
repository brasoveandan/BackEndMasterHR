package bdir.masterhr.repository;

import bdir.masterhr.domain.validators.Validator;

import java.util.List;

/**
 * CRUD operations masterhr.repository interface
 *
 * @param <T> - type E must have an attribute of type T
 * @param <E> - type of entities saved in masterhr.repository
 */
public interface CrudRepository<T, E> {

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws Validator.ValidationException if the entity is not valid
     * @throws IllegalArgumentException      if the given entity is null. *
     */
    E save(E entity) throws Validator.ValidationException;

    /**
     * removes the entity with the specified id
     *
     * @param id id must be not null
     * @return the removed entity or null if there is no entity with the
     * given id
     * @throws IllegalArgumentException if the given entity is null.
     */
    E delete(T id);

    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not
     * exist).
     * @throws IllegalArgumentException      if the given entity is null.
     * @throws Validator.ValidationException if the entity is not valid.
     */
    E update(E entity) throws Validator.ValidationException;

    /**
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException if id is null.
     */
    E findOne(T id);

    /**
     * @return all entities
     */
    List<E> findAll();
}