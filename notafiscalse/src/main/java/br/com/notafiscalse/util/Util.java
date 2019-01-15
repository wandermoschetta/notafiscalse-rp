package br.com.notafiscalse.util;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public String formataData(Date data) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String dataFormatada = null;
		try {
			dataFormatada = format.format(data);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return dataFormatada;
	}
	
	public String formataValor(Double valor) {
        DecimalFormat novoFormato = new DecimalFormat("0.00");   
		String valorFormatado = novoFormato.format(valor);
		
		String[] part = valorFormatado.split("[,]");
		String novoValor = part[0]+"."+part[1];
		
		return novoValor;
		
    }
	
	
}
