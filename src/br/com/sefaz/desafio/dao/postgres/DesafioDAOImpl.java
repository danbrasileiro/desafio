package br.com.sefaz.desafio.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.util.Util;

public class DesafioDAOImpl {

	private Connection conexao;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;

	public Connection getConnection() throws DesafioRuntimeException {

        try {

        		if (getConexao() == null) {

            		String lUrl = Util.obterMensagem("banco.url");
	        		String lUsuario= Util.obterMensagem("banco.usuario");
	        		String lSenha = Util.obterMensagem("banco.senha");

	        		Class.forName("org.postgresql.Driver");

	        		setConexao(DriverManager.getConnection(lUrl,lUsuario,lSenha));
        		}
        }
        catch (SQLException e) {

        		System.out.println(e.getMessage());
			throw new DesafioRuntimeException(Util.obterMensagem("msg.banco.de.dados.fora.do.ar"), e.getCause());
		}
        catch (ClassNotFoundException e) {

			throw new DesafioRuntimeException(e.getMessage(), e.getCause());
		}

        return getConexao();                
	}
	public void setConnection(Connection pConexao) {

		setConexao(pConexao);
	}

	public Long obterValorSequence(String pNomeSequence) throws DesafioRuntimeException {

		Long lValorSequence = null;

		try {

			setPreparedStatement(getConnection().prepareStatement("select nextval('" + pNomeSequence + "')"));

			setResultSet(getPreparedStatement().executeQuery());

			if (getResultSet().next()) {

				lValorSequence = getResultSet().getLong(1);
			}
			
			fecharConexaoBancoTry();
		}
		catch (SQLException e) {

			tratarExcecaoBanco(e);
		}
		finally {

			fecharConexaoBancoFinally();
		}

		return lValorSequence;
	}

	public void tratarExcecaoBanco(SQLException pExcecao) throws DesafioRuntimeException {

		try {

			if (!Util.nullOrEmpty(getConexao()) && !getConexao().getAutoCommit()) {

				getConexao().rollback();

				getConexao().close();
				setConexao(null);
			}

			switch (pExcecao.getSQLState()) {

				case "23503":

					throw new DesafioRuntimeException(Util.obterMensagem("msg.o.registro.esta.sendo.utilizado.por.outra.tabela.do.banco.de.dados") + obterDetalhesMensagemErro(pExcecao.getMessage()), pExcecao.getCause());

				case "23505":

					throw new DesafioRuntimeException(Util.obterMensagem("msg.o.registro.informado.ja.existe.no.banco.de.dados") + obterDetalhesMensagemErro(pExcecao.getMessage()), pExcecao.getCause());

				default:

					throw new DesafioRuntimeException(pExcecao.getMessage(), pExcecao.getCause());
			}
		}
		catch (SQLException e) {

			throw new DesafioRuntimeException(e.getMessage(), e.getCause()); 
		}
	}

	public String obterDetalhesMensagemErro(String pMensagem) {

		if (!Util.nullOrEmpty(pMensagem) && pMensagem.indexOf("(") != -1 && pMensagem.lastIndexOf(")") != -1) {

			return " (" + pMensagem.substring(pMensagem.indexOf("("), pMensagem.lastIndexOf(")") + 1).replaceAll("\\(", "").replaceAll("\\)", "") + ") ";
		}

		return "";
	}

	public void fecharConexaoBancoTry() throws SQLException {

		if (!Util.nullOrEmpty(getResultSet())) {

			getResultSet().close();

			setResultSet(null);
		}

		if (!Util.nullOrEmpty(getPreparedStatement())) {

			getPreparedStatement().close();

			setPreparedStatement(null);
		}

		if (!Util.nullOrEmpty(getConexao())) {

			if (!getConexao().getAutoCommit()) {

				getConexao().commit();
			}

			getConexao().close();

			setConexao(null);
		}
	}

	public void fecharConexaoBancoFinally() {

		if (!Util.nullOrEmpty(getResultSet())) {

			try {

				getResultSet().close();
			}
			catch (SQLException e) {

				e.printStackTrace();
			}

			setResultSet(null);
		}

		if (!Util.nullOrEmpty(getPreparedStatement())) {

			try {

				getPreparedStatement().close();
			}
			catch (SQLException e) {

				e.printStackTrace();
			}

			setPreparedStatement(null);
		}

		if (!Util.nullOrEmpty(getConexao())) {

			try {

				getConexao().close();
			}
			catch (SQLException e) {

				e.printStackTrace();
			}

			setConexao(null);
		}
	}

	public void prepararSQL(PreparedStatement pPreparedStatement) {

		setPreparedStatement(pPreparedStatement);
	}
	public void consultar() throws SQLException {

		setResultSet(getPreparedStatement().executeQuery());
	}
	public void cadastrar() throws SQLException {

		getPreparedStatement().execute();
	}
	public int atualizar() throws SQLException {

		return getPreparedStatement().executeUpdate();
	}
	public boolean existeRegistroDaConsulta() throws SQLException {

		return getResultSet().next();
	}
	public Object obterRegistroConsulta(String pNomeColuna, int pTipoColuna) throws SQLException {

		switch (pTipoColuna) {

			case Types.VARCHAR:

				return getResultSet().getString(pNomeColuna);

			case Types.LONGNVARCHAR:

				return getResultSet().getLong(pNomeColuna);

			case Types.INTEGER:

				return getResultSet().getInt(pNomeColuna);
		}

		return null;
	}
	public void setarParametroSQL(int pIndiceParametro, Object pObjeto) throws SQLException {

		switch (pObjeto.getClass().getSimpleName()) {

			case "String":

				getPreparedStatement().setString(pIndiceParametro, (String) pObjeto);

			break;

			case "Long":

				getPreparedStatement().setLong(pIndiceParametro, (Long) pObjeto);

			break;

			case "Integer":

				getPreparedStatement().setInt(pIndiceParametro, (Integer) pObjeto);

			break;
		}
	}

	public Connection getConexao() {
		return conexao;
	}
	private void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

	private ResultSet getResultSet() {
		return resultSet;
	}
	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	private PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}
	private void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}
}