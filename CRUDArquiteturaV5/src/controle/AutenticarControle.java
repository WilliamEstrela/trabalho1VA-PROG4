package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interfaces.IReferenciaAcaoNegocio;
import modelo.Usuario;
import persistencia.DAOGeneric;
import util.NegocioEntryPoint;
import util.Retorno;

public class AutenticarControle extends CrudControle<Usuario, String> {
	
	public static final String ACAO_FORM_LOGIN = "formularioLogin";
	public static final String ACAO_LOGOUT = "logout";
	public static final String ACAO_LOGIN = "login";
	public static final String ENTRYPOINT_VALIDAR_LOGIN = "_AutenticaControleValidarLogin";

	@Override
	public String getCasoDeUso() {
		return "autenticar";
	}

	protected Retorno acaoLogin(String acao) {
		
		Retorno retEntryPointAntes = this.executarEntryPoint(ENTRYPOINT_VALIDAR_LOGIN, ACAO_LOGIN);
		if(!retEntryPointAntes.isSucesso()) {
			return retEntryPointAntes;
		}
		
		String nomeUsuario = (String) this.getVariaveis().get("nome_usuario");
		String senhaUsuario = (String) this.getVariaveis().get("senha_usuario");
		Usuario usuario = new Usuario();
		usuario.setPk(nomeUsuario);
		usuario = DAOGeneric.getIntancia().getObjeto(usuario);
		Retorno retAcao = new Retorno(true, null,acao);
		if(usuario==null || !usuario.getSenha_usuario().equals(senhaUsuario)) {
			retAcao.setAcao(ACAO_FORM_LOGIN);
			retAcao.setSucesso(false);
			retAcao.addMensagem("Usuário não existe, ou a senha está errada");;
			return retAcao;
		}else {
			this.getControleSessao().setVariavel("logged", "true");
			retAcao.setAcao(ACAO_PADRAO);
			retAcao.setSucesso(true);
			retAcao.addMensagem("Usuário autenticado com sucesso");;
			return retAcao;
		}
	}
	
	protected Retorno acaoLogOut(String acao) {
		Retorno retAcao = new Retorno(true, null,acao);
		
		this.getControleSessao().setVariavel("logged", "false");
		retAcao.addMensagem("Usuário deslogado");
		retAcao.setAcao(ACAO_FORM_LOGIN);
		retAcao.setSucesso(true);
		return retAcao;
	}
	
	@Override
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodos() {
		HashMap<String, IReferenciaAcaoNegocio> acaoMetodos = new HashMap<>();
		  acaoMetodos.put(ACAO_LOGIN,this::acaoLogin);
		  acaoMetodos.put(ACAO_LOGOUT,this::acaoLogOut);
		  acaoMetodos.put(ACAO_FORM_LOGIN,null);
		  return acaoMetodos;

	}

	protected Retorno entryPointValidarLogin(String acao) {
		Retorno ret = new Retorno(true, null,acao);
		String nomeUsuario = (String) this.getVariaveis().get("nome_usuario");
		nomeUsuario= nomeUsuario!=null?nomeUsuario:"";
		String senhaUsuario = (String) this.getVariaveis().get("senha_usuario");
		senhaUsuario = senhaUsuario!=null?senhaUsuario:"";
		if(nomeUsuario.trim().equals("")|| senhaUsuario.trim().equals("")) {
			ret.setSucesso(false);
			ret.setAcao(ACAO_FORM_LOGIN);
			ret.addMensagem("Nome de Usuário e Senhas são obrigatórios");
		}
		
		return ret;		
	}
	@Override
	public List<NegocioEntryPoint> getEntryPoints() {
		List<NegocioEntryPoint> list = new ArrayList<>();
		list.add(new NegocioEntryPoint(ENTRYPOINT_VALIDAR_LOGIN, this::entryPointValidarLogin));
		return list;

	}

	@Override
	public Usuario getNovoObjeto() {
		return new Usuario();
	}

	@Override
	public boolean mostrarPaginaComum() {
		return false;
	}

}
