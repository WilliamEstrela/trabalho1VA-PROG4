package conversores;

import interfaces.IConversor;

public class StringConversor extends AbstractConversor<String> implements IConversor<String> {

	@Override
	public String converter(String valor) {
		return valor;
	}

	@Override
	public String converterBdParaVisao(String valor) {
		if(valor==null) {
			return "";
		}else {
			return valor;
		}
	}

}
