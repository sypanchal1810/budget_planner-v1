package com.planner.servlet;

import com.planner.dao.Userdao;
import com.planner.entities.User;
import com.planner.helper.ConnectionProvider;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig
public class loginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            Userdao dao = new Userdao(ConnectionProvider.getConnection());

            if (!dao.isEmailExist(email)) {
                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'error', title: 'Alert', text: 'This Email is not registered. First create new account and then Login with this email' }).then(result => {window.location.reload()});");
                out.println("</script>");
            }

            User user = dao.getUserByEmailAndPassword(email, password);

            if (user == null) {
                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'error', title: 'Alert', text: 'Invalid Email or Password!' }).then(result => {window.location.reload()});");
                out.println("</script>");
            } else {
                HttpSession ssn = request.getSession();
                ssn.setAttribute("CurrentUser", user);

                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'success', title: 'Success', text: 'Logging you in...!', timer: 1500,timerProgressBar: true, didOpen: () => {Swal.showLoading()}}).then(result => {window.location = \"home.jsp\"});");
                out.println("</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();

            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("Swal.fire({ icon: 'error', title: 'Oops...', text: 'Something went wrong!! Try Again' }).then(result => {window.location.reload()});");
            out.println("</script>");
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
