package modelo;

import java.util.ArrayList;
import java.util.List;

import anotacoes.Campo;
import conversores.StringConversor;
import interfaces.IConversor;
import util.Retorno;

public class Usuario extends Tabela<String> {

	@Campo(isPk=true,nome="nome_usuario",nomeTela="Nome",obrigatorio=true,usarPkNaInsercao=true)
	private String nome_usuario;
	
	@Campo(nome="senha_usuario",nomeTela="Senha",obrigatorio=true)
	private String senha_usuario;
	

	
	@Override
	public String getTabelaNome() {
		return "usuario";
	}

	@Override
	public String getTabelaPKNome() {
		return "nome_usuario";
	}

	@Override
	public List<Object> getCamposValor() {
		List<Object> camposValor = new ArrayList<>();
		camposValor.add(this.getPk());
		camposValor.add(this.getSenha_usuario());
		return camposValor;
	}

	@Override
	public List<IConversor> getCamposConversor() {
		List<IConversor> listConversor = new ArrayList<>();
		listConversor.add(new StringConversor());
		listConversor.add(new StringConversor());
		return listConversor;
	}

	@Override
	public List<String> getCamposNome() {
		List<String> nomeCampos = new ArrayList<>();
		nomeCampos.add("nome_usuario");
		nomeCampos.add("senha_usuario");
		return nomeCampos;
	}

	@Override
	protected List<String> getCamposObrigatorios() {
		return this.getCamposNome();
	}

	/*@Override
	protected Retorno setCamposValor(List<Object> list) {
		
		Retorno ret = new Retorno(true, null);
		try{
			this.setPk((String)list.get(0));
			this.setSenha_usuario((String)list.get(1)); 
 
		}catch(Exception e){
			ret.setSucesso(false);
			ret.addMensagem("Erro ao configura campos, ERROR:"+e.getMessage());
		}
				
		return ret;
	}*/

	@Override
	public Tabela<?> getNovoObjeto() {
		return new Usuario();
	}


	public String getNome_usuario() {
		return nome_usuario;
	}

	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}

	public String getSenha_usuario() {
		return senha_usuario;
	}

	public void setSenha_usuario(String senha_usuario) {
		this.senha_usuario = senha_usuario;
	}

}
