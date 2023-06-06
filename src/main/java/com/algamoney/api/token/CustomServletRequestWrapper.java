package com.algamoney.api.token;

import org.apache.catalina.util.ParameterMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

public class CustomServletRequestWrapper extends HttpServletRequestWrapper {

    private final String refreshToken;

    public CustomServletRequestWrapper(HttpServletRequest request, String refreshToken) {
        super(request);
        this.refreshToken = refreshToken;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        ParameterMap<String, String[]> parameterMap = new ParameterMap<>(getRequest().getParameterMap());
        parameterMap.put("refresh_token", new String[] {this.refreshToken});

        // Mapa não modificável
        parameterMap.setLocked(true);
        return parameterMap;
    }
}
