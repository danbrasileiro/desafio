package br.com.sefaz.desafio.bo;

import br.com.sefaz.desafio.dao.GeralDAOIf;
import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.exception.DesafioAplicacaoException;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.util.DAOFactory;
import br.com.sefaz.desafio.util.Util;

public class GeralBO {

	private static final GeralDAOIf geralDAO = DAOFactory.getDAOGeral();

	public UsuarioDTO logarSistema(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		pUsuario = geralDAO.logarSistema(pUsuario);

		if (Util.nullOrEmpty(pUsuario)) {

			throw new DesafioAplicacaoException(Util.obterMensagem("msg.email.ou.senha.nao.conferem"));
		}

		return pUsuario;
	}
}