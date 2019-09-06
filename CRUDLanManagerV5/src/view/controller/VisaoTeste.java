package view.controller;

import java.util.HashMap;
import java.util.List;

import biz.source_code.miniTemplator.MiniTemplator;
import interfaces.IControleNegocio;
import interfaces.IReferenciaVisaoAcao;
import util.VisaoEntryPoint;

public class VisaoTeste extends VisaoCrudServletControleGenerica {

	@Override
	public HashMap<String, IReferenciaVisaoAcao> getVisaoAcaoMetodos() {
		HashMap<String, IReferenciaVisaoAcao> acaoMetodos = new HashMap<>();
		  acaoMetodos.put("teste",this::processaTeste);//para ações que tem processamento na camada de controle(negocio)		  
		  return acaoMetodos;
		 
	}
	public void processaTeste(MiniTemplator t,
    			IControleNegocio controleNegocio) {
		String teste = (String)controleNegocio.getVariaveis().get("teste");
			t.setVariable("teste", teste,true);
	}
	@Override
	public List<VisaoEntryPoint> getEntryPoints() {
		return null;
	}

}
