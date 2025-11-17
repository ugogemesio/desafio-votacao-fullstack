package com.dbserver.ugo.votacao.utils;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidador implements ConstraintValidator<CpfValido, String> { // Corrigido para CpfValido

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null) return false;
        String apenasNumeros = cpf.replaceAll("\\D", "");
        return apenasNumeros.length() == 11;
    }
}