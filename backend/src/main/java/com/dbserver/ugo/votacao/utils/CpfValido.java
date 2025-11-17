package com.dbserver.ugo.votacao.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfValidador.class) // Corrigido para CpfValidador
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfValido {
    String message() default "CPF deve conter 11 dígitos numéricos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}