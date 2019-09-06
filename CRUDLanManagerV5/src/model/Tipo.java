package model;

import java.util.ArrayList;
import java.util.List;

import anotacoes.Campo;
import conversores.IntegerConversor;
import conversores.StringConversor;
import interfaces.IConversor;
import modelo.Tabela;
import util.Retorno;

public class Tipo extends Tabela<Integer> {
	
	@Campo(isPk = true, nome="idtipo", nomeTela="ID")
	private Integer idTipo;
	
	@Campo(nome="tipo", nomeTela="Tipo", obrigatorio=true)
	private String tipo;

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	// metodos abstratos implementaos
	 
	
	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	@Override
	public String getTabelaNome() {
		return "tipo";
	}
	


/*	@Override
	public List<String> getCamposNome(){
		ArrayList<String> listNomes = new ArrayList<>();
		listNomes.add("idtipo");
		listNomes.add("tipo");
		return listNomes ;
	}*/

/*	@Override
	protected Retorno setCamposValor(List<Object> list) {
		Retorno ret = new Retorno(true, null);
		try{
			this.setPk((Integer) list.get(0));
			this.setTipo((String) list.get(1) );
 
		}catch(Exception e){
			ret.setSucesso(false);
			ret.addMensagem("Erro ao configura campos, ERROR:"+e.getMessage());
		}
				
		return ret;
	}*/

/*	@Override
	public List<Object> getCamposValor() {
		ArrayList<Object> list = new ArrayList<>();

		list.add(this.getPk());
		list.add(this.getTipo());

		return list;
	}*/

	@Override
	public Tabela<?> getNovoObjeto() {
		return new Tipo();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<IConversor> getCamposConversor() {
		ArrayList<IConversor> listConversor = new ArrayList<>();
		listConversor.add(new IntegerConversor());//pk		
		listConversor.add(new StringConversor());
		return listConversor ;
	}
/*	@Override
	protected List<String> getCamposObrigatorios() {
		List<String> list = new ArrayList<>();
		list.add("tipo");
		return list;
	}*/

}
