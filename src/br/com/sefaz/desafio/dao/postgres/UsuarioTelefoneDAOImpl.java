package br.com.sefaz.desafio.dao.postgres;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import br.com.sefaz.desafio.dao.UsuarioTelefoneDAOIf;
import br.com.sefaz.desafio.dto.TipoTelefoneDTO;
import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.dto.UsuarioTelefoneDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.util.Util;

public class UsuarioTelefoneDAOImpl extends DesafioDAOImpl implements UsuarioTelefoneDAOIf {

	public ArrayList<UsuarioTelefoneDTO> obterListaTelefonesUsuario(UsuarioTelefoneDTO pUsuarioTelefone) throws DesafioRuntimeException {

		ArrayList<UsuarioTelefoneDTO> lListaTelefonesUsuario = null;

		StringBuffer lSql = new StringBuffer();

		lSql.append("	SELECT ");
		lSql.append("		ut.id, ut.ddd, ut.numero, ut.tipo_telefone, ut.email ");
		lSql.append("	FROM ");
		lSql.append("		usuario_telefone ut ");
		lSql.append("			INNER JOIN usuario u on ut.email = u.email ");
		lSql.append("	WHERE ");
		lSql.append("		1 = 1 ");

		if (!Util.nullOrEmpty(pUsuarioTelefone)) {

			if (!Util.nullOrEmpty(pUsuarioTelefone.getUsuario())) {

				if (!Util.nullOrEmpty(pUsuarioTelefone.getUsuario().getEmail())) {

					lSql.append("	and ut.email = ? ");
				}
			}
		}

		int lIndice = 0;

		try {

			prepararSQL(getConnection().prepareStatement(lSql.toString()));

			if (!Util.nullOrEmpty(pUsuarioTelefone)) {

				if (!Util.nullOrEmpty(pUsuarioTelefone.getUsuario())) {

					if (!Util.nullOrEmpty(pUsuarioTelefone.getUsuario().getEmail())) {

						setarParametroSQL(++lIndice, pUsuarioTelefone.getUsuario().getEmail());
					}
				}
			}

			consultar();
			
			if (existeRegistroDaConsulta()) {

				lListaTelefonesUsuario = new ArrayList<UsuarioTelefoneDTO>();

				lListaTelefonesUsuario.add(
					new UsuarioTelefoneDTO(
						(Long) obterRegistroConsulta("id", Types.LONGNVARCHAR),
						(Integer) obterRegistroConsulta("ddd", Types.INTEGER),
						(String) obterRegistroConsulta("numero", Types.VARCHAR),
						TipoTelefoneDTO.obterPorChave((String) obterRegistroConsulta("tipo_telefone", Types.VARCHAR)),
						new UsuarioDTO((String) obterRegistroConsulta("email", Types.VARCHAR))
					)
				);

				while (existeRegistroDaConsulta()) {

					lListaTelefonesUsuario.add(
						new UsuarioTelefoneDTO(
							(Long) obterRegistroConsulta("id", Types.LONGNVARCHAR),
							(Integer) obterRegistroConsulta("ddd", Types.INTEGER),
							(String) obterRegistroConsulta("numero", Types.VARCHAR),
							TipoTelefoneDTO.obterPorChave((String) obterRegistroConsulta("tipo_telefone", Types.VARCHAR)),
							new UsuarioDTO((String) obterRegistroConsulta("email", Types.VARCHAR))
						)
					);
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

		return lListaTelefonesUsuario;
	}

	public UsuarioTelefoneDTO inserirTelefoneUsuario(UsuarioTelefoneDTO pUsuarioTelefone) throws DesafioRuntimeException {

		StringBuffer lSql = new StringBuffer();

		lSql.append("	INSERT INTO ");
		lSql.append("		usuario_telefone(id, ddd, numero, tipo_telefone, email) ");
		lSql.append("	VALUES ");
		lSql.append("		(?,?,?,?,?) ");

		int lIndice = 0;

		try {

			pUsuarioTelefone.setId(obterValorSequence("usuario_telefone_seq"));

			prepararSQL(getConnection().prepareStatement(lSql.toString()));

			setarParametroSQL(++lIndice, pUsuarioTelefone.getId());
			setarParametroSQL(++lIndice, pUsuarioTelefone.getDdd());
			setarParametroSQL(++lIndice, pUsuarioTelefone.getNumero());
			setarParametroSQL(++lIndice, pUsuarioTelefone.getTipoTelefone().getCodigo());
			setarParametroSQL(++lIndice, pUsuarioTelefone.getUsuario().getEmail());

			cadastrar();

			fecharConexaoBancoTry();
		}
		catch (SQLException e) {

			tratarExcecaoBanco(e);
		}
		finally {

			fecharConexaoBancoFinally();
		}

		return pUsuarioTelefone;
	}

	public int excluirTelefoneUsuario(UsuarioTelefoneDTO pUsuarioTelefone) throws DesafioRuntimeException {

		StringBuffer lSql = new StringBuffer();

		lSql.append("	DELETE FROM ");
		lSql.append("		usuario_telefone ");
		lSql.append("	WHERE ");
		lSql.append("		1 = 1 ");

		if (!Util.nullOrEmpty(pUsuarioTelefone)) {

			if (!Util.nullOrEmpty(pUsuarioTelefone.getId())) {

				lSql.append("		and id = ? ");
			}

			if (!Util.nullOrEmpty(pUsuarioTelefone.getUsuario())) {

				if (!Util.nullOrEmpty(pUsuarioTelefone.getUsuario().getEmail())) {

					lSql.append("	and email = ? ");
				}
			}
		}

		int lIndice = 0;

		try {

			prepararSQL(getConnection().prepareStatement(lSql.toString()));

			if (!Util.nullOrEmpty(pUsuarioTelefone)) {

				if (!Util.nullOrEmpty(pUsuarioTelefone.getId())) {

					setarParametroSQL(++lIndice, pUsuarioTelefone.getId());
				}

				if (!Util.nullOrEmpty(pUsuarioTelefone.getUsuario())) {

					if (!Util.nullOrEmpty(pUsuarioTelefone.getUsuario().getEmail())) {

						setarParametroSQL(++lIndice, pUsuarioTelefone.getUsuario().getEmail());
					}
				}
			}

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