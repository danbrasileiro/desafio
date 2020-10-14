package br.com.sefaz.desafio.util;

import br.com.sefaz.desafio.dao.postgres.GeralDAOImpl;
import br.com.sefaz.desafio.dao.postgres.UsuarioDAOImpl;
import br.com.sefaz.desafio.dao.postgres.UsuarioTelefoneDAOImpl;

public class DAOFactory {

	public static GeralDAOImpl getDAOGeral() {

		return new GeralDAOImpl();
	}
	public static UsuarioDAOImpl getDAOUsuario() {

		return new UsuarioDAOImpl();
	}
	public static UsuarioTelefoneDAOImpl getDAOUsuarioTelefone() {

		return new UsuarioTelefoneDAOImpl();
	}
}