package br.com.sefaz.desafio.exception;

public class DesafioAplicacaoException extends DesafioRuntimeException {

	private static final long serialVersionUID = 1L;

	public DesafioAplicacaoException() {
        super("");
    }
	public DesafioAplicacaoException(String pErroMessagem) {
        super(pErroMessagem);
    }
    public DesafioAplicacaoException(String pErroMessagem, Throwable pCause) {
        super(pErroMessagem, pCause);
    }
}