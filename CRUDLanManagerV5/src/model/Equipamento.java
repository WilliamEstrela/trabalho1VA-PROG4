package model;

import anotacoes.Campo;
import modelo.Tabela;

public class Equipamento extends Tabela<Integer> {

	@Campo(isPk=true,nome="idequipamento",nomeTela="ID")
	private Integer idEquipamento;
	
	@Campo(nome="porta_idporta", nomeTela="Porta", obrigatorio=true)
	private Porta porta;
	
	@Campo(nome="nome", nomeTela="Nome", obrigatorio=true)
	private String nome;
	
	@Campo(nome="descricao", nomeTela="Descrição")
	private String descricao;
	
	@Campo(nome="observacao", nomeTela="Observação")
	private String observacao;
	
	public Integer getIdEquipamento() {
		return idEquipamento;
	}

	public void setIdEquipamento(Integer idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	public Porta getPorta() {
		return porta;
	}

	public void setPorta(Porta porta) {
		this.porta = porta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
