package view.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controle.ManterSwitch;
import controle.ManterTipo;
import controle.ManterVlan;
import interfaces.IControleNegocio;
import interfaces.IVisaoControle;

@SuppressWarnings("serial")
public class ViewServlet extends ServletControle {

	@Override
	public HashMap<String, IVisaoControle> configurarControladoresDeVisao() {
		HashMap<String,IVisaoControle> conf = new HashMap<>(); 
		conf.put("sobre", new VisaoServletControle());//controlador de caso de uso generico para paginas sem controaldor 
		conf.put("manterSwitch", new VisaoManterSwitch());
		conf.put("manterTipo", new VisaoCrudServletControle());
		conf.put("manterVlan", new VisaoCrudServletControle());
		conf.put("manterReprovacao", new VisaoCrudServletControle());
		conf.put("manterVlan", new VisaoTeste());
		return conf;
	}
	

}
