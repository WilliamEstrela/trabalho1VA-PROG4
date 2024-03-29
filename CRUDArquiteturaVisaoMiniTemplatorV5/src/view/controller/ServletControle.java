package view.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.source_code.miniTemplator.MiniTemplator;
import controle.AutenticarControle;
import controle.PaginaControle;
import interfaces.IControleNegocio;
import interfaces.IVisaoControle;
import persistencia.DAOGeneric;
import util.ClassFinder;
import util.ControleSessao;
import util.PaginaUtilitario;
import util.Retorno;
import util.Visitor;

@SuppressWarnings("serial")
public abstract class ServletControle extends HttpServlet {

	private static final String PAGINA_COMUM_NOME_VARIAVEL = "paginaComum";
	private static final String TEMPLATE_PAGINA_COMUM = "page.html";
	ServletContext servletContext;
	String separador;
	String realPath;
	String contextPath;

	@Override
	public void init() {
		servletContext = getServletContext();
		separador = System.getProperty("file.separator");
		realPath = servletContext.getRealPath("/");
		contextPath = servletContext.getContextPath();
		DAOGeneric.getIntancia();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processa(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processa(req, resp);
	}

	private String getFullFileName(String casoDeUso, String acao) {
		return PaginaUtilitario.getCaminhoPaginaCasoDeUso(realPath, casoDeUso, acao);
	}

	protected List<IControleNegocio> initListaControladores() {
		List<IControleNegocio> listaControladores = new ArrayList<>();
		//listaControladores.add(new PaginaControle());
		//listaControladores.add(new AutenticarControle());
		listaControladores.addAll(this.configurarControladores());
	
		return listaControladores;
	}

	protected HashMap<String, IVisaoControle> initListaVisaoControladores() {
		HashMap<String, IVisaoControle> listaVisaoControladores = new HashMap<>();
		// caso de uso pagina (paginas genericas sem controlador de negoco)
		listaVisaoControladores.put("pagina", new VisaoServletControle());
		listaVisaoControladores.put("autenticar", new VisaoServletControle());
		listaVisaoControladores.putAll(this.configurarControladoresDeVisao());
		return listaVisaoControladores;
	}

	/**
	 * Metodo respons�vel por configurar os Controladores de Visao para os
	 * controladores de negocio deve retornar um
	 * HashMap<casoDeUso,ControladorDeVisao>
	 * 
	 * <pre>
	 * Ex.:
	 *	HashMap<String, IVisaoControle> conf = new HashMap<>();
	 *	conf.put("nomeCasoDeUso", new VisaoServletControle());//controlador de caso de uso generico para paginas sem controaldor
	 *	conf.put("crud", new VisaoCrudServletControle());//controlador de caso de uso de Crud
	 *	return conf;
	 * </pre>
	 * 
	 * @return
	 */
	public abstract HashMap<String, IVisaoControle> configurarControladoresDeVisao();

	private IControleNegocio processClass(String nome) {
		Class<?> cl=null;
		System.out.println(nome);
		if(!nome.contains("controle")) { return null ; }
		try {
			cl = Class.forName(nome);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		if(cl!=null) {
			//TODO: colocar o pacote em configura��es
			if(cl.getPackage().getName().contains("controle")) {
				if(IControleNegocio.class.isAssignableFrom(cl) && !Modifier.isAbstract( cl.getModifiers() )) {
					try {
						System.out.println(cl.getName());
						 return ((IControleNegocio) cl.newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
				System.out.println(cl.getSimpleName());
			}
		}
		
		return null;
	}
	
	/**
	 * Metodo respons�vel por configurar os Controladores de Negocio dispon�veis
	 * deve retornar um HashMap<casoDeUso,ControladorDeVisao>
	 * 
	 * <pre>
	 * Ex.:
	 *	List<IControle> conf = new ArrayList<>();
	 *	conf.add( new PaginaControle());//controlador de caso de uso generico para paginas sem controaldor
	 *	conf.put( new SeuControlador());//crie o seu controlador Herdando de Controle ou CrudControle
	 *	return conf;
	 * </pre>
	 * 
	 * @return
	 */
	public List<IControleNegocio> configurarControladores(){
		//https://stackoverflow.com/questions/2548384/java-get-a-list-of-all-classes-loaded-in-the-jvm
		ClassLoader c=getClass().getClassLoader();
		URLClassLoader u=(URLClassLoader)c;
		List<IControleNegocio> listaControladores = new ArrayList<>();
		ClassFinder.findClasses(new Visitor<String>() {
			
			@Override
			public boolean visit(String t) {
				IControleNegocio cont = processClass(t);
				if(cont!=null) {
					listaControladores.add(cont);
				}
				return true;
			}
		},u);
		return listaControladores;
		
		//ClassLoader cl = getClass().getClassLoader();
	    //Set<ClassPath.ClassInfo> classesInPackage = ClassPath.from(cl).getTopLevelClassesRecursive("com.mycompany.mypackage");
		
		//https://github.com/google/guava
		
		
		/*List<IControleNegocio> listaControladores = new ArrayList<>();
		try {
			Class<?>[] controles = ReflexaoListarPacote.getClasses("controle");
			for(int i=0;i<controles.length;i++) {
				listaControladores.add((IControleNegocio) controles[i].newInstance());	
			}
			
		} catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}	
		return listaControladores;*/
	}

	private IControleNegocio getControladorNegocio(String useCase) {
		List<IControleNegocio> controllerList = this.initListaControladores();
		for (IControleNegocio iController : controllerList) {
			if (iController.getCasoDeUso().equalsIgnoreCase(useCase)) {
				return iController;
			}
		}
		;
		PaginaControle controlador = (PaginaControle) this.getControladorNegocio("pagina");
		controlador.setCasoDeUso(useCase);
		return controlador;
	}

	private IVisaoControle getVisaoControlador(String casoDeUso) {
		HashMap<String, IVisaoControle> controllerList = this.initListaVisaoControladores();
		for (String casoDeUsoVisaoNome : controllerList.keySet()) {
			if (casoDeUsoVisaoNome.equalsIgnoreCase(casoDeUso)) {
				return controllerList.get(casoDeUsoVisaoNome);
			}
		}
		return null;
	}

	private HashMap<String, Object> initListaVariaveis(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();

		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String pName = parameterNames.nextElement();
			result.put(pName, request.getParameter(pName));
		}

		return result;
	}

	public void processa(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		String htmlResult = "";

		String casoDeUso = getCasoDeUsoFromRequest(request);

		String acao = getAcaoFromRequest(request);

		boolean exibirPaginaComum = false;

		IControleNegocio controladorNegocio = this.getControladorNegocio(casoDeUso);
		if (controladorNegocio != null) {
			ControleSessao controleSessao = new ControleSessao(request.getSession(true));			
			controladorNegocio.setControleSessao(controleSessao);

			// verifica��o de autentica��o
			if (controladorNegocio.precisaAutenticar()) {
				String attribute = (String) controladorNegocio.getControleSessao().getVariavel("logged");
				attribute = attribute != null ? attribute : "false";
				if (attribute.equalsIgnoreCase("false")) {
					controladorNegocio = this.getControladorNegocio("autenticar");
					controladorNegocio.setControleSessao(controleSessao);
					casoDeUso = "autenticar";
					acao = AutenticarControle.ACAO_FORM_LOGIN;
				}

			}

			controladorNegocio.configurarCaminhoAplicacao(realPath);
			exibirPaginaComum = getExibirPaginaComum(request, controladorNegocio.mostrarPaginaComum());

			if (controladorNegocio.acaoExiste(acao)) {

				// prepara e executar a a��o de neg�cio
				controladorNegocio.setVariaveis(initListaVariaveis(request));
				Retorno retornoControlador = controladorNegocio.executar(acao);

				// preparar e executar a a��o de vis�o
				IVisaoControle visaoControle = this.getVisaoControlador(casoDeUso);
				MiniTemplator t = visaoControle.executarAcao(request, controladorNegocio, retornoControlador.getAcao(),retornoControlador);
				mesclarVariaveisImplicitas(t);
				htmlResult = t.generateOutput();

			} else {
				htmlResult = getErrorOutput(
						"A��o(" + acao + ") n�o existe no controller:" + controladorNegocio.getClass().getName());
			}

		}

		out.println(getHtmlPage(htmlResult, exibirPaginaComum));
	}

	private boolean getExibirPaginaComum(HttpServletRequest request, boolean paginaComumConfControlador) {
		String paginaComum = request.getParameter(PAGINA_COMUM_NOME_VARIAVEL);
		if (paginaComum == null) {
			return paginaComumConfControlador;
		}
		boolean exibirPaginaComum = true;
		if (paginaComum != null && (paginaComum.equalsIgnoreCase("n�o") || paginaComum.equalsIgnoreCase("nao")
				|| paginaComum.equalsIgnoreCase("no"))) {
			exibirPaginaComum = false;
		}

		return exibirPaginaComum;
	}

	private void mesclarVariaveisImplicitas(MiniTemplator t) {
		t.setVariable("contextPath", contextPath, true);
	}

	private String getHtmlPage(String htmlResult, boolean exibirPaginaComum) {
		String result = htmlResult;
		if (exibirPaginaComum) {
			String file = realPath + TEMPLATE_PAGINA_COMUM;
			MiniTemplator t = PaginaUtilitario.initTemplator(file);

			mesclarVariaveisImplicitas(t);

			t.setVariable("conteudo", htmlResult);

			result = t.generateOutput();
		}
		return result;
	}

	private String getErrorOutput(String errorMessage) {
		MiniTemplator t = getErrorTemplate();
		t.setVariable("error_message", errorMessage, true);
		return t.generateOutput();
	}

	private String getAcaoFromRequest(HttpServletRequest request) {
		String action = request.getParameter("acao");
		action = (action != null) && !action.equals("") ? action : "index";
		return action;
	}

	/**
	 * obtem o nome do caso de uso a partir da requisi��o
	 * 
	 * @param request
	 *            nome do caso de uso.
	 * @return
	 */
	private String getCasoDeUsoFromRequest(HttpServletRequest request) {
		String useCase = request.getParameter("casoDeUso");
		useCase = (useCase != null) && (!useCase.equals("")) ? useCase : "pagina";
		return useCase;
	}

	private MiniTemplator getErrorTemplate() {
		String file;
		file = getFullFileName("geral", "error");
		MiniTemplator t = PaginaUtilitario.initTemplator(file);
		return t;
	}
}
