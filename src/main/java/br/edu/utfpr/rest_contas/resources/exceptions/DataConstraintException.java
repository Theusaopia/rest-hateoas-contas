package br.edu.utfpr.rest_contas.resources.exceptions;

public class DataConstraintException extends RuntimeException{
    public DataConstraintException(String mensagem) {
        super(mensagem);
    }
}
