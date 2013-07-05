package br.com.yaw.ssjpac.dao;

import br.com.yaw.ssjpac.dao.generic.AbstractRepository;
import br.com.yaw.ssjpac.model.Mercadoria;
import java.util.List;

/**
 *
 * @author Marcelo Yukio Outa
 * @since 13/06/2013
 */
public interface MercadoriaRepository extends AbstractRepository<Mercadoria, Long> {

    List<Mercadoria> getByNome(String nome);
}
