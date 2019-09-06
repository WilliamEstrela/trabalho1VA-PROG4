package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import biz.source_code.miniTemplator.MiniTemplator;
import biz.source_code.miniTemplator.MiniTemplator.TemplateSpecification;
import biz.source_code.miniTemplator.MiniTemplator.TemplateSyntaxException;

//classe criada para evitar acomplamento entre ServletControle e PaginaControle
public class PaginaUtilitario {
	static String SEPARADOR_DIRETORIO = System.getProperty("file.separator");
	public static  String getCaminhoPaginaCasoDeUso(String caminhoAplicao,String casoDeUso, String acao) {

		return  PaginaUtilitario.getCaminhoDiretorioCasoDeUso(caminhoAplicao, casoDeUso) + SEPARADOR_DIRETORIO + acao.toLowerCase() + ".html";
	}
	public static String getCaminhoDiretorioCasoDeUso(String caminhoAplicao,String casoDeUso) {
		return  caminhoAplicao + SEPARADOR_DIRETORIO+ casoDeUso.toLowerCase();
	}

	/** inicializa o arquivo de erro, caso tenha problema tenta inicializar 
	 * @param file
	 * @return
	 */
	public static MiniTemplator initTemplator(String file){
	
		MiniTemplator t = null;
		TemplateSpecification ts=null;
		try {			
			ts = new TemplateSpecification();
			ts.templateText= "<h1> Erro no template:</h1><br>"
					+ " ${message} <br>"
					+ "Stack trake:<br><pre>${stacktrace}</pre>";
			
			t = new MiniTemplator(file);
		} catch (Exception e) {
			try {
				t = new MiniTemplator(ts);
				t.setVariable("message", e.getMessage());
				
				String sStackTrace = getStackTraceAsString(e);
				
				t.setVariable("stacktrace", sStackTrace);
			} catch (TemplateSyntaxException | IOException e1) {
				e1.printStackTrace();
			}

		} /*catch (IOException e) {
			e.printStackTrace();
		}*/
		return t;
	}

	private static String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString(); // stack trace as a string
		return sStackTrace;
	}
}
