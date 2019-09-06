package util;

import java.util.ArrayList;
import java.util.List;

public class Retorno {
	private boolean sucesso;
	private List<String> mensagens;
	private String acao;
	
	public Retorno(boolean sucesso, String mensagem){
		this.initRetorno();
		setSucesso(sucesso);
		addMensagem(mensagem);
		setAcao("execute");
		
	}
	public Retorno(boolean sucesso, String mensagem, String acao){
		this.initRetorno();
		setSucesso(sucesso);
		addMensagem(mensagem);
		setAcao(acao);
	}
	private void initRetorno() {
		this.mensagens = new ArrayList<>(); 
	}
	
	public boolean isSucesso() {
		return sucesso;
	}
	public String getMensagem() {
		if(mensagens.size()>0) {
			String retorno = "";
			for(String mens: this.getMensagens()) {
				retorno += ","+mens;
			}
			return retorno.substring(1);
		}else {
			return "";
		}
	}
	public List<String> getMensagens(){
		return this.mensagens;
	}
	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}
	public void addMensagem(String mensagem) {
		if(mensagem!=null && !mensagem.equals("")) {
			this.mensagens.add(mensagem);
		}
	}
	public void AddMensagens(List<String> mensagens) {
		if(mensagens!=null && mensagens.size()>0) {
			this.getMensagens().addAll(mensagens);
		}
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	@Override
	public String toString() {
		return "Retorno [sucesso=" + sucesso + ", mensagem=" + this.getMensagem() + ", acao=" + acao + "]";
	}

	public void copiar(Retorno ret) {
		this.setAcao(ret.getAcao());
		this.getMensagens().addAll(ret.getMensagens());
		this.setSucesso(ret.isSucesso());
	}
	
}
