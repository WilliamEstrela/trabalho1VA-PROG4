package interfaces;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import biz.source_code.miniTemplator.MiniTemplator;
import util.Retorno;

public interface IVisaoControle {
	/**
	 * @return string contendo o nome do caso de uso(usar padrao de nome de v�riavel)
	 */
	public String getCasoDeUso();
	
	/**
	 * metodo utilizado para executar a a��o de vis�o,
	 * @param request  
	 * @param controle Controlador do caso de uso
	 * @param acao acao executado pelo controlador antes da parte de visao
	 * @param controleRetornoAcao retorno da execu��o da a��o no controlador
	 * @return
	 */

	public MiniTemplator executarAcao(HttpServletRequest request,IControleNegocio controle, String acao, Retorno controleRetornoAcao);
	
	public HashMap<String, IReferenciaVisaoAcao> getVisaoAcaoMetodosUtil();
	
	
}
