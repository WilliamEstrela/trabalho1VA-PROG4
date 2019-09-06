package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import anotacoes.Campo;
import conversores.AbstractConversor;
import interfaces.IConversor;
import modelo.Tabela;

public class Reflexao {
	public static Object getTabelaAtributoValor(Tabela<?> tab, String nomeAtributo) {
		Field atributo = Reflexao.getTabelaAtributoPorNome(tab, nomeAtributo);
		return Reflexao.getTabelaAtributoValor(tab, atributo);
	}
	public static Object getTabelaAtributoValor(Tabela<?> tab, Field metadadoAtributo) {
		String auxNome = obterNomeMetodoGetDoAtributo(metadadoAtributo);
		Object valor = Reflexao.invocarMetodo(tab, auxNome, null);
		return valor;
	}
	public static String obterNomeMetodoGetDoAtributo(Field metadadoAtributo) {
		return Reflexao.obterNomeMetodoAtributo(metadadoAtributo, "get");
	}
	public static String obterNomeMetodoSetDoAtributo(Field metadadoAtributo) {
		return Reflexao.obterNomeMetodoAtributo(metadadoAtributo, "set");
	}
	private static String obterNomeMetodoAtributo(Field metadadoAtributo, String prefixo) {
		String auxNome = prefixo+StringUtils.capitularizar(metadadoAtributo.getName());
		return auxNome;
	}
	public static void mudarTabelaAtributoValor(Tabela<?> objeto, String nomeAtributo, Object valor) {
		Field atributo = Reflexao.getTabelaAtributoPorNome(objeto, nomeAtributo);
		Reflexao.mudarTabelaAtributoValor(objeto, atributo, valor);
		
	}
	public static void mudarTabelaPkValor(Tabela<?> objeto, Object valor) {
		Field pkField = Reflexao.getTabelaAtributoPk(objeto);
		Reflexao.mudarTabelaAtributoValor(objeto, pkField, valor);
	}
	public static void mudarTabelaAtributoValor(Tabela<?> objeto, Field metadadoAtributo, Object valor) {
		String auxNome = Reflexao.obterNomeMetodoSetDoAtributo(metadadoAtributo);
		Method meth = Reflexao.obterMetodo(objeto.getClass(), auxNome,new Class<?>[] {metadadoAtributo.getType()});
		Reflexao.invocarMetodo(objeto, auxNome, new Object[] {valor});
	}
	public static Method obterMetodo(Class<?> cls, String nome,Class<?>[] parametroTipos) {
		Method meth = null;
		try {
			 meth = cls.getMethod(nome, parametroTipos);
		} catch (NoSuchMethodException |SecurityException e) {
			throw new RuntimeException(e);
		} 
		return meth;
		
	}
	public static Object invocarMetodo(Object obj, String nome, Object[] valores) {
		int tamanhoParametro = 0;
		if(valores!=null) {
			tamanhoParametro = valores.length;
		}
		Class<?> [] tipos = new Class[tamanhoParametro];
		Object retorno = null;
		for (int i=0; i<tamanhoParametro;i++) {
			tipos[i] = valores[i].getClass();
		}
		Method meth = Reflexao.obterMetodo(obj.getClass(), nome, tipos);
		try {
			retorno = meth.invoke(obj, valores);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return retorno;
		
	}
	public static Field[] getAtributosDaTabela(Tabela<?> tab) {
		List<Field> listaAtributos = new ArrayList<>();
		Class<?> cls = tab.getClass();
		while(cls!=null && Tabela.class.isAssignableFrom(cls)) {
			listaAtributos.addAll(
						new ArrayList(
									Arrays.asList(cls.getDeclaredFields()
									)
								)
						);
			cls = cls.getSuperclass();
		}
		Field[] atributos = new Field[listaAtributos.size()];
		for(int i = 0; i< atributos.length; i++) {
			atributos[i] = listaAtributos.get(i);
		}
		return atributos;
	}
	public static Object getTabelaAtributoPkValor(Tabela<?> tab) {
		Field pkField = Reflexao.getTabelaAtributoPk(tab);
		return Reflexao.getTabelaAtributoValor(tab, pkField);
	}
	public static Field getTabelaAtributoPorNome(Tabela<?> tab, String nome) {
		return Reflexao.getTabelaAtributoPorNome(tab, nome, false);
	}
	private static Field getTabelaAtributoPorNome(Tabela<?> tab, String nome,boolean pk) {
		if(tab==null) throw new RuntimeException("Paramentro do tipo Tabela<?> é obrigatório para utilizar o método");
		//TODO passar para um metodo com a responsabilidade de obter a lista de Fields
		//até a classe Tabela.
		Field[] campos = Reflexao.getAtributosDaTabela(tab);
		for(int i = 0; i< campos.length; i++) {
			Campo cmp = campos[i].getAnnotation(Campo.class);
			if(cmp!=null && ( 
						(pk=true && cmp.isPk()) ||
						(pk=false && campos[i].getName().equals(nome))
						)
				){
				return campos[i];
			}
		}
		throw new RuntimeException("A classe:"+tab.getClass().getName()+" "
				+ " não possui um atributo anotado com a anotação Campo("+(pk?"isPk=true":"nome="+nome)+")");	
	}
	public static Field getTabelaAtributoPk(Tabela<?> tab) {
		return Reflexao.getTabelaAtributoPorNome(tab, null, true);
	}
	public static boolean getTabelaUsarPkNaInsercao(Tabela<?> tab) {
		Class<?> cls = tab.getClass();
		Field[] campos = Reflexao.getAtributosDaTabela(tab);
		for(int i = 0; i< campos.length; i++) {
			Campo cmp = campos[i].getAnnotation(Campo.class);
			if(cmp!=null && cmp.usarPkNaInsercao()) {
				return true;
			}
		}
		return false;		
	}
	public static String getTabelaNome(Tabela<?> tab) {
		anotacoes.Tabela tblAnt = tab.getClass().getAnnotation(anotacoes.Tabela.class);
		if(tblAnt!=null) {
			return tblAnt.nome();
		}else {
			return tab.getClass().getSimpleName().toLowerCase();
		}
	}
	public static String getTabelaPkNome(Tabela<?> tab) {
		Field pkField = Reflexao.getTabelaAtributoPk(tab);
		return pkField.getAnnotation(Campo.class).nome();
	}
	
	public static List<Object> getTabelaCamposValor(Tabela<?> tab) {
		List<Object> listaCamposValor = new ArrayList<>();
		Field[] campos = Reflexao.getAtributosDaTabela(tab);
		for(int i=0;i<campos.length; i++) {
			Object valor = Reflexao.getTabelaAtributoValor(tab, campos[i]);
			listaCamposValor.add(valor);
		}
		return listaCamposValor;
	}
	@SuppressWarnings("rawtypes")
	public static List<IConversor> getTabelaCamposConvesor(Tabela<?> tab){
		List<IConversor> listaConversor = new ArrayList<>();
		Field[] campos = Reflexao.getAtributosDaTabela(tab);
		for(int i=0;i<campos.length;i++) {
			try {
				IConversor conv = Reflexao.getTabelaAtributoConversor(campos[i].getType());
				conv.setFieldData(campos[i]);
				listaConversor.add(conv);
			}catch(Exception e){
				throw new RuntimeException("Problema ao encontrar Convesor para o :"+campos[i].getName()+" Erro:"+e.getMessage());
			}
		}
		return listaConversor;
	}
	public static IConversor<?> getTabelaAtributoConversor(Class<?> atributo){
		Class<?> clsConversor = null;
		IConversor<?> conversor = null;
		try{
			String nomeConversor = AbstractConversor.PACOTE_CONVERSORES+"."+atributo.getSimpleName()+AbstractConversor.SUFIXO_CONVERSORES;			
			clsConversor = Class.forName(nomeConversor);
			conversor = (IConversor<?>) clsConversor.newInstance();
		}catch(ClassNotFoundException e) {
			if(atributo.getSuperclass()!=null) {
				return Reflexao.getTabelaAtributoConversor(atributo.getSuperclass());
			}				
			throw new RuntimeException(e);
		} catch (InstantiationException|IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		return conversor;
	}
	public static List<String> getTabelaCamposNome(Tabela<?> tab){
		List<String> camposNome = new ArrayList<>();
		Field[] campos = Reflexao.getAtributosDaTabela(tab);
		for(int i = 0; i< campos.length;i++) {
			String campoNome = getTabelaCampoNome(campos[i]);
			camposNome.add(campoNome);
		}
		return camposNome;
	}
	public static String getTabelaCampoNome(Field campo) {
		return campo.getAnnotation(Campo.class).nome();
	}
	public static List<String> getTabelaCamposObritatorios(Tabela<?> tab){
		List<String> camposNome = new ArrayList<>();
		Field[] campos = Reflexao.getAtributosDaTabela(tab);
		for(int i = 0; i< campos.length;i++) {
			if(Reflexao.isCampoObrigatorio(campos[i])) {
				String campoNome = getTabelaCampoNome(campos[i]);
				camposNome.add(campoNome);	
			}
		}
		return camposNome;
	}
	private static boolean isCampoObrigatorio(Field field) {
		Campo cmp = field.getAnnotation(Campo.class);
		if(cmp!=null && cmp.obrigatorio()) {
			return true;
		}
		return false;
	}

}
