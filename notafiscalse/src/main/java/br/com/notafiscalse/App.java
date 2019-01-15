package br.com.notafiscalse;

import br.com.notafiscalse.test.TestNotaFiscalSe;

/*
  Lib para envio,consulta e cancelamento(Lote) da Nota Fiscal Eletrônica de Serviços da cidade de Ribeirão Preto-SP

*/

public class App 
{
    
	
	public static void main( String[] args )
    {
        TestNotaFiscalSe  test = new TestNotaFiscalSe();
        
		/* teste classe Util
    	   test.testeUtil(); */
    	
    	/* Teste geração XML 
    	   test.testeGeraXML();
    	*/
    	
        /* teste envio de cancelamento */
    	test.testeCancelamento();
    	
    	
    	
    }
    
       
    
}
