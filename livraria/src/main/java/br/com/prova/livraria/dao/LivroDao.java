package br.com.prova.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.prova.livraria.modelo.Livro;

public class LivroDao implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Livro> listaTodos() {
		return dao.listaTodos();
	}

	@Inject
	EntityManager em;
	
	private DAO<Livro> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Livro>(this.em, Livro.class);
	}

	public void adiciona(Livro t) {
		dao.adiciona(t);
	}

	public void remove(Livro t) {
		dao.remove(t);
	}

	public void atualiza(Livro t) {
		dao.atualiza(t);
	}
	
	public Livro buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	/*
	public List<Livro> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}
	*/
	
}
