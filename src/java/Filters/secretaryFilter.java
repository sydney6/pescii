package Filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The purpose of this filter is to prevent users who are not logged in
 * from accessing confidential website areas. 
 */
public class secretaryFilter implements Filter {

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("accountType") == null) {
            response.sendRedirect(request.getContextPath() + "/logIn.jsp");
        } 
        else if(!session.getAttribute("accountType").equals("Secretary")){
            if(session.getAttribute("accountType").equals("CEO")){
                chain.doFilter(request, response);
            }
            else{
                String message = "You do not have clearance to perform that function.";
                request.setAttribute("message", message);
                request.getRequestDispatcher("homePage.jsp").forward(request, response);
            } 
        }
        else {
            chain.doFilter(request, response);
        }
    }


    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {}

}