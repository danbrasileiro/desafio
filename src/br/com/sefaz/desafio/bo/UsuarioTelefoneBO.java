package br.com.sefaz.desafio.bo;

import java.util.ArrayList;

import br.com.sefaz.desafio.dao.UsuarioTelefoneDAOIf;
import br.com.sefaz.desafio.dto.UsuarioTelefoneDTO;
import br.com.sefaz.desafio.exception.DesafioAplicacaoException;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.util.DAOFactory;
import br.com.sefaz.desafio.util.Util;

public class UsuarioTelefoneBO {

	private static final UsuarioTelefoneDAOIf usuarioTelefoneDAO = DAOFactory.getDAOUsuarioTelefone();

	public ArrayList<UsuarioTelefoneDTO> obterListaTelefonesUsuario(UsuarioTelefoneDTO pUsuarioTelefone) throws DesafioRuntimeException {

		return usuarioTelefoneDAO.obterListaTelefonesUsuario(pUsuarioTelefone);
	}

	public UsuarioTelefoneDTO inserirTelefoneUsuario(UsuarioTelefoneDTO pUsuarioTelefone) throws DesafioRuntimeException {

		return usuarioTelefoneDAO.inserirTelefoneUsuario(pUsuarioTelefone);
	}

	public void excluirTelefoneUsuario(UsuarioTelefoneDTO pUsuarioTelefone, boolean pExibirMsgRegistroNaoExcluido) throws DesafioRuntimeException {

		if (usuarioTelefoneDAO.excluirTelefoneUsuario(pUsuarioTelefone) < 1) {

			if (pExibirMsgRegistroNaoExcluido) {

				throw new DesafioAplicacaoException(Util.obterMensagem("msg.nao.foi.possivel.excluir.registro.selecionado"));
			}
		}
	}
}