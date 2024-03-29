package controle;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import anotacoes.CasoUso;
import interfaces.IControleNegocio;
import interfaces.IControleSessao;
import interfaces.IReferenciaAcaoNegocio;
import util.NegocioEntryPoint;
import util.Retorno;

public abstract class Controle implements IControleNegocio {
	
	public static final String ENTRYPOINT_ACAO_ANTES = "_CEAcaoAntes";
	public static final String ENTRYPOINT_ACAO_DEPOIS = "_CEAcaoDepois";

	public static final String ACAO_PADRAO = "index";
	protected HashMap<String, Object> variaveis;
	protected List<String> acoesValidas;
	protected String caminhoAplicacao;
	
	protected IControleSessao sessao;
	
	
	@Override
	public void setControleSessao(IControleSessao sessao) {
		this.sessao = sessao;
	}
	
	@Override
	public IControleSessao getControleSessao() {
		return this.sessao;
	}
	
	public Controle(){
		this.variaveis = new HashMap<String, Object>();
		this.initAcoesValidas();
		this.initCaminhoAplicacao();
	}
	private void initCaminhoAplicacao() {
		try {
			this.caminhoAplicacao = new java.io.File( "." ).getCanonicalPath();
		} catch (IOException e) {
			System.out.println("Erro ao obter Caminho Aplica��o");
			e.printStackTrace();
			this.caminhoAplicacao = "c:";
			
		}
	}
	
	@Override
	public boolean mostrarPaginaComum() {
		CasoUso uc = this.getClass().getAnnotation(CasoUso.class);
		return uc.paginaComum();
	}
	
	@Override
	public String getCasoDeUso() {
		CasoUso uc = this.getClass().getAnnotation(CasoUso.class);
		return uc.nomeCasoDeUso();
	}
	
	@Override
	public boolean precisaAutenticar() {
		CasoUso uc = this.getClass().getAnnotation(CasoUso.class);
		return uc.precisaAutenticar();
	}
	
	protected Retorno executarEntryPoint(String entrypointAcao,String acao) {
		List<NegocioEntryPoint> list = this.getEntryPointsUtils();
		Retorno retorno = new Retorno(true,null,acao);
		for(NegocioEntryPoint entryPoint : list) {
			if(entryPoint.getEntryPointName().equals(entrypointAcao)) {
				Retorno retornoEntryPoint = entryPoint.getMetodoEntryPoint().executar(retorno.getAcao());
				retorno.setAcao(retornoEntryPoint.getAcao());
				if(!retornoEntryPoint.isSucesso()) {
					retorno.setSucesso(false);
				}
				retorno.AddMensagens(retornoEntryPoint.getMensagens());
			}
		}
		return retorno;
		
	}
	
	@Override
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodosUtil() {
		HashMap<String, IReferenciaAcaoNegocio> acaoMetodos = new HashMap<>();
		HashMap<String, IReferenciaAcaoNegocio> acaoMetodosExtras = this.getAcaoMetodos();
		if(acaoMetodosExtras!=null) {
			acaoMetodos.putAll(acaoMetodosExtras);
		}
		acaoMetodos.put(ACAO_PADRAO, this::_acaoPadrao);
		return acaoMetodos;
	}
	public Retorno _acaoPadrao(String acao) {
		Retorno ret = null;
		if(!this.acaoExiste(acao)){			
			ret = new Retorno(false, "Nenhuma a��o implementada!",acao);
		}else{
			ret = new Retorno(true, null,acao);//A��o Existe!
		}
		return ret;
	}
	
	/**
	 * Deve retornar a lista de acoes suportadas pelo controlador associado ao metodo que dever� ser envocado
	 * utiliza referecia para metodos (http://tecnopode.blogspot.com.br/2015/09/referencias-para-metodos-e-seu-uso.html
	 * https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
	 * Exemplo:
	 * <pre>
	 *  HashMap&lt;String, IReferenciaAcaoNegocio&gt; acaoMetodos = new HashMap<>();
	 *  acaoMetodos.put("nomeAcao",this::acaoNomeAcao);//para a��es que tem processamento na camada de controle(negocio)
	 *  acaoMetodos.put("nomeAcao2",null);//acao que n�o tem processamento na camada de controle
	 *  
	 *  return acaoMetodos;
	 * </pre>
	 * O metodo de a��o deve ter a assinatura public Retorno acaoNomeAcao(String acao)
	 * @return
	 */
	public abstract HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodos() ;
	
