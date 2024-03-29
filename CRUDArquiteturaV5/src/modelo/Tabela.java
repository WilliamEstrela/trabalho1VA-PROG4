package modelo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.swing.Icon;
import javax.swing.plaf.synth.SynthSeparatorUI;

import anotacoes.Campo;
import conversores.AbstractConversor;
import interfaces.IConversor;
import persistencia.DAOGeneric;
import util.Reflexao;
import util.Retorno;
import util.StringUtils;
 
public abstract class Tabela<TipoPK> {
	
	public boolean getUsarPkNaInsercao() {
		return Reflexao.getTabelaUsarPkNaInsercao(this);
	}

	public TipoPK getPk() { 
		TipoPK valorPk = (TipoPK) Reflexao.getTabelaAtributoPkValor(this);
		return valorPk;

	}

	public void setPk(TipoPK pk) {
		Reflexao.mudarTabelaPkValor(this, pk);
	}
	
	
	/** retorno o nome da tabela para utilizar na persit�ncia
	 * @return nome da tabela
	 */
	public String getTabelaNome() {
		return Reflexao.getTabelaNome(this);
	}
	/** retorno o nome da coluna da chave primaria
	 * @return
	 */
	public  String getTabelaPKNome() {
		return Reflexao.getTabelaPkNome(this);
	}
	
	public String getTabelaPKNomeUtil() {
		if(this.getTabelaPKNome()== null) {
		
		}
		String pkNome = this.getTabelaPKNome();
		for(String campoNome: this.getCamposNome()) {
			if (campoNome.equals(pkNome)) {
				return pkNome;
			}
		}
		String msg = "O M�todo "+this.getClass().getName()+".getTabelaPKNome()"
				+ " est� retornando um nome de coluna que n�o existe, "
				+ " valor retornado:"+pkNome+ " valores v�lidos: "+String.join(",",this.getCamposNome());
		throw new RuntimeException(msg);
		
	}
	/** retorna a lista de valor dos campos
	 * @return
	 */
	public  List<Object> getCamposValor(){
		return Reflexao.getTabelaCamposValor(this);

	}
	public List<Object> getCamposValorUtil(){
		List<Object> list = this.getCamposValor();
		if(list.size()!=this.getNumerosCampos()){
			
			String msg = "O M�todo "+this.getClass().getName()+".getCamposValor()"
					+ " deveria retornar uma lista com "+this.getNumerosCampos()
					+ " item/itens e retornou com "+list.size()+" item/itens!";
			throw new RuntimeException(msg);
			
		}
		return list;
	}
	
	/** retorna uma lista com objetos conversores para converter
	 * um string no tipo do atributo.
	 * @return
	 */
	
	@SuppressWarnings("rawtypes")
	public List<IConversor> getCamposConversor(){
		return Reflexao.getTabelaCamposConvesor(this);
	}
	
	@SuppressWarnings("rawtypes")
	public List<IConversor> getCamposConversorUtil(){
		List<IConversor> list = this.getCamposConversor();
		if(list.size()!=this.getNumerosCampos()){
			
			String msg = "O M�todo "+this.getClass().getName()+".getCamposConversor()"
					+ " deveria retornar uma lista com "+this.getNumerosCampos()
					+ " item/itens e retornou com "+list.size()+" item/itens!";
			throw new RuntimeException(msg);
			
		}
		return list;
	}
	
	/** retorna uma lista com o nome dos campos
	 * @return
	 */
	public  List<String> getCamposNome(){
		return Reflexao.getTabelaCamposNome(this);
	}
	
	protected List<String> getCamposObrigatorios(){
		return Reflexao.getTabelaCamposObritatorios(this);
	}
	/*{//Seria necess�rio se n�o quisesse quebra o c�digo
		List<String> list = new ArrayList<String>();
		return list;
	}*/
	
