package interfaces;

import java.util.List;

import modelo.Tabela;
import util.Retorno;

public interface IDAO {
	public <T extends Tabela<?>> List<T> listar(T tab);
	public <T extends Tabela<?>> List<T>  procurar(T tab);
	public Retorno incluir(Tabela<?> tab);
	public Retorno alterar(Tabela<?> tab);
	public Retorno remover(Tabela<?> objPk);
	public <T extends Tabela<?>> T getObjeto(T objPk);
	//novos metodos ProgIII
	public boolean existir(Tabela<?> objPk);
	
}