	protected List<String> initAcoesValidas() {
		HashMap<String, IReferenciaAcaoNegocio> acaoMetodos = this.getAcaoMetodosUtil();
		
		this.acoesValidas = new ArrayList<>();
		
		Iterator<String> iteratorAcaoMetodos = acaoMetodos.keySet().iterator();
		while(iteratorAcaoMetodos.hasNext()) {
			String acaoNome = iteratorAcaoMetodos.next();
			this.acoesValidas.add(acaoNome);
		}
		return this.acoesValidas;
	}	
	@Override
	public boolean acaoExiste(String acao) {
		if(getListaDeAcoesValidas().contains(acao)) {
			return true;
		}
		return false;
	}

	
	@Override
	public void setVariaveis(HashMap<String, Object> variables) {
		this.variaveis.putAll(variables);
	}
	
	@Override
	public void limparVariaveis() {
		this.variaveis.clear();
	}

	@Override
	public HashMap<String, Object> getVariaveis() {
		return this.variaveis;
	}
	@Override
	public Retorno executar(String acao) {
		//Retorno ret = this._acaoPadrao(acao);
		//EntryPoint antes da a��o
		Retorno retEntryPointAntes = this.executarEntryPoint(ENTRYPOINT_ACAO_ANTES, acao);
		if(!retEntryPointAntes.isSucesso()) {
			return retEntryPointAntes;
		}
		
		//Executar a a��o
		Retorno retAcaoNegocio = this.processarAcaoDeNegocio(retEntryPointAntes.getAcao());
		retAcaoNegocio.AddMensagens(retEntryPointAntes.getMensagens());
		if(!retAcaoNegocio.isSucesso()) {
			return retAcaoNegocio;
		}
		
		//EntryPoint depois da a��o
		Retorno retEntryPointDepois = this.executarEntryPoint(ENTRYPOINT_ACAO_DEPOIS, retAcaoNegocio.getAcao());
		retEntryPointDepois.AddMensagens(retAcaoNegocio.getMensagens());
		return retEntryPointDepois;
	}
	
	public Retorno entryPointAcaoPadraoAntes(String acao) {
		return this._acaoPadrao(acao);		
	}
	
	protected Retorno processarAcaoDeNegocio(String acao) {
		HashMap<String, IReferenciaAcaoNegocio> acaoMetodos = this.getAcaoMetodosUtil();
		IReferenciaAcaoNegocio acaoMetodo = acaoMetodos.get(acao); 
		if (acaoMetodo == null) {
			acaoMetodo = acaoMetodos.get(ACAO_PADRAO);
		}
		Retorno ret = acaoMetodo.executar(acao);
		return ret;

	}
	@Override
	public List<String> getListaDeAcoesValidas() {
		return this.acoesValidas;
	}
	
	@Override
	public void configurarCaminhoAplicacao(String caminho) {
		this.caminhoAplicacao = caminho;		
	}
	@Override
	public String getCaminhoAplicacao() {
		return this.caminhoAplicacao;
	}
	/**Deve retornar a lista de NegocioEntryPoint que ser�o utilizados.
	 * A ordem de execu��o e a mesma ordem do retorno(FIFO)
	 * exemplo:
	 * <pre>
	 * List&lt;NegocioEntryPoint&gt; list = new ArrayList&lt;&gt;();
	 *	list.add(new NegocioEntryPoint(ENTRYPOINT_ALTERAR_ANTES, this::entryPointAcaoAlterarAntes));
	 *	list.add(new NegocioEntryPoint(ENTRYPOINT_ALTERAR_DEPOIS,this::entryPointAcaoAlterarDepois));
	 *	return list;</pre>
	 * @return
	 */
	public abstract List<NegocioEntryPoint> getEntryPoints();
	
	protected List<NegocioEntryPoint> getControleEntryPoints(){
		List<NegocioEntryPoint> list = new ArrayList<>();
		list.add(new NegocioEntryPoint(ENTRYPOINT_ACAO_ANTES, this::entryPointAcaoPadraoAntes));
		return list;
	}
	
	public List<NegocioEntryPoint> getEntryPointsUtils(){
		List<NegocioEntryPoint> list = new ArrayList<>();
		list.addAll(this.getControleEntryPoints());
		List<NegocioEntryPoint> listExtra = this.getEntryPoints();
		if(listExtra!=null) {
			list.addAll(listExtra);
		}
		return list;
	
	}
}
