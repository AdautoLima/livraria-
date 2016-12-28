package br.com.prova.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prova.livraria.dao.AutorDao;
import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.tx.Transacional;


@Named
@ViewScoped
public class AutorBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Autor autor = new Autor();
	private Integer autorId;
	private List<Autor> autores;
	
	@Inject
	private AutorDao dao;
	
	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Autor getAutor() {
		return autor;
	}

	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}
		
	@Transacional
	public void gravar() {
		if (this.autor.getId() == null) {
			this.dao.adiciona(this.autor);
		} else {
			this.dao.atualiza(this.autor);
		}
		this.autores = dao.listaTodos();
		this.autor = new Autor();

	}

	public void carregar(Autor autor) {
		this.autor = autor;
	}
	
	@Transacional
	public void remover(Autor autor) {
		this.dao.remove(autor);
	}
	
	public void carregarAutorPeloId() {
		this.autor = this.dao.buscaPorId(autorId);
	}
}
