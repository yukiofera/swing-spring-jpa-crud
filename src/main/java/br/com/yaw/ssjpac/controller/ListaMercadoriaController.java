package br.com.yaw.ssjpac.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.yaw.ssjpac.action.AbstractAction;
import br.com.yaw.ssjpac.dao.MercadoriaDAO;
import br.com.yaw.ssjpac.event.AbstractEventListener;
import br.com.yaw.ssjpac.event.AtualizarListarMercadoriaEvent;
import br.com.yaw.ssjpac.event.BuscarMercadoriaEvent;
import br.com.yaw.ssjpac.event.DeletarMercadoriaEvent;
import br.com.yaw.ssjpac.event.IncluirMercadoriaEvent;
import br.com.yaw.ssjpac.model.Mercadoria;
import br.com.yaw.ssjpac.ui.ListaMercadoriasFrame;
import br.com.yaw.ssjpac.ui.SobreFrame;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.sjpac.controller.PersistenceController
 * 
 * @author YaW Tecnologia
 */
@Component
public class ListaMercadoriaController extends AbstractController {

	@Autowired
	private ListaMercadoriasFrame frame;
	private SobreFrame sobreFrame;
	
	@Autowired
	private IncluirMercadoriaController incluirController;

	@Autowired
	private BuscarMercadoriaController buscarController;
	
	@Autowired
	private MercadoriaDAO dao;
	
	public ListaMercadoriaController() {
	}
	
	@PostConstruct
	private void init() {
		this.frame.addWindowListener(this);
		this.sobreFrame = new SobreFrame();
		
		registerAction(frame.getNewButton(), new AbstractAction() {
			public void action() {
				incluirController.show();
			}
		});
		
		registerAction(frame.getRefreshButton(), new AbstractAction() {
			public void action() {
				fireEvent(new AtualizarListarMercadoriaEvent());
			}
		});
		
		registerAction(frame.getFindButton(), new AbstractAction() {
			public void action() {
				buscarController.show();
			}
		});
		
		registerAction(frame.getMenuSobre(), new AbstractAction() {
			@Override
			protected void action() {
				sobreFrame.setVisible(true);
			}
		});
		
		this.frame.getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					Mercadoria m = frame.getTable().getMercadoriaSelected();
					if (m != null) {
						incluirController.show(m);
					}
				}
			}
		});
		
		registerEventListener(IncluirMercadoriaEvent.class, new AbstractEventListener<IncluirMercadoriaEvent>() {
			public void handleEvent(IncluirMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						refreshTable();
					}
				});
			}
		});
		
		registerEventListener(DeletarMercadoriaEvent.class, new AbstractEventListener<DeletarMercadoriaEvent>() {
			public void handleEvent(DeletarMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						refreshTable();
					}
				});
			}
		});
		
		registerEventListener(AtualizarListarMercadoriaEvent.class, new AbstractEventListener<AtualizarListarMercadoriaEvent>() {
			public void handleEvent(AtualizarListarMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						refreshTable();
					}
				});
			}
		});
		
		registerEventListener(BuscarMercadoriaEvent.class, new AbstractEventListener<BuscarMercadoriaEvent>() {
			public void handleEvent(final BuscarMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						List<Mercadoria> list = event.getTarget();
						if (list != null) {
							frame.refreshTable(list);
						}
					}
				});
			}
		});
		
		refreshTable();
		this.frame.setVisible(true);
	}
	
	private void refreshTable() {
		this.frame.refreshTable(this.dao.findAll());
	}
	
}
