package br.com.yaw.ssjpac.dao.generic.factory;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.PersistenceProvider;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author Marcelo Yukio Outa
 * @since 14/06/2013
 */
@NoRepositoryBean
public interface CustomJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    public JpaEntityInformation<T, ?> getEntityInformation();

    public Class<T> getPersistentClass();

    public Class<?> getSpringDataRepositoryInterface();

    public PersistenceProvider getProvider();
}
