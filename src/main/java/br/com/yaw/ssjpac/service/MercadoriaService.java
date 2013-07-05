package br.com.yaw.ssjpac.service;

import br.com.yaw.ssjpac.model.Mercadoria;
import java.util.List;

/**
 *
 * @author Marcelo Yukio Outa
 * @since 14/06/2013
 */
public interface MercadoriaService {

    void save(Mercadoria mercadoria);

    void delete(Long id);

    List<Mercadoria> findAll();

    List<Mercadoria> getByNome(String nome);
}
