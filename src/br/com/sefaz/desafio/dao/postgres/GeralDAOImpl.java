package br.com.sefaz.desafio.dao.postgres;

import java.sql.SQLException;
import java.sql.Types;

import br.com.sefaz.desafio.dao.GeralDAOIf;
import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.util.Util;

public class GeralDAOImpl extends DesafioDAOImpl implements GeralDAOIf {

	public UsuarioDTO logarSistema(UsuarioDTO pUsuario) throws DesafioRuntimeException {

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

			if (!Util.nullOrEmpty(pUsuario.getSenha())) {

				lSql.append("		and senha = ? ");
			}
		}

		int lIndice = 0;

		try {

			prepararSQL(getConnection().prepareStatement(lSql.toString()));

			if (!Util.nullOrEmpty(pUsuario)) {

				if (!Util.nullOrEmpty(pUsuario.getEmail())) {

					setarParametroSQL(++lIndice, pUsuario.getEmail()); 
				}

				if (!Util.nullOrEmpty(pUsuario.getSenha())) {

					setarParametroSQL(++lIndice, pUsuario.getSenha());
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
}