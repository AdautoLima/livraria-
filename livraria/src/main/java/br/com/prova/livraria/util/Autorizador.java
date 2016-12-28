package br.com.prova.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.prova.livraria.modelo.Usuario;

public class Autorizador implements PhaseListener {
	
	private static final long serialVersionUID = 1L;

    public void afterPhase(PhaseEvent evento) {
    	
    	FacesContext context = evento.getFacesContext();
    	String nomePagina = context.getViewRoot().getViewId();
    	
    	System.out.println(nomePagina);
    	
    	if("/login.xhtml".equals(nomePagina)) {
    		return;
    	}
    	
    	Object usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
    	
    	if(usuarioLogado != null){
    		return;
    	}
    	
    	NavigationHandler handler = context.getApplication().getNavigationHandler();
    	handler.handleNavigation(context, null, "/login?faces-redirect=true");
    	
    	context.renderResponse();
    }

    public void beforePhase(PhaseEvent event) {
        System.out.println("FASE: " + event.getPhaseId());
    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}