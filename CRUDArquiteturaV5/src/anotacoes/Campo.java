package anotacoes;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import conversores.StringConversor;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Campo {
	boolean isPk() default false;
	boolean usarPkNaInsercao() default false;
	String nome();
	String nomeTela();
	boolean obrigatorio() default false;
}
