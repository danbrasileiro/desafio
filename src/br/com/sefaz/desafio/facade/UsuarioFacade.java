package br.com.sefaz.desafio.facade;

import java.util.ArrayList;

import br.com.sefaz.desafio.bo.UsuarioBO;
import br.com.sefaz.desafio.bo.UsuarioTelefoneBO;
import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.dto.UsuarioTelefoneDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.util.Util;

public class UsuarioFacade {

	private static final UsuarioBO usuarioBO = new UsuarioBO();
	private static final UsuarioTelefoneBO usuarioTelefoneBO = new UsuarioTelefoneBO();

	public ArrayList<UsuarioDTO> obterListaUsuarios(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		return usuarioBO.obterListaUsuarios(pUsuario);
	}

	public UsuarioDTO obterUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		pUsuario = usuarioBO.obterUsuario(pUsuario);

		pUsuario.setListaUsuarioTelefone(usuarioTelefoneBO.obterListaTelefonesUsuario(new UsuarioTelefoneDTO(pUsuario)));

		return pUsuario;
	}

	public void inserirUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		usuarioBO.inserirUsuario(pUsuario);

		if (!Util.nullOrEmpty(pUsuario.getListaUsuarioTelefoneCadastrar())) {

			for (UsuarioTelefoneDTO lUsuarioTelefone : pUsuario.getListaUsuarioTelefoneCadastrar()) {

				lUsuarioTelefone.setUsuario(pUsuario);

				usuarioTelefoneBO.inserirTelefoneUsuario(lUsuarioTelefone);
			}
		}
	}

	public void alterarUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		if (!Util.nullOrEmpty(pUsuario.getListaUsuarioTelefoneExcluir())) {

			for (UsuarioTelefoneDTO lUsuarioTelefone : pUsuario.getListaUsuarioTelefoneExcluir()) {

				usuarioTelefoneBO.excluirTelefoneUsuario(lUsuarioTelefone, true);
			}
		}

		if (!Util.nullOrEmpty(pUsuario.getListaUsuarioTelefoneCadastrar())) {

			for (UsuarioTelefoneDTO lUsuarioTelefone : pUsuario.getListaUsuarioTelefoneCadastrar()) {

				lUsuarioTelefone.setUsuario(pUsuario);

				usuarioTelefoneBO.inserirTelefoneUsuario(lUsuarioTelefone);
			}
		}

		usuarioBO.alterarUsuario(pUsuario);
	}

	public void excluirUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		usuarioTelefoneBO.excluirTelefoneUsuario(new UsuarioTelefoneDTO(pUsuario), false);

		usuarioBO.excluirUsuario(pUsuario);
	}
}