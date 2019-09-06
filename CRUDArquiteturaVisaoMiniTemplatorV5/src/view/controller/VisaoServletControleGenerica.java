package view.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import biz.source_code.miniTemplator.MiniTemplator;
import interfaces.IControleNegocio;
import interfaces.IReferenciaVisaoAcao;
import interfaces.IVisaoControle;
import util.PaginaUtilitario;
import util.Retorno;
import util.VisaoEntryPoint;

public abstract class VisaoServletControleGenerica implements IVisaoControle {
	public static final String ACAO_PADRAO = "__acaoPadrao__";
	public static final String ENTRYPOINT_ACAO_ANTES  = "_VSCGenerica_ACAO_ANTES";
	public static final String ENTRYPOINT_ACAO_DEPOIS = "_VSCGenerica_ACAO_DEPOIS";

	@Override
	public MiniTemplator executarAcao(HttpServletRequest request, IControleNegocio controleNegocio, String acao,
			Retorno controleRetornoAcao) {
		
		String file = PaginaUtilitario.getCaminhoPaginaCasoDeUso(controleNegocio.getCaminhoAplicacao(),
				controleNegocio.getCasoDeUso(), controleRetornoAcao.getAcao());

		MiniTemplator t = PaginaUtilitario.initTemplator(file);
		this.executarEntryPoint(ENTRYPOINT_ACAO_ANTES, t, controleNegocio);
		// invocar metodos de acao de visao
		processarAcaoDeVisao(t, controleNegocio, acao);
		this.executarEntryPoint(ENTRYPOINT_ACAO_DEPOIS, t, controleNegocio);


		for(String mensagem:controleRetornoAcao.getMensagens()) {
			t.setVariable("message", mensagem, true);
			t.addBlock("bloco_mensagem",true);
		}
		// TODO clocar o nome useCase em uma constante
		t.setVariable("casoDeUso", controleNegocio.getCasoDeUso(), true);

		return t;
	}

	protected void executarEntryPoint(String entrypointAcao, MiniTemplator t,IControleNegocio controleNegocio) {
		List<VisaoEntryPoint> list = this.getEntryPointsUtils();
		for(VisaoEntryPoint entryPoint : list) {
			if(entryPoint.getEntryPointName().equals(entrypointAcao)) {
				entryPoint.getMetodoEntryPoint().processar(t, controleNegocio); 
			}
		}
		
	}

	private void processAcaoPadrao(MiniTemplator t, IControleNegocio controleNegocio) {
		this.mesclarVariaveis(t, controleNegocio, null);
	}

	protected void processarAcaoDeVisao(MiniTemplator t, IControleNegocio controleNegocio, String acao) {
		HashMap<String, IReferenciaVisaoAcao> acaoMetodos = this.getVisaoAcaoMetodosUtil();
		IReferenciaVisaoAcao acaoMetodo = acaoMetodos.get(acao); 
		if (acaoMetodo == null) {
			acaoMetodo = acaoMetodos.get(ACAO_PADRAO);
		}
		acaoMetodo.processar(t, controleNegocio);

	}

	@Override
	public String getCasoDeUso() {
		return "pagina";
	}

	protected void mesclarVariaveis(MiniTemplator t, IControleNegocio controleNegocio, String prefixo) {
		HashMap<String, Object> variaveis = controleNegocio.getVariaveis();
		if (prefixo != null) {
			prefixo = prefixo + ".";
		} else {
			prefixo = "";
		}
		Iterator<Entry<String, Object>> iterator = variaveis.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> next = iterator.next();
			Object valueObject = next.getValue();
			String valor = valueObject != null ? valueObject.toString() : "";
			t.setVariable(prefixo + next.getKey(), valor, true);
			
			//System.out.println(prefixo + next.getKey() + "." + valor + ".selected");

			// adicionado para poder utilizar componente combobox para qualquer valor e
			// voltar selecionado.
			// deve ser utilizado no HTML a variável: tablename.variavel.valor.selected
			//t.setVariable(prefixo + next.getKey() + "." + valor + ".selected", "selected", true);
			this.setVariableForSelectedOption(t, prefixo, next.getKey(), valor);
		}
	}
	protected void setVariableForSelectedOption(MiniTemplator t,String prefixo, String  coluna, String valor) {
		t.setVariable(prefixo + coluna + "." + valor + ".selected", "selected", true);
	}

	@Override
	public HashMap<String, IReferenciaVisaoAcao> getVisaoAcaoMetodosUtil() {
		HashMap<String, IReferenciaVisaoAcao> list = new HashMap<>();
		list.put(ACAO_PADRAO, this::processAcaoPadrao);
		HashMap<String, IReferenciaVisaoAcao> acaoMetodos = this.getVisaoAcaoMetodos();
		if (acaoMetodos != null) {
			list.putAll(acaoMetodos);
		}
		return list;
	}

	/**
	 * Deve retornar a lista de acoes suportadas pelo controlador de visão associado ao metodo que deverá ser envocado
	 * utiliza referecia para metodos (http://tecnopode.blogspot.com.br/2015/09/referencias-para-metodos-e-seu-uso.html
	 * https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
	 * Exemplo:
	 * <pre>
	 *  HashMap&lt;String, IReferenciaVisaoAcao&gt; acaoMetodos = new HashMap<>();
	 *  acaoMetodos.put("nomeAcao",this::acaoNomeAcao);//para ações que tem processamento na camada de controle(negocio)
	 *  acaoMetodos.put("nomeAcao2",null);//acao que não tem processamento na camada de controle
	 *  
	 *  return acaoMetodos;
	 * </pre>
	 * O metodo de ação deve ter a assinatura public Retorno acaoNomeAcao(MiniTemplator t, IControleNegocio controleNegocio)
	 * @return
	 */
	public abstract HashMap<String, IReferenciaVisaoAcao> getVisaoAcaoMetodos();
	
	/**Deve retornar a lista de VisaoEntryPoint que serão utilizados.
	 * A ordem de execução e a mesma ordem do retorno(FIFO)
	 * exemplo:
	 * <pre>
	 * List<VisaoEntryPoint> list = new ArrayList<>();
	 *	list.add(new VisaoEntryPoint(ENTRYPOINT_EDIT_DEPOIS, this::entryPointProcessaEditDepois));
	 *	list.add(new VisaoEntryPoint(ENTRYPOINT_NEW_DEPOIS, this::entryPointProcessaEditDepois));
	 *	return list;</pre>
	 * @return
	 */
	public abstract List<VisaoEntryPoint> getEntryPoints();
	
	public List<VisaoEntryPoint> getEntryPointsUtils(){
		List<VisaoEntryPoint> list = new ArrayList<>();
		List<VisaoEntryPoint> listExtra = this.getEntryPoints();
		if(listExtra!=null) {
			list.addAll(listExtra);
		}
		return list;
	
	}
}
