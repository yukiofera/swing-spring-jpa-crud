package br.com.yaw.ssjpac.service.impl;

import br.com.yaw.ssjpac.dao.MercadoriaRepository;
import br.com.yaw.ssjpac.model.Mercadoria;
import br.com.yaw.ssjpac.service.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcelo Yukio Outa
 * @since 14/06/2013
 */
@Service
@Transactional(readOnly = true)
public class MercadoriaServiceImpl implements MercadoriaService {

    @Autowired
    private MercadoriaRepository repository;

    @Transactional
    public void save(Mercadoria mercadoria) {
        repository.save(mercadoria);

        // teste
//        if (true) {
//            throw new RuntimeException("teste @Transactional");
//        }

//        mercadoria = new Mercadoria();
//        mercadoria.setNome("zzzzzzzzzzz");
//        mercadoria.setDescricao("zzzz");
//        mercadoria.setPreco(5d);
//        mercadoria.setQuantidade(5);
//        repository.save(mercadoria);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    public List<Mercadoria> findAll() {
        return repository.findAll();
    }

    public List<Mercadoria> getByNome(String nome) {
        return repository.getByNome(nome);
    }
}
