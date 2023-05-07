package com.planner.servlet;

import com.planner.dao.Userdao;
import com.planner.entities.User;
import com.planner.helper.ConnectionProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

@MultipartConfig
public class signupServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            boolean state = true;

            String name = request.getParameter("name");

            // Email Validator
            String email = request.getParameter("email");
            String regex_email = "^[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
            Pattern pattern_email = Pattern.compile(regex_email);
            Matcher matcher_email = pattern_email.matcher(email);
            if (!matcher_email.matches()) {
                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'error', title: 'Alert', text: 'Please enter valid Email ID!' }).then(result => {window.location.reload()});");
                out.println("</script>");
                state = false;
            }

            // Password Validation and Encryption
            String password = request.getParameter("password");
            String passwordConfirm = request.getParameter("password-confirm");
            if (!password.equals(passwordConfirm)) {
                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'error', title: 'Alert', text: 'Please enter same password in Password and Password Confirm!' }).then(result => {window.location.reload()});");
                out.println("</script>");
                state = false;

            }
            password = BCrypt.hashpw(password, BCrypt.gensalt());

            // Phone Number Validator
            String phone = request.getParameter("phone");
            String regex_phone = "^[0-9]{10}$";
            Pattern pattern_phone = Pattern.compile(regex_phone);
            Matcher matcher_phone = pattern_phone.matcher(phone);
            if (phone.length() != 10 || !matcher_phone.matches()) {
                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'error', title: 'Alert', text: 'Please enter valid Contact Number!' }).then(result => {window.location.reload()});");
                out.println("</script>");
                state = false;
            }

            if (state) {
                // Create User object and set all data to that
                User user = new User(name, email, password, phone);

                // Create Userdao Object to store the user
                Userdao dao = new Userdao(ConnectionProvider.getConnection());
                if (!dao.isEmailExist(email)) {
                    if (dao.saveUser(user)) {
                        out.println("<script type='text/javascript'>");
                        out.println("Swal.fire({ icon: 'success', title: 'Success', text: 'You have successfully created your account 😀' }).then(result => {window.location = \"login.jsp\"});");
                        out.println("</script>");
                    } else {
                        out.println("<script type='text/javascript'>");
                        out.println("Swal.fire({ icon: 'error', title: 'Oops...', text: 'Something went wrong!! Try Again' }).then(result => {window.location.reload()});");
                        out.println("</script>");
                    }
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("Swal.fire({ icon: 'error', title: 'Alert', text: 'This Email is already exist. Please Login with this email or Sign Up with another Email!' }).then(result => {window.location.reload()});");
                    out.println("</script>");
                    state = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("Swal.fire({ icon: 'error', title: 'Oops...', text: 'Something went wrong!! Try Again' }).then(result => {window.location.reload()});");
            out.println("</script>");
        }
    }

    //<editor-fold>
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | BadPaddingException ex) {
            Logger.getLogger(signupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | BadPaddingException ex) {
            Logger.getLogger(signupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
