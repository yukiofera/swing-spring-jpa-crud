package br.com.yaw.ssjpac.dao.impl;

import br.com.yaw.ssjpac.dao.MercadoriaRepository;
import br.com.yaw.ssjpac.dao.generic.impl.AbstractRepositoryImpl;
import br.com.yaw.ssjpac.model.Mercadoria;
import br.com.yaw.ssjpac.model.QMercadoria;
import com.mysema.query.jpa.impl.JPAQuery;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcelo Yukio Outa
 * @since 14/06/2013
 */
@Repository
@Transactional(readOnly = true)
public class MercadoriaRepositoryImpl extends AbstractRepositoryImpl<Mercadoria, Long> implements MercadoriaRepository {

    public List getByNome(String nome) {
        JPAQuery query = new JPAQuery(em);
        QMercadoria qMercadoria = QMercadoria.mercadoria;

        return query.from(qMercadoria)
                .where(qMercadoria.nome.startsWith(nome))
                .orderBy(qMercadoria.nome.asc())
                .list(qMercadoria);
    }
}
