package br.com.prova.livraria.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prova.livraria.dao.AutorDao;
import br.com.prova.livraria.dao.LivroDao;
import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.Livro;
import br.com.prova.livraria.tx.Transacional;

@Named
@ViewScoped
public class LivroBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;
	private List<Livro> livros;
	
	@Inject
	LivroDao livroDao;
	
	@Inject
	AutorDao autorDao;
	
	@Inject
	FacesContext context;
		
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}
	
	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	@Transacional
	public void remover(Livro livro) {
	    livroDao.remove(livro);
	    this.livros = livroDao.listaTodos();
	}	
	
	public void carregar(Livro livro) {
	    this.livro = this.livroDao.buscaPorId(livro.getId());
	}
		
	public void removerAutorDoLivro(Autor autor) {
	    this.livro.getAutores().remove(autor);
	}
	
	public void carregarLivroPeloId() {
		this.livro = livroDao.buscaPorId(livroId);
	}
		
	public List<Livro> getLivros() {
		if(this.livros == null){
			this.livros = livroDao.listaTodos();
		}	
		return livros;
	}
	
	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
	public void gravarAutor() {
        Autor autor = autorDao.buscaPorId(this.autorId);
        
        if (! livro.getAutores().isEmpty()) {
        	boolean incluir = true;
        	
        	for(int i = 0; i<=livro.getAutores().size()-1; i++){
        		Autor aut = livro.getAutores().get(i);
        		
        		if (aut.getId().equals(autor.getId())){
        			incluir = false;
        			break;
        		}
        	}	
        	
        	if (incluir) {
        		this.livro.adicionaAutor(autor);
        	}
        	
        }else{
        	this.livro.adicionaAutor(autor);
        }
	}
	
	
	@Transacional
	public void gravar() {
	    if (livro.getAutores().isEmpty()) {
	        context.addMessage("autor",
	                new FacesMessage("Livro deve ter pelo menos um Autor."));
	        return;
	    }

	    if (this.livro.getId() == null) {
	    	livroDao.adiciona(this.livro);
	    } else {
	    	livroDao.atualiza(this.livro);
	    }
	    this.livros = livroDao.listaTodos();
	    this.livro = new Livro();
	}
		
	public String formAutor() {
        return "autor?faces-redirect=true";
    }
	
}