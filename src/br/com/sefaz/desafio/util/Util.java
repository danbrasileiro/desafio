package br.com.sefaz.desafio.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Util {

    public static final String STRING_VAZIA = "";

    public static String obterMensagem(String pItemArquivoPropriedades) {

		return ResourceBundle.getBundle("resources.messages", Locale.getDefault()).getString(pItemArquivoPropriedades);
}
    public static String obterMensagem(String pArquivoPropriedades, String pItemArquivoPropriedades) {

    		return ResourceBundle.getBundle(pArquivoPropriedades, Locale.getDefault()).getString(pItemArquivoPropriedades);
    }
	public static String obterMensagem(String pArquivoPropriedades, Locale pLocal, String pItemArquivoPropriedades) {

		return ResourceBundle.getBundle(pArquivoPropriedades, pLocal).getString(pItemArquivoPropriedades);
	}

    public static boolean nullOrEmpty(Object objeto) {

        if (objeto == null) {

            return true;
        }

        if (objeto instanceof String) {

            if (STRING_VAZIA.equals(((String) objeto).trim())) {

                return true;
            }
        }

        return false;
    }
}