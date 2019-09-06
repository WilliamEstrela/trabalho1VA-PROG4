package controle;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import interfaces.IReferenciaAcaoNegocio;
import util.NegocioEntryPoint;
import util.PaginaUtilitario;

public class PaginaControle extends Controle {
	private String casoDeUsoLocal;

	public void setCasoDeUso(String casoDeUso) {
		this.casoDeUsoLocal = casoDeUso;
		
	}
	@Override
	public String getCasoDeUso() {
		if(this.casoDeUsoLocal==null) {
			this.casoDeUsoLocal="pagina";
		}
		return this.casoDeUsoLocal;
	}
	
	
	//foi necessário sobrescrever para iniciar as ações, pois quando o controlador é criado não foi definido o caminho da aplicação
	@Override
	public void configurarCaminhoAplicacao(String caminho) {
		
		super.configurarCaminhoAplicacao(caminho);
		this.initAcoesValidas();
	}
	@Override
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodos() {
		
			String diretorio = PaginaUtilitario.getCaminhoDiretorioCasoDeUso(this.getCaminhoAplicacao(),this.getCasoDeUso());
		
		File file = new File(diretorio);
		if(!file.exists()) {
			return null;
		}
		
		HashMap<String, IReferenciaAcaoNegocio> acaoMetodos = new HashMap<>();
		File afile[] = file.listFiles();
		int i = 0;
		for (int j = afile.length; i < j; i++) {
			File arquivos = afile[i];
			String arquivo = arquivos.getName().replaceAll(".html", "");
			acaoMetodos.put(arquivo,null);
			//System.out.println(arquivo);
		}
		
		return acaoMetodos;
	}
	@Override
	public List<NegocioEntryPoint> getEntryPoints() {
		return null;
	}
}
