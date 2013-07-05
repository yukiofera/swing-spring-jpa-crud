package br.com.yaw.ssjpac.dao.generic.factory.impl;

import br.com.yaw.ssjpac.dao.generic.factory.CustomJpaRepository;
import java.io.Serializable;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.PersistenceProvider;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author Marcelo Yukio Outa
 * @since 14/06/2013
 */
@NoRepositoryBean
public class CustomJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>, Serializable {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CustomJpaRepositoryImpl.class);
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager entityManager;
    private final Class<?> springDataRepositoryInterface;
    private final PersistenceProvider provider;

    /**
     * Creates a new {@link SimpleJpaRepository} to manage objects of the given {@link JpaEntityInformation}.
     *
     * @param entityInformation
     * @param entityManager
     */
    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, Class<?> springDataRepositoryInterface) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
        this.springDataRepositoryInterface = springDataRepositoryInterface;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }

    public JpaEntityInformation<T, ?> getEntityInformation() {
        return entityInformation;
    }

    public Class<T> getPersistentClass() {
        return entityInformation.getJavaType();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<?> getSpringDataRepositoryInterface() {
        return springDataRepositoryInterface;
    }

    public PersistenceProvider getProvider() {
        return provider;
    }
}
