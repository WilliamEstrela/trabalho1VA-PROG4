package controle;

import java.util.HashMap;
import java.util.List;

import interfaces.IReferenciaAcaoNegocio;
import modelo.TabelaTeste;
import util.NegocioEntryPoint;

public class TesteControle extends CrudControle<TabelaTeste,Integer> {

	@Override
	public TabelaTeste getNovoObjeto() {
		return new TabelaTeste();
	}

	@Override
	public String getCasoDeUso() {
		return "ManterFake";
	}

	@Override
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodos() {
		return null;
	}

	@Override
	public List<NegocioEntryPoint> getEntryPoints() {
		return null;
	}

}
