package model;

import anotacoes.BooleanConversorParameteros;
import anotacoes.Campo;
import modelo.Tabela;

public class Vlan extends Tabela<Integer> {
	
	@Campo(isPk=true, nome="vlan",nomeTela="VLAN",usarPkNaInsercao=true,obrigatorio=true)
	private Integer vlan;
	@Campo(nome="nome", nomeTela="Nome", obrigatorio=true)
	private String nome;
	@Campo(nome="ativo",nomeTela="Ativo",obrigatorio=true)
	@BooleanConversorParameteros(textoVerdadeiro="ATIVO",textoFalso="INATIVO")
	private Boolean ativo;
	
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getVlan() {
		return this.vlan;
	}

	public void setVlan(Integer vlan) {
		this.vlan = vlan;
	}

/*	@Override
	public String getTabelaNome() {
		return "vlan";
	}*/

/*	@Override
	public Tabela<?> getNovoObjeto() {
		return new Vlan();
	}*/
	
/*	@SuppressWarnings("rawtypes")
	@Override
	public List<IConversor> getCamposConversor() {
		ArrayList<IConversor> listConversor = new ArrayList<>();
		listConversor.add(new IntegerConversor());//pk		
		listConversor.add(new StringConversor());
		listConversor.add(new BooleanConversor("ATIVO","INATIVO"));
		return listConversor ;
	}*/

}
