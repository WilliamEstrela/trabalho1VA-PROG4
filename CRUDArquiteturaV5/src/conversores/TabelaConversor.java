package conversores;

import interfaces.IConversor;
import interfaces.ITabelaConversor;
import modelo.Tabela;
@SuppressWarnings("rawtypes")
public class TabelaConversor extends AbstractConversor<Tabela> implements ITabelaConversor<Tabela> {
	private Tabela<?> tabela;

	public Tabela getTabela() {
		try {
			tabela = (Tabela<?>) this.getFieldData().getType().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return this.tabela;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public Tabela converter(String valor) {
		if(valor==null) return null;
		Tabela t = this.getTabela().getNovoObjeto();
		if(valor.equals("")) return t;
			
		int indicePk = this.getTabela().getColunaPK();
		IConversor conv = (IConversor) this.getTabela().getCamposConversorUtil().get(indicePk);
		Object pkValor = conv.converter(valor);
		t.setPk(pkValor);
		t = t.getDadosExtrangeiro(t);	
		return t;
	}

	
	@Override
	public String converterBdParaVisao(Tabela valor) {
		if(valor==null) {
			return "";
		}
		return valor.toString();
	}

}
