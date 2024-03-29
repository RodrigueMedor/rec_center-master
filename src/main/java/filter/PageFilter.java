package filter;

import dao.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        filterName = "authenticationFilter",
        urlPatterns = "/*"
        //servletNames = {"LoginServlet"}
)
public class PageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession currentUserSession = ((HttpServletRequest) request).getSession();
        UserDAO userDAO= UserDAO.getInstance();
        String email=currentUserSession.getAttribute("email")==null?"":currentUserSession.getAttribute("email").toString();
         String currentRequest=((HttpServletRequest) request).getContextPath();
         String currentUrl=((HttpServletRequest) request).getRequestURI();
        HttpSession appSession = ((HttpServletRequest) request).getSession(false);
         boolean isHomeRequest= currentUrl.equals(currentRequest+"/") || currentUrl.equals(currentRequest+"/checkuser");
         boolean isLoginRequest=currentUrl.equals(currentRequest+"/login") || currentUrl.equals(currentRequest+"/login.jsp");
        boolean isSignUpRequest=currentUrl.equals(currentRequest+"/signup") || currentUrl.equals(currentRequest+"/signup.jsp");
        boolean isValidUser=email!="" && userDAO.getUsers().stream().filter(user -> user.getEmail().equals(email)).count()>0;
         boolean isStaticResource = currentUrl.startsWith("/resources/")|| currentUrl.startsWith("/assets/");
         if ( isHomeRequest || isLoginRequest || isSignUpRequest || isValidUser || isStaticResource)
            chain.doFilter(request, response);
        else
        {
            HttpServletResponse resp=(HttpServletResponse)response;
           // resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            ((HttpServletResponse) response).sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
}
