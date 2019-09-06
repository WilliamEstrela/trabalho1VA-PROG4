package view.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import biz.source_code.miniTemplator.MiniTemplator;
import controle.CrudControle;
import interfaces.IControleNegocio;
import interfaces.IConversor;
import interfaces.ICrudControleNegocio;
import interfaces.IReferenciaVisaoAcao;
import interfaces.ITabelaConversor;
import modelo.Tabela;

/** Classe utilizada para tratar a visão de casos de uso de CRUD,
 * Possui as ações de processaNew (novo), processaListarTodos, processaProcurar, processaEdit (editar),
 * Essa classe tem relação direta com a camada de controle na classe {@link CrudControle}
 * @author guili
 *
 */
public abstract class VisaoCrudServletControleGenerica  extends VisaoServletControleGenerica{
	public static final String ENTRYPOINT_NEW_ANTES = "_VCSCG_ProcessaNewAntes";
	public static final String ENTRYPOINT_NEW_DEPOIS = "_VCSCG_ProcessaNewDepois";
	public static final String ENTRYPOINT_EDIT_ANTES = "_VCSCG_ProcessaEditAntes";
	public static final String ENTRYPOINT_EDIT_DEPOIS = "_VCSCG_ProcessaEditDepois";
	
	public void mesclarMapValores(MiniTemplator t,ICrudControleNegocio<Tabela<?>,?> crudControleNegocio, HashMap<String,Object> map) {
		this.mesclarMapValores(t,crudControleNegocio, map,null);
	}

