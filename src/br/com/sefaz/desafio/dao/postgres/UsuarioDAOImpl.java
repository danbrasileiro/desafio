package br.com.sefaz.desafio.dao.postgres;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import br.com.sefaz.desafio.dao.UsuarioDAOIf;
import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.util.Util;

public class UsuarioDAOImpl extends DesafioDAOImpl implements UsuarioDAOIf {

	public ArrayList<UsuarioDTO> obterListaUsuarios(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		ArrayList<UsuarioDTO> lListaUsuarios = null;

		StringBuffer lSql = new StringBuffer();

		lSql.append("	SELECT ");
		lSql.append("		nome, email ");
		lSql.append("	FROM ");
		lSql.append("		usuario ");
		lSql.append("	WHERE ");
		lSql.append("		1 = 1 ");
		lSql.append("	ORDER BY ");
		lSql.append("		nome ");

		try {

			prepararSQL(getConnection().prepareStatement(lSql.toString()));

			consultar();
			
			if (existeRegistroDaConsulta()) {

				lListaUsuarios = new ArrayList<UsuarioDTO>();

				lListaUsuarios.add(new UsuarioDTO((String) obterRegistroConsulta("nome", Types.VARCHAR), (String) obterRegistroConsulta("email", Types.VARCHAR)));

				while (existeRegistroDaConsulta()) {

					lListaUsuarios.add(new UsuarioDTO((String) obterRegistroConsulta("nome", Types.VARCHAR), (String) obterRegistroConsulta("email", Types.VARCHAR)));
				}
			}

			fecharConexaoBancoTry();
		}
		catch (SQLException e) {

			tratarExcecaoBanco(e);
		}
		finally {

			fecharConexaoBancoFinally();
		}

		return lListaUsuarios;
	}

	public UsuarioDTO obterUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		StringBuffer lSql = new StringBuffer();

		lSql.append("	SELECT ");
		lSql.append("		nome ");
		lSql.append("	FROM ");
		lSql.append("		usuario ");
		lSql.append("	WHERE ");
		lSql.append("		1 = 1 ");

		if (!Util.nullOrEmpty(pUsuario)) {

			if (!Util.nullOrEmpty(pUsuario.getEmail())) {

				lSql.append("		and lower(email) = lower(?) ");
			}
		}

		int lIndice = 0;

		try {

			prepararSQL(getConnection().prepareStatement(lSql.toString()));

			if (!Util.nullOrEmpty(pUsuario)) {

				if (!Util.nullOrEmpty(pUsuario.getEmail())) {

					setarParametroSQL(++lIndice, pUsuario.getEmail()); 
				}
			}

			consultar();

			if (existeRegistroDaConsulta()) {

				pUsuario.setNome((String) obterRegistroConsulta("nome", Types.VARCHAR));
			}
			else {

				pUsuario = null;
			}

			fecharConexaoBancoTry();
		}
		catch (SQLException e) {

			tratarExcecaoBanco(e);
		}
		finally {

			fecharConexaoBancoFinally();
		}

		return pUsuario;
	}

	public void inserirUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		StringBuffer lSql = new StringBuffer();

		lSql.append("	INSERT INTO ");
		lSql.append("		usuario(nome, email, senha) ");
		lSql.append("	VALUES ");
		lSql.append("		(?,?,?) ");

		int lIndice = 0;

		try {

			prepararSQL(getConnection().prepareStatement(lSql.toString()));

			setarParametroSQL(++lIndice, pUsuario.getNome());
			setarParametroSQL(++lIndice, pUsuario.getEmail());
			setarParametroSQL(++lIndice, pUsuario.getSenha());

			cadastrar();

			fecharConexaoBancoTry();
		}
		catch (SQLException e) {

			tratarExcecaoBanco(e);
		}
		finally {

			fecharConexaoBancoFinally();
		}
	}

	public int alterarUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		StringBuffer lSql = new StringBuffer();

		lSql.append("	UPDATE ");
		lSql.append("		usuario ");
		lSql.append("	SET ");
		lSql.append("		nome = ? ");

		if (!Util.nullOrEmpty(pUsuario.getSenha())) {

			lSql.append("	, senha = ? ");
		}

		lSql.append("	WHERE ");
		lSql.append("		email = ? ");

		int lIndice = 0;

		try {

			prepararSQL(getConnection().prepareStatement(lSql.toString()));

			setarParametroSQL(++lIndice, pUsuario.getNome());

			if (!Util.nullOrEmpty(pUsuario.getSenha())) {

				setarParametroSQL(++lIndice, pUsuario.getSenha());
			}

			setarParametroSQL(++lIndice, pUsuario.getEmail());

			lIndice = atualizar();

			fecharConexaoBancoTry();
		}
		catch (SQLException e) {

			tratarExcecaoBanco(e);
		}
		finally {

			fecharConexaoBancoFinally();
		}

		return lIndice;
	}

	public int excluirUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		StringBuffer lSql = new StringBuffer();

		lSql.append("	DELETE FROM ");
		lSql.append("		usuario ");
		lSql.append("	WHERE ");
		lSql.append("		email = ? ");

		int lIndice = 0;

		try {

			prepararSQL(getConnection().prepareStatement(lSql.toString()));

			setarParametroSQL(++lIndice, pUsuario.getEmail());

			lIndice = atualizar();

			fecharConexaoBancoTry();
		}
		catch (SQLException e) {

			tratarExcecaoBanco(e);
		}
		finally {

			fecharConexaoBancoFinally();
		}

		return lIndice;
	}
}