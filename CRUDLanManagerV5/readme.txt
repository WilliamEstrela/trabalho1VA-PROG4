* Cria��o da arquitetura inicial reutilizando a CRUDArquiteturaV1 adicionando Suporte a Generics
** Classe DAOGeneric modificado para utilizar Generics

Vers�o2
*Modifica��o na Arquitetura CRUDArquitetura classe Tabela
** Adi��o do metodo limparColunas na classe Tabela
** Adi��o do atributo "action" na classe Retorno, utilizado para informar qual dever� ser a pr�xima a��o 
** Adi��o da interface IController para generalizar um controlador que faz apenas o controle do fluxo de execu��o de um caso de uso


*Cria��o do Projeto CRUDArquiteturaVisaoSwing
** Refatora��o das classes: (adi��o de generic para Tabela e Tipo)
*** TabelaJTableModel<TABELA extends Tabela<TipoPK>,TipoPK>
*** JanelaCrudModelo<TABELA extends Tabela<TipoPK>,TipoPK>
** Refatora��o da classe JanelaCrudModelo para utilizar o novo mecanismo de acesso ao controlador
*** metodo para actionBuscar 
*** metodo public abstract List<Object> getFormularioValores(); foi alterado para	
**** public abstract HashMap<String,Object> getFormularioValores(); //pega os valores com nomes
*** metodo actionEditar()

