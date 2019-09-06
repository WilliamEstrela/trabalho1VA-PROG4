* Criação da arquitetura inicial reutilizando a CRUDArquiteturaV1 adicionando Suporte a Generics
** Classe DAOGeneric modificado para utilizar Generics

Versão2
*Modificação na Arquitetura CRUDArquitetura classe Tabela
** Adição do metodo limparColunas na classe Tabela
** Adição do atributo "action" na classe Retorno, utilizado para informar qual deverá ser a próxima ação 
** Adição da interface IController para generalizar um controlador que faz apenas o controle do fluxo de execução de um caso de uso


*Criação do Projeto CRUDArquiteturaVisaoSwing
** Refatoração das classes: (adição de generic para Tabela e Tipo)
*** TabelaJTableModel<TABELA extends Tabela<TipoPK>,TipoPK>
*** JanelaCrudModelo<TABELA extends Tabela<TipoPK>,TipoPK>
** Refatoração da classe JanelaCrudModelo para utilizar o novo mecanismo de acesso ao controlador
*** metodo para actionBuscar 
*** metodo public abstract List<Object> getFormularioValores(); foi alterado para	
**** public abstract HashMap<String,Object> getFormularioValores(); //pega os valores com nomes
*** metodo actionEditar()

