package interfaces;

import java.util.List;

import modelo.Tabela;
import util.Retorno;

public interface ICrudControleNegocio<TABELA extends Tabela<TipoPK>, TipoPK> extends IControleNegocio{
	public List<TABELA> listar();
	public List<TABELA> procurar(TABELA tab);
	public List<TABELA> procurar();
	public Retorno incluir(TABELA tab);
	public Retorno incluir();
	public Retorno alterar(TABELA tab);
	public Retorno alterar();
	public Retorno remover(TABELA objPk);
	public Retorno remover();
	
	public TABELA editar(TABELA objPk);
	public TABELA editar();
	
	public TABELA getNovoObjeto();
	public TABELA getObjetoPreenchido();
	
	public <T extends Tabela<?>> T getDadosExtrangeiro(T tab);
	public <T extends Tabela<?>> List<T> procurarTodos(T tab);
	public List<Object> getTabelaCamposValoresDasVariaveis(Tabela<?> tabela);
}