	public void mesclarMapValores(MiniTemplator t, ICrudControleNegocio<Tabela<?>,?> crudControleNegocio, HashMap<String,Object> map, String prefixo) {
		prefixo=prefixo==null||prefixo.equals("")?"":prefixo+".";
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, Object> coluna = iterator.next();
			String nomeColuna = prefixo+coluna.getKey();
			Object valorOriginal = coluna.getValue();
			String valorColuna = valorOriginal==null?"":valorOriginal.toString();
			t.setVariable(nomeColuna, valorColuna,true);
			//para ser utilizado com campos select
			//variavel no padraão. prefixo.campo.valor.selected
			String nomeVariavelSelected = nomeColuna+"."+valorColuna+".selected";
			//System.out.println("nomeVariavelSelected:"+nomeVariavelSelected);
			t.setVariable(nomeVariavelSelected,"selected",true);
			mesclarMapEstrangeiro(t, crudControleNegocio,prefixo, nomeColuna,valorOriginal, valorColuna);
			
			
			
		}
	}

	@SuppressWarnings("rawtypes")
	public void mesclarMapEstrangeiro(MiniTemplator t,ICrudControleNegocio<Tabela<?>,?> crudControleNegocio, String prefixo,String nomeColuna, Object valorOriginal, String valorColuna) {
		Tabela tabelaPrincipal = crudControleNegocio.getNovoObjeto();
		int indColuna;
		try{
			indColuna = tabelaPrincipal.getCampoIndice(nomeColuna);
		}catch (Exception e) {
			return;
		}
		//System.out.println("mesclarMapEstrangeira:indcoluna:"+indColuna+" nomecoluna:"+nomeColuna);
		IConversor conv = (IConversor)tabelaPrincipal.getCamposConversorUtil().get(indColuna);
		
		if(conv instanceof ITabelaConversor) {
			ITabelaConversor convTabela = (ITabelaConversor)conv;
			Tabela tabelaEstrangeira = (Tabela)convTabela.getTabela();
			String tabelaEstrangeiraNome = tabelaEstrangeira.getTabelaNome();
			String prefixoOriginal = prefixo+(prefixo.equals("")?"":".")+tabelaEstrangeiraNome;
			if(valorOriginal==null || valorOriginal.equals("")) {
				valorOriginal = tabelaEstrangeira;
			}
			Tabela valorOriginalTabela = (Tabela)valorOriginal;
			HashMap<String,Object> mapOrignal = this.getMapValoresFromTabela(valorOriginalTabela) ;
			this.mesclarMapValores(t,crudControleNegocio, mapOrignal, prefixoOriginal);
			t.setVariable(tabelaEstrangeiraNome, valorColuna,true);		
		}
				

	}
	
	@SuppressWarnings("unchecked")
	private void processaListarTodos(MiniTemplator t,IControleNegocio controleNegocio) {
		ICrudControleNegocio<Tabela<?>,?> crudControleNegocio = (ICrudControleNegocio<Tabela<?>,?>)controleNegocio;
		List<Tabela<?>> listAllObject = crudControleNegocio.listar();	
		this.mesclarListarOuProcurar(t, crudControleNegocio, listAllObject);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void processaProcurar(MiniTemplator t,IControleNegocio controleNegocio) {
		ICrudControleNegocio<Tabela<?>,?> crudControleNegocio = (ICrudControleNegocio<Tabela<?>,?>)controleNegocio;
		List<Tabela<?>> listAllObject = crudControleNegocio.procurar();	
		this.mesclarListarOuProcurar(t, crudControleNegocio, listAllObject);
		
		Tabela table = crudControleNegocio.getObjetoPreenchido();
		
		this.processaTodasTabelaEstrangeira(t, table,crudControleNegocio);
	}
	public void mesclarListarOuProcurar(MiniTemplator t,ICrudControleNegocio<Tabela<?>,?> crudControleNegocio,List<Tabela<?>> listAllObject) {	
		for(Tabela<?> tabela: listAllObject) {
			this.mesclarMapValores(t,crudControleNegocio, this.getMapValoresFromTabela(tabela));
			t.setVariable("acaoEditar", CrudControle.EDITAR,true);
			t.setVariable("acaoApagar", CrudControle.APAGAR,true);
			
			t.addBlock(crudControleNegocio.getCasoDeUso(),true);
		}
		this.mesclarVariaveis(t, crudControleNegocio);
	}

	protected void processNew(MiniTemplator t,
			IControleNegocio controleNegocio) {
		
		this.executarEntryPoint(ENTRYPOINT_NEW_ANTES, t, controleNegocio);
		
		@SuppressWarnings("unchecked")
		ICrudControleNegocio<Tabela<?>,?> crudControleNegocio = (ICrudControleNegocio<Tabela<?>,?>)controleNegocio;
		
		this.mesclarVariaveis(t, controleNegocio);
		this.processaTodasTabelaEstrangeira(t, crudControleNegocio.getNovoObjeto(),crudControleNegocio);
		
		t.setVariable("acao", CrudControle.INCLUIR,true);
		
		if(!crudControleNegocio.getNovoObjeto().getUsarPkNaInsercao()) {
		//adicionado para tornar o campo da pk readonly utilizar a variavel pkname_readonly quando a chave primaria não é usada na inserçao
			String nomeVariavel = crudControleNegocio.getNovoObjeto().getTabelaPKNomeUtil()+"_readonly";
			t.setVariable(nomeVariavel, "readonly",true);
			//System.out.println("processNew: nomeVariavelPKReadonly: "+nomeVariavel);
		}
		
		this.executarEntryPoint(ENTRYPOINT_NEW_DEPOIS, t, controleNegocio);
		
	}
	
	@SuppressWarnings("unchecked")
	private void processEdit(MiniTemplator t,IControleNegocio controleNegocio) {
		
		this.executarEntryPoint(ENTRYPOINT_EDIT_ANTES, t, controleNegocio);
		
		ICrudControleNegocio<Tabela<?>,?> crudControleNegocio = (ICrudControleNegocio<Tabela<?>,?>)controleNegocio;
		t.setVariable("acao", CrudControle.ALTERAR,true);
		try {
			Tabela<?> tabela = crudControleNegocio.editar();
			this.processarTabela(t, tabela,crudControleNegocio);
			
			this.processaTodasTabelaEstrangeira(t, tabela,crudControleNegocio);
			
			
			//adicionado para tornar o campo da pk readonly utilizar a variavel pkname_readonly
			t.setVariable(crudControleNegocio.getNovoObjeto().getTabelaPKNomeUtil()+"_readonly", "readonly",true);
		}catch(Exception e) {
			t.setVariable("message", e.getMessage(),true);
			e.printStackTrace();
			//TODO verificar problema de não exibir mensagem de visão
		}
		
		this.mesclarVariaveis(t, crudControleNegocio);
		
		this.executarEntryPoint(ENTRYPOINT_EDIT_DEPOIS, t, controleNegocio);
		
	}

	@SuppressWarnings("rawtypes")
	public void processaTodasTabelaEstrangeira(MiniTemplator t, Tabela<?> tabelaPrincipal, ICrudControleNegocio<Tabela<?>, ?> crudControleNegocio) {
		List<IConversor> lista  = tabelaPrincipal.getCamposConversorUtil();
		for(int i=0; i<lista.size();i++){
			IConversor conv = lista.get(i);
			if(conv instanceof ITabelaConversor) {
				ITabelaConversor tabConv = (ITabelaConversor) conv;
				Tabela tabelaEstrangeira = (Tabela)tabConv.getTabela();
				String nomeCampo = tabelaPrincipal.getCamposNome().get(i);
				Tabela valorCampoFK = (Tabela)tabelaPrincipal.getCampoValor(nomeCampo);
				
				List<Tabela> listaEstrangeiro = crudControleNegocio.procurarTodos(tabelaEstrangeira);
				
				processarTabelaEstrangeira(t, crudControleNegocio, valorCampoFK, listaEstrangeiro);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void processarTabelaEstrangeira(MiniTemplator t, ICrudControleNegocio<Tabela<?>, ?> crudControleNegocio,
			Tabela valorCampoFK, List<Tabela> listaEstrangeiro) {
		processarTabelaEstrangeira(t, crudControleNegocio, valorCampoFK, listaEstrangeiro, "estrangeiro");
	}
	@SuppressWarnings("rawtypes")
	public void processarTabelaEstrangeira(MiniTemplator t, ICrudControleNegocio<Tabela<?>, ?> crudControleNegocio,
			Tabela valorCampoFK, List<Tabela> listaEstrangeiro, String prefixo) {
		if(listaEstrangeiro == null || listaEstrangeiro.isEmpty()) {
			return;
		}
		String auxPrefixo = prefixo!=null&&!prefixo.equals("")?prefixo+".":"";
		String tabelaEstrangeiraNome = listaEstrangeiro.get(0).getTabelaNome();
		String prefixoEstrangeiro = auxPrefixo+tabelaEstrangeiraNome;
		String nomeBlocoEstrangeiro = auxPrefixo+tabelaEstrangeiraNome;
		for(Tabela linha:listaEstrangeiro) {
			this.mesclarMapValores(t, crudControleNegocio, this.getMapValoresFromTabela(linha),prefixoEstrangeiro);
			String nome_selected_variable = auxPrefixo+tabelaEstrangeiraNome+".selected";
			if(valorCampoFK!=null && 
					linha.getPk().equals(valorCampoFK.getPk())) {						
				t.setVariable(nome_selected_variable, "selected",true);
			}else {
				t.setVariable(nome_selected_variable, "",true);
			}
			t.addBlock(nomeBlocoEstrangeiro,true);
		}
	}
	
	private void processarTabela(MiniTemplator t,Tabela<?> table, ICrudControleNegocio<Tabela<?>, ?> crudControleNegocio){
		
		this.mesclarMapValores(t,crudControleNegocio, this.getMapValoresFromTabela(table),table.getTabelaNome().toLowerCase());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, Object> getMapValoresFromTabela(Tabela<?> tabela){
		HashMap<String, Object> mapValores = new HashMap<>();
		List<String> camposNomes = tabela.getCamposNome();
		List<Object> camposValores = tabela.getCamposValorUtil();
		List<IConversor> listConversor = tabela.getCamposConversorUtil();
		
		for (int i = 0; i < camposNomes.size(); i++) {
			String nome = camposNomes.get(i);
			Object valor = camposValores.get(i);
			String valorTela = listConversor.get(i).converterBdParaVisao(valor);
			mapValores.put(nome+"_tela", valorTela);
			mapValores.put(nome, valor);
		}
		return mapValores;
	}


	@SuppressWarnings("unchecked")
	protected void mesclarVariaveis(MiniTemplator t,IControleNegocio controle){
		ICrudControleNegocio<Tabela<?>,?> crudController = (ICrudControleNegocio<Tabela<?>,?>)controle;
		
		Tabela<?> tabela = crudController.getNovoObjeto();
		String prefixo = tabela.getTabelaNome().toLowerCase();
		
		this.mesclarVariaveis(t,crudController,prefixo);		
	}
	
	@Override
	public HashMap<String, IReferenciaVisaoAcao> getVisaoAcaoMetodosUtil() {
		HashMap<String, IReferenciaVisaoAcao> list = super.getVisaoAcaoMetodosUtil();
		list.put(CrudControle.LISTAR_TODOS, this::processaListarTodos);
		list.put(CrudControle.NOVO,this::processNew);
		list.put(CrudControle.EDITAR, this::processEdit);
		list.put(CrudControle.EXCLUIR, this::processEdit);//TODO verificar se naõ tem problema, pois ficou sem entry point
		list.put(CrudControle.PROCURAR, this::processaProcurar);
		return list;
	}

}