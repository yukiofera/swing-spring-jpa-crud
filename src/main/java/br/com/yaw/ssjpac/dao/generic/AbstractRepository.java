package br.com.yaw.ssjpac.dao.generic;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Marcelo Yukio Outa
 * @since 14/06/2013
 */
public interface AbstractRepository<T, ID extends Serializable> {

    T save(T entity);

    List<T> save(Iterable<T> entities);

    void delete(ID id);

    void delete(T entity);

    T findById(ID id);

    List<T> findAll();

    Long count();
}
