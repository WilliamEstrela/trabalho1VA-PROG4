package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interfaces.IReferenciaAcaoNegocio;
import interfaces.ICrudControleNegocio;
import interfaces.IDAO;
import modelo.Tabela;
import persistencia.DAOGeneric;
import util.NegocioEntryPoint;
import util.Retorno;

public abstract class CrudControle<TABELA extends Tabela<TipoPK>, TipoPK> extends Controle
		implements ICrudControleNegocio<TABELA, TipoPK> {
	
	public static final String ENTRYPOINT_INCLUIR_ANTES  = "_CCEntryPointIncluirAntes";
	public static final String ENTRYPOINT_INCLUIR_DEPOIS = "_CCEntryPointIncluirDepois";
	public static final String ENTRYPOINT_ALTERAR_ANTES  = "_CCEntryPointAlterarAntes";
	public static final String ENTRYPOINT_ALTERAR_DEPOIS = "_CCEntryPointAlterarDepois";
	public static final String ENTRYPOINT_APAGAR_ANTES  = "_CCEntryPointApagarAntes";
	public static final String ENTRYPOINT_APAGAR_DEPOIS = "_CCEntryPointApagarDepois";
	
	public static final String LISTAR_TODOS = "listarTodos";
	public static final String PROCURAR = "procurar";
	public static final String EDITAR = "editar";
	public static final String NOVO = "novo";
	public static final String APAGAR = "apagar";
	/**
	 * Para exibir tela de confirmação da exclusão
	 */
	public static final String EXCLUIR = "excluir";
	public static final String ALTERAR = "alterar";
	public static final String INCLUIR = "incluir";
	protected IDAO dao;

	public CrudControle() {
		dao = DAOGeneric.getIntancia();
		this.tabelaClass = this.getNovoObjeto();
	}
	

	@Override
	public List<TABELA> listar() {
		return dao.listar(this.getNovoObjeto());
	}

	@Override
	public List<TABELA> procurar(TABELA tab) {
		List<TABELA> list = dao.procurar(tab);
		return list;
	}

	@Override
	public Retorno incluir(TABELA tab) {
		Retorno retorno = dao.incluir(tab);
		return retorno;
	}

	@Override
	public Retorno alterar(TABELA tab) {
		Retorno retorno = dao.alterar(tab);
		return retorno;
	}

	@Override
	public Retorno remover(TABELA objPk) {		
		return dao.remover(objPk);
	}

	@Override
	public TABELA editar(TABELA objPk) {
		return dao.getObjeto(objPk);

	}

	@Override
	public abstract TABELA getNovoObjeto();
	
// PARTE NOVA
	

	protected Retorno acaoRemover(String acao) {
		Retorno ret = this.remover();
		if(ret.isSucesso()){
			ret.setSucesso(true);
			ret.addMensagem("Remoção na tabela "+ this.tabelaClass.getTabelaNome()+" realizada com sucesso!");
		}else{
			ret.setSucesso(false);
			String mensagem = "Remoção na tabela "+ this.tabelaClass.getTabelaNome()+" falhou! \n Erro:"+ret.getMensagem();
			ret.getMensagens().clear();
			ret.addMensagem(mensagem);
		}
		return ret;
	}

	protected Retorno acaoAlterar(String acao) {
		Retorno ret = this.alterar();
		if(ret.isSucesso()){
			ret.setSucesso(true);
			ret.addMensagem("Alteração na tabela "+ this.tabelaClass.getTabelaNome()+" realizada com sucesso!");
		}else{
			ret.setSucesso(false);
			String mensagem = "Alteração na tabela "+ this.tabelaClass.getTabelaNome()+" falhou! \n Erro:"+ret.getMensagem();
			ret.getMensagens().clear();
			ret.addMensagem(mensagem);
		}
		return ret;
	}

	protected Retorno acaoIncluir(String Acao) {
		Retorno ret = this.incluir();
		if(ret.isSucesso()){
			ret.setSucesso(true);
			ret.addMensagem("Inclusao na tabela "+ this.tabelaClass.getTabelaNome()+" realizada com sucesso!");
		}else{
			ret.setSucesso(false);
			String mensagem = "Inclusao na tabela "+ this.tabelaClass.getTabelaNome()+" falhou!";
			List<String> mensagensErro = new ArrayList<>(ret.getMensagens());
			ret.getMensagens().clear();
			ret.addMensagem(mensagem);
			ret.getMensagens().addAll(mensagensErro);
		}
		return ret;
	}



	protected TABELA tabelaClass;
	

	@Override
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodosUtil() {
		HashMap<String, IReferenciaAcaoNegocio> acaoMetodos = super.getAcaoMetodosUtil();
		acaoMetodos.put(NOVO, null);//ação NOVO não tem processamento no negocio somente visao
		acaoMetodos.put(EDITAR,null);//ação EDITAR não tem processamento no negocio somente visao
		acaoMetodos.put(INCLUIR, this::acaoIncluir);//acao incluir tem processamento no negocio
		acaoMetodos.put(ALTERAR, this::acaoAlterar);//acao alterar tem processamento no negocio
		acaoMetodos.put(EXCLUIR, null);//acao Exibir dados para excluir
		acaoMetodos.put(APAGAR,this::acaoRemover);//acao apagar tem processamento no negocio
		acaoMetodos.put(LISTAR_TODOS, null);//acao LISTAR_TODOS nao tem processamento no negocio somente visao
		acaoMetodos.put(PROCURAR,null);
		return acaoMetodos;
	}

	protected List<NegocioEntryPoint> getEntryCrudControlePoints(){
		List<NegocioEntryPoint> list = new ArrayList<>();
		list.add(new NegocioEntryPoint(ENTRYPOINT_ACAO_ANTES, this::entryPointPreencherTabelaComVariaveis));
		return list;
	}
	@Override
	public List<NegocioEntryPoint> getEntryPointsUtils(){
		List<NegocioEntryPoint> list = new ArrayList<>();
		list.addAll(this.getControleEntryPoints());
		//preenche a tabelaClass com os dados das variáveis.
		list.addAll(this.getEntryCrudControlePoints());
		
		List<NegocioEntryPoint> listExtra = this.getEntryPoints();
		if(listExtra!=null) {
			list.addAll(listExtra);
		}
		return list;
	}


	/**
	 * retorna uma lista de valores com os valores correspondentes aos campos 
	 * da Tabela, pois na lista de Valores Pode ter mais informações que a lista de valores
	 * @return
	 *///getObjectValuesList
	protected List<Object> getTabelaCamposValoresDasVariaveis(){
		return this.getTabelaCamposValoresDasVariaveis(this.tabelaClass);
	}
	
	
	public List<Object> getTabelaCamposValoresDasVariaveis(Tabela<?> tabela){
		if(tabela == null) return null;
		
		List<String> columns = tabela.getCamposNome();
		List<Object> columnsValues = new ArrayList<Object>();
		for(int i=0;i<columns.size();i++){
			Object valor =  this.variaveis.get(columns.get(i));
			columnsValues.add(valor);
		}
		
		return columnsValues;
	}
	
	
	//entry point antes acao
	protected Retorno entryPointPreencherTabelaComVariaveis(String acao) {
		Retorno retorno = this.preencherTabelaComVariaveis();
		retorno.setAcao(acao);
		return retorno;
	}


	public Retorno preencherTabelaComVariaveis() {
		this.tabelaClass.limparColunas();;
		Retorno ret = this.tabelaClass.setCamposValorUtil(this.getTabelaCamposValoresDasVariaveis());
		return ret;
	}

	@Override
	public Retorno incluir() {
		Retorno retEntryPointAntes = this.executarEntryPoint(ENTRYPOINT_INCLUIR_ANTES, INCLUIR);
		if(!retEntryPointAntes.isSucesso()) {
			return retEntryPointAntes;
		}

		Retorno retAcao = this.incluir(this.tabelaClass);
		retAcao.AddMensagens(retEntryPointAntes.getMensagens());
		if(!retAcao.isSucesso()) {
			retAcao.setAcao(NOVO);
			return retAcao;
		}
		retAcao.setAcao(ACAO_PADRAO);
		
		return this.executarEntryPoint(ENTRYPOINT_INCLUIR_DEPOIS, retAcao.getAcao());
	}


	@Override
	public Retorno remover() {	
		Retorno retEntryPointAntes = this.executarEntryPoint(ENTRYPOINT_APAGAR_ANTES, APAGAR);
		if(!retEntryPointAntes.isSucesso()) {
			return retEntryPointAntes;
		}
		
		Retorno retAcao = this.remover(this.tabelaClass);
		retAcao.AddMensagens(retEntryPointAntes.getMensagens());
		if(!retAcao.isSucesso()) {
			retAcao.setAcao(ACAO_PADRAO);
			return retAcao;
		}
		retAcao.setAcao(ACAO_PADRAO);
		
		return this.executarEntryPoint(ENTRYPOINT_APAGAR_DEPOIS, retAcao.getAcao());
	}

	@Override
	public Retorno alterar() {	
		Retorno retEntryPointAntes = this.executarEntryPoint(ENTRYPOINT_ALTERAR_ANTES, ALTERAR);
		if(!retEntryPointAntes.isSucesso()) {
			return retEntryPointAntes;
		}
		
		Retorno retAcao = this.alterar(this.tabelaClass);
		retAcao.AddMensagens(retEntryPointAntes.getMensagens());
		if(!retAcao.isSucesso()) {
			retAcao.setAcao(EDITAR);
			return retAcao;
		}
		retAcao.setAcao(ACAO_PADRAO);
		
		return this.executarEntryPoint(ENTRYPOINT_ALTERAR_DEPOIS, retAcao.getAcao());
	}
	

	@Override
	public TABELA editar() {
		Retorno ret = preencherTabelaComVariaveis();
		if(!ret.isSucesso()) {
			throw new RuntimeException("Erro Editar("+this.getClass().getSimpleName()+") mensagem:"+ret.getMensagem());
		}			
		return this.editar(this.tabelaClass);
	}
	
	@Override
	public List<TABELA> procurar() {
		Retorno ret = preencherTabelaComVariaveis();
		if(!ret.isSucesso()) {
			throw new RuntimeException("Erro Editar("+this.getClass().getSimpleName()+") mensagem:"+ret.getMensagem());
		}				
		return this.procurar(this.tabelaClass);
	}
	
	public <T extends Tabela<?>> List<T> procurarTodos(T tab){
		if(tab==null) return null;
		return DAOGeneric.getIntancia().procurar(tab);
	}

	@Override
	public TABELA getObjetoPreenchido() {
		Retorno ret = preencherTabelaComVariaveis();
		if(!ret.isSucesso()) {
			throw new RuntimeException("Erro preenchendo dados da visão no modelo:"+ret.getMensagem());
		}
		return this.tabelaClass;
	}

	public <T extends Tabela<?>> T getDadosExtrangeiro(T tab){
		if(tab==null) return null;
		return DAOGeneric.getIntancia().getObjeto(tab);
	}
}
