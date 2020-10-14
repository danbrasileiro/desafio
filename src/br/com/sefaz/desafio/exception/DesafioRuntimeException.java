package br.com.sefaz.desafio.exception;

public class DesafioRuntimeException extends Exception {

	private static final long serialVersionUID = 1L;

	public DesafioRuntimeException(String pErroMessagem) {
        super(pErroMessagem);
    }

    public DesafioRuntimeException(String pErroMessagem, Throwable pCause) {
        super(pErroMessagem, pCause);
    }

    public String getErroMessagem() {
        return getMessage();
    }
}