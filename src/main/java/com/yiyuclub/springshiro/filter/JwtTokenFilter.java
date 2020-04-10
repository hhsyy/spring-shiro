package com.yiyuclub.springshiro.filter;

import cn.hutool.json.JSONObject;
import com.yiyuclub.springshiro.jwt.JJwtUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

public class JwtTokenFilter extends FormAuthenticationFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest r = (HttpServletRequest) request;
        String url = r.getServletPath();
        System.out.println(url);

        if (!url.equals("/login")) {
            String token = r.getHeader("token");
            if (StringUtils.isEmpty("token") && !JJwtUtils.checkToken(token)) {
                return false;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.CONFLICT.value());
        JSONObject json = new JSONObject();
        json.put("message", "没有权限访问");
        Writer writer = httpServletResponse.getWriter();
        writer.write(json.toString());
        writer.flush();
        writer.close();
        return false;
    }


}
