package cn.tuyucheng.taketoday.app.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component("loggingFilter")
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("Request Info : " + req);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // cleanup code, if necessary
    }
}