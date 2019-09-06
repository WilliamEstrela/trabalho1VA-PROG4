package interfaces;

import java.util.HashMap;
import java.util.List;

import util.Retorno;


public interface IControleNegocio {
	/**
	 * @return string contendo o nome do caso de uso(usar padrao de nome de v�riavel)
	 */
	public String getCasoDeUso();
	
	/** Metodo utilizado para pegar os parametros que vem da vis�o e passar para o controlador
	 * @param variables uma lista de HashMap<String, Object> onde 
	 * string  � o nome da variavel e object o seu valor 
	 */
	public void setVariaveis(HashMap<String, Object> variables);
	
	/**
	 * limpar todas as vari�veis internas no controlador
	 */
	public void limparVariaveis();
	
	/**
	 * @return retorna o hashmap das vari�veis conhecidas pelo controllador.
	 */
	public HashMap<String, Object> getVariaveis();
	
	
	
	/** Esse metodo deve ser chamado apos o comando setVariables, para executar uma a��o. 
	 * @param action string que indica uma a��o conhecida pelo controlador.
	 * @return Objeto que indica o status da execu��o. contem uma mensagem
	 */
	public Retorno executar(String action);
	
	/** retorna a lista de a��es reconhecidas pelo controlador
	 * @return
	 */
	public List<String> getListaDeAcoesValidas();
	
	/** Valida se a��o informada existe no controlador.
	 * @param acao nome da a��o
	 * @return deve retornar true caso a a��o exista ou false
	 */
	public boolean acaoExiste(String acao);
	
	/** utilizado para informar o controlador o diret�rio onde a aplica��o est� trabalhando/ executando
	 * @param caminho
	 */
	public void configurarCaminhoAplicacao(String caminho);
	
	/** Retorno o caminho onde a aplica��o est� trabalhando/ executando
	 * @return
	 */
	public String getCaminhoAplicacao();
	
	/** metodo utilizado para retornar um hashmat de Acoes de Negocio do controlador.
	 * @return
	 */
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodosUtil();
	
	public void setControleSessao(IControleSessao sessao);
	
	public IControleSessao getControleSessao();
	
	public boolean precisaAutenticar();
	
	public boolean mostrarPaginaComum();
}
