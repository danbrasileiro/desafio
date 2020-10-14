package br.com.sefaz.desafio.view;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.facade.GeralFacade;
import br.com.sefaz.desafio.util.Util;

@ManagedBean(name = "loginView")
@ViewScoped
public class LoginView extends DesafioView {

	private static final long serialVersionUID = -8814091480975776461L;

	private GeralFacade geralFacade;
	private UsuarioDTO usuario;

	@PostConstruct
	public void init() {

		Map<String, String> lMapParametros = obterParametrosRequisicao();

		if (!Util.nullOrEmpty(lMapParametros) && "expirou".equals(lMapParametros.get("sessao"))) {

			setExibirMensagem(true);
			setMensagem(Util.obterMensagem("msg.sua.sessao.expirou.favor.logar.novamente.no.sistema"));
		}
	}

	public String logar() {

		try {

			setExibirMensagem(false);
			setMensagem(null);
			setUsuarioLogado(getGeralfacade().logarSistema(getUsuario()));
		}
		catch (DesafioRuntimeException e) {

			exibirMsgErro("msg.erro.no.login", e.getErroMessagem());

			return "login";
		}

		return "logar";
	}

	public String logout() {

		setUsuario(null);
		setUsuarioLogado(null);

		return "login";
    }

	public GeralFacade getGeralfacade() {

		if (Util.nullOrEmpty(geralFacade)) {

			geralFacade = new GeralFacade();
		}

		return geralFacade;
	}

	public UsuarioDTO getUsuario() {

		if (Util.nullOrEmpty(usuario)) {

			usuario = new UsuarioDTO();
		}

		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {

		this.usuario = usuario;
	}
}