	public List<String> getCamposObrigatoriosUtil(){
		List<String> camposValidos = this.getCamposNome();
		List<String> camposObrigatorios = this.getCamposObrigatorios();
		
		for (String campoNome : camposObrigatorios) {
			boolean encontrou = false;
			for (String campoValido : camposValidos) {
				if(campoNome.equals(campoValido)){
					encontrou = true;
					break;
				}
			}
			if(encontrou== false){
				throw new RuntimeException(
						"Campo "+campoNome+
						" n�o � um nome v�lido para:"+
								this.getClass().getName()+
								" Erro no metodo getCamposObrigatorios()!");
			}
		}
		return camposObrigatorios;
				
	}
	/** metodo para configurar os valores do objeto.
	 * ele s� � chamado se a lista tiver o n�mero correto de valores
	 * @param list
	 * @return
	 */
	protected Retorno setCamposValor(List<Object> list) {
		Retorno ret = new Retorno(true, null);
		Class<?> cls = this.getClass();
		Field[] campos = cls.getDeclaredFields();
		System.out.println("Lista:"+list);
		for(int i = 0; i< campos.length; i++) {
			Campo cmp = campos[i].getAnnotation(Campo.class);
			if(cmp!=null) {
				String auxNome = "set"+StringUtils.capitularizar(campos[i].getName());
				Method meth = null;
				try {
					//comentario debut
					String tipoValorLista= "";
					if(list.get(i)!=null) {
						tipoValorLista = list.get(i).getClass().getTypeName();
					}
					System.out.println("nome:"+campos[i].getName()+" tipo:"+campos[i].getType()+" tipoValorDaLista:"+tipoValorLista);
					//debug
					
					meth = cls.getMethod(auxNome, campos[i].getType());
					meth.invoke(this, new Object[] {list.get(i)});
				} catch (NoSuchMethodException | SecurityException | 
						IllegalArgumentException  e) {
					throw new RuntimeException("Classe: "+this.getClass().getName()+" n�o possui "
							+ "metodo para configurar o campo="+campos[i].getName()+" deveria ter:"+auxNome);
				} catch (IllegalAccessException e) {
					throw new RuntimeException("Na Classe: "+this.getClass().getName()+" "
							+ "O m�todo:"+auxNome +" n�o � publico");
				} catch(InvocationTargetException e) {
					ret.setSucesso(false);
					ret.addMensagem("Erro ao configura campos, na Classe:"+this.getClass().getName()+""
							+ " no metodo:"+auxNome+" ERROR:"+e.getMessage());
					
				}
			}
		}	
		return ret;
	}
	
	/** metodo utilizado para retornar um novo objeto da tabela implementada
	 * @return
	 */
	public Tabela<?> getNovoObjeto(){
		Tabela<?> novoObjeto = null;
		try {
			novoObjeto = this.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return novoObjeto;
	}
	
	public Retorno setCamposValorUtil(List<Object> list){
		Retorno ret = new Retorno(true,null);
		if(list==null){
			ret.setSucesso(false);
			ret.addMensagem("Necess�rio passar a lista de valores para configura objeto");
			return ret;
		}
		if(list.size()!=this.getNumerosCampos()){
			ret.setSucesso(false);
			ret.addMensagem("Necess�rio passar a lista de valores para configura objeto com " + this.getNumerosCampos());
			return ret;
		}
		//metodo abstrato que realmente configura os valores

		@SuppressWarnings("rawtypes")
		List<IConversor> listaConversores = this.getCamposConversorUtil();
		List<String> listaCamposNomes = this.getCamposNome();
		for (int i = 0; i < list.size(); i++) {
			Object valor = list.get(i);
			if(valor instanceof String || valor instanceof Integer){
				try{
					valor = listaConversores.get(i).converter(String.valueOf(valor));
				}catch(RuntimeException e){
					ret = new Retorno(false,
							"Erro de Convers�o no campo:"+listaCamposNomes.get(i)+", ERRO:"+e.getMessage());
					return ret;
					
				}
				list.set(i, valor);
			}
		}
		
		ret = this.setCamposValor(list);
		return ret;
	}
	
	
	public int getNumerosCampos(){
		return this.getCamposNome().size();
	}
	

	
	public String toString(){
		List<String> camposNome = this.getCamposNome();
		List<Object> camposValor = this.getCamposValorUtil();
		String resultado = "";
		for(int i=0;i<camposNome.size();i++){
			resultado = resultado + camposValor.get(i);
		}
		return resultado.substring(1);
	}
	



	public int getColunaPK() {
		return this.getCampoIndice(this.getTabelaPKNomeUtil());
	}
	public int getCampoIndice(String nome) {
		int indice = this.getIndiceColuna(nome);
		if(indice>=this.getCamposNome().size()) {
			throw new RuntimeException("Coluna: "+nome+" N�o pertence a tabela:"+this.getClass().getName());
		}
		return indice;
	}
	public Object getCampoValor(String nome) {
		List<String> camposNome = this.getCamposNome();
		if(!camposNome.contains(nome)) {
			throw new RuntimeException("Campo: "+nome+" n�o existe na tabela:"+this.getTabelaNome());
		}
		List<Object> camposValor = this.getCamposValorUtil();
		return camposValor.get(this.getCampoIndice(nome));
	}

	private int getIndiceColuna(String nomeColuna) {
		int indice = 0;
		List<String> camposNome = this.getCamposNome();
		for (String coluna : camposNome) {
			if(coluna.equals(nomeColuna) == true){
				break;
			}
			indice++;
		}
		return indice;
		
	}
	
	public void limparColunas() {
		
		int qtdCampos = this.getCamposValorUtil().size();
		List<Object> valorVazios = new ArrayList<>();
		for (int i = 0; i < qtdCampos; i++) {
			valorVazios.add(null);			
		}
		this.setCamposValor(valorVazios);
	}
	public <T extends Tabela<?>> T getDadosExtrangeiro(T tab){
		if(tab==null) return null;
		return DAOGeneric.getIntancia().getObjeto(tab);
	}
}
