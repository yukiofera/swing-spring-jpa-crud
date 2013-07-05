package br.com.yaw.ssjpac.controller;

import br.com.yaw.ssjpac.action.AbstractAction;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.yaw.ssjpac.dao.MercadoriaDAO;
import br.com.yaw.ssjpac.event.BuscarMercadoriaEvent;
import br.com.yaw.ssjpac.model.Mercadoria;
import br.com.yaw.ssjpac.ui.BuscaMercadoriaFrame;
import java.util.List;

/**
 * Define a
 * <code>Controller</code> responsável por gerir a tela de inclusão/edição de
 * <code>Mercadoria</code>.
 *
 * <p>
 * <code>IncluirMercadoriaController</code> é mapeada como
 * <code>@Component</code> do Spring. Dessa forma uma instância de <code>IncluirMercadoriaController</code> pode ser criada e gerenciada pelo Spring,
 * favorecendo a Inversão de Controle <i>(IoC)</i> e Injeção de Dependência <i>(DI)</i>.
 * </p>
 *
 * <p>
 * Essa <code>Controller</code> depende de outros componentes da camada <code>Model</code> (DAO) e <code>View</code> (telas). As dependências são resolvidas
 * pelo Spring, através da <strong>Injeção de Dependência</strong> c/ a anotação <code>@Autowired</code>.
 * </p>
 *
 * @see br.com.yaw.sjpac.controller.PersistenceController
 *
 * @author YaW Tecnologia
 */
@Component
public class BuscarMercadoriaTesteController extends AbstractController {

    @Autowired
    private BuscaMercadoriaFrame frame;
    @Autowired
    private MercadoriaDAO dao;

    @Autowired
    public BuscarMercadoriaTesteController(ListaMercadoriaController parent) {
        super(parent);
    }

    @PostConstruct
    private void init() {
        this.frame.addWindowListener(this);

        registerAction(frame.getBuscarButton(), new AbstractAction() {
            private List<Mercadoria> list;

            @Override
            public void action() {
                if (frame.getText().length() > 0) {
                    list = dao.getMercadoriasByNome(frame.getText().concat("%"));
                }
            }

            @Override
            public void posAction() {
                cleanUp();
                fireEvent(new BuscarMercadoriaEvent(list));
                list = null;
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    @Override
    protected void cleanUp() {
        frame.setVisible(false);
        frame.resetForm();

        super.cleanUp();
    }
}
