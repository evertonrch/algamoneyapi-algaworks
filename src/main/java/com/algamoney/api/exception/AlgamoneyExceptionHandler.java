package com.algamoney.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String msgUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String msgDesenvolvedor = ex.getCause().getMessage();

        return handleExceptionInternal(ex, new Error(msgUsuario, msgDesenvolvedor), headers, HttpStatus.BAD_REQUEST, request);
    }

    private static class Error {

        private String msg;
        private String msgDesenvolvedor;

        public Error(String msg, String msgDesenvolvedor) {
            this.msg = msg;
            this.msgDesenvolvedor = msgDesenvolvedor;
        }

        public String getMsg() {
            return msg;
        }

        public String getMsgDesenvolvedor() {
            return msgDesenvolvedor;
        }
    }
}
