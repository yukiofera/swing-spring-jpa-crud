package br.com.yaw.ssjpac.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.yaw.ssjpac.model.Mercadoria;

/**
 * Contrato de persistência para a entidade <code>Mercadoria</code>. 
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * <p>
 *  As operações básicas do cadastro são definidas indiretamente pela interface pai, <code>JpaRepository</code>.
 *  O Spring Data JPA gera a classe (proxy) que implementa essa interface. Dessa forma métodos especificos do cadastro, como
 *  <code>getMercadoriasByNome</code>, devem mapear a consulta <code>JPA-QL</code>.
 * </p>
 * 
 * @author YaW Tecnologia
 */
public interface MercadoriaDAO extends JpaRepository<Mercadoria, Integer> {

	/**
	 * Utiliza anotação <code>Query</code> do Spring Data JPA pra definir o código SQL.
	 * @param nome Filtro da pesquisa de mercadorias.
	 * @return Lista de mercadorias com filtro em nome.
	 * @throws <code>RuntimeException</code> se algum problema ocorrer.
	 */
	@Query("select m from Mercadoria m where m.nome like ?1")
	List<Mercadoria> getMercadoriasByNome(String nome);

}
