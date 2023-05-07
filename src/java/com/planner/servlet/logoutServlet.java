package com.planner.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class logoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession s = request.getSession();
            s.removeAttribute("CurrentUser");
            try {
                // Pause for 1.5 seconds
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();

                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'error', title: 'Oops...', text: 'Something went wrong!! Try Again' }).then(result => {window.location.reload()});");
                out.println("</script>");
            }
            response.sendRedirect("index.jsp");
        }
    }

    // <editor-fold>
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    } // </editor-fold>
}
