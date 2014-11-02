package br.com.ss.core.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.ss.core.web.validator.CepValidator;

@Constraint(validatedBy = CepValidator.class)
@Documented
@Target(java.lang.annotation.ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cep {
   
  String message() default "Cep inválido";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
 
}
