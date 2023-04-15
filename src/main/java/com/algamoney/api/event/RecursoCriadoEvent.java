package com.algamoney.api.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

public class RecursoCriadoEvent extends ApplicationEvent {

    private final HttpServletResponse response;
    private final Long id;

    public RecursoCriadoEvent(Object source, HttpServletResponse response,  Long id) {
        super(source);
        this.response = response;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
