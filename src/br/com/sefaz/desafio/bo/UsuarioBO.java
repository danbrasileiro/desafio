package br.com.sefaz.desafio.bo;

import java.util.ArrayList;

import br.com.sefaz.desafio.dao.UsuarioDAOIf;
import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.exception.DesafioAplicacaoException;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.util.DAOFactory;
import br.com.sefaz.desafio.util.Util;

public class UsuarioBO {

	private static final UsuarioDAOIf usuarioDAO = DAOFactory.getDAOUsuario();

	public ArrayList<UsuarioDTO> obterListaUsuarios(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		return usuarioDAO.obterListaUsuarios(pUsuario);
	}

	public UsuarioDTO obterUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		pUsuario = usuarioDAO.obterUsuario(pUsuario);

		if (Util.nullOrEmpty(pUsuario)) {

			throw new DesafioAplicacaoException("msg.nao.foi.possivel.obter.registro.selecionado");
		}

		return pUsuario;
	}

	public void inserirUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		usuarioDAO.inserirUsuario(pUsuario);
	}

	public void alterarUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		if (usuarioDAO.alterarUsuario(pUsuario) < 1) {

			throw new DesafioAplicacaoException(Util.obterMensagem("msg.nao.foi.possivel.alterar.registro.selecionado"));
		}
	}

	public void excluirUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		if (usuarioDAO.excluirUsuario(pUsuario) < 1) {

			throw new DesafioAplicacaoException(Util.obterMensagem("msg.nao.foi.possivel.excluir.registro.selecionado"));
		}
	}
}