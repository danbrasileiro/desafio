package br.com.sefaz.desafio.view;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.util.Util;

public class DesafioView implements Serializable {

	private static final long serialVersionUID = 194658482807503648L;

	private static final String USUARIO_LOGADO = "usuario";
	private boolean exibirMensagem;
	private String mensagem;
	private String acao;

	public void exibirMsgSucesso(String pChave) {

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Util.obterMensagem(pChave), ""));
	}

	public void exibirMsgErro(String pChave) {

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Util.obterMensagem(pChave), ""));
	}
	public void exibirMsgErro(String pChave, String pDetalhes) {

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Util.obterMensagem(pChave), pDetalhes));
	}

	public String novo() {

		return "novo";
	}
	public String alterar() {

		return "alterar";
	}
	public String cancelar() {

		return "cancelar";
	}

	public boolean getModoAlteracao() {

		if (!Util.nullOrEmpty(getAcao()) && "alterar".equals(getAcao().toLowerCase())) {

			return true;
		}

		return false;
	}

	public HttpSession getSession(boolean pValor) {

		if (!Util.nullOrEmpty(getRequest())) {

			return (HttpSession) getRequest().getSession(pValor);
		}

		return null;
	}

    protected HttpServletRequest getRequest() {

    		if (!Util.nullOrEmpty(FacesContext.getCurrentInstance()) && 
				!Util.nullOrEmpty(FacesContext.getCurrentInstance().getExternalContext())) {

			return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		}

		return null;
    }

	public Map<String, String> obterParametrosRequisicao() {

		if (!Util.nullOrEmpty(FacesContext.getCurrentInstance())
				&& !Util.nullOrEmpty(FacesContext.getCurrentInstance().getExternalContext())
				&& !Util.nullOrEmpty(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap())) {

			return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		}

		return null;
	}
	public String obterParametroRequisicao(String pNomeParametro) {

		Map<String, String> lMapParametros = obterParametrosRequisicao();

		return !Util.nullOrEmpty(lMapParametros) ? lMapParametros.get(pNomeParametro) : null;
	}
	
	public UsuarioDTO obterUsuarioLogado(HttpServletRequest request) {

		if (!Util.nullOrEmpty(request.getSession())) {

			return (UsuarioDTO) request.getSession().getAttribute(USUARIO_LOGADO);
		}

		return null;
    }
	public void setUsuarioLogado(UsuarioDTO pUsuario) {

		if (!Util.nullOrEmpty(getRequest())) {

			getSession(false).setAttribute(USUARIO_LOGADO, pUsuario);
		}
	}

	public boolean isExibirMensagem() {
		return exibirMensagem;
	}
	public void setExibirMensagem(boolean exibirMensagem) {
		this.exibirMensagem = exibirMensagem;
	}

	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
}