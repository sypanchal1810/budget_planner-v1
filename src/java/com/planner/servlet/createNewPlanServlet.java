package com.planner.servlet;

import com.planner.dao.PlanDetailsDao;
import com.planner.entities.PlanDetails;
import com.planner.entities.User;
import com.planner.helper.ConnectionProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig
public class createNewPlanServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            boolean state = true;

            // Get current user id from session
            HttpSession s = request.getSession();
            User user = (User) s.getAttribute("CurrentUser");
            if (user == null) {
                response.sendRedirect("login.jsp");
            }
            int user_id = user.getId();

            int initial_budget = Integer.parseInt(request.getParameter("initial-budget"));
            int total_person = Integer.parseInt(request.getParameter("total-person"));
            String trip_title = request.getParameter("trip-title");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date from_date = new java.sql.Date((dateFormat.parse(request.getParameter("from-date"))).getTime());
            java.sql.Date to_date = new java.sql.Date((dateFormat.parse(request.getParameter("to-date"))).getTime());

            // Compare the dates using the compareTo() method
            int result = from_date.compareTo(to_date);
            if (result >= 0) {
                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'error', title: 'Alert', text: 'Starting Date must be earlier than Ending Date' }).then(result => {window.location.reload()});");
                out.println("</script>");
                state = false;
            }
//
            // Create PlanDetails object and set all data to that
            PlanDetails plan = new PlanDetails(user_id, initial_budget, total_person, trip_title, from_date, to_date);

            // Create PlanDetailsDao Object to store the user
            PlanDetailsDao planDao = new PlanDetailsDao(ConnectionProvider.getConnection());

            if (state) {
                if (planDao.savePlan(plan)) {
                    out.println("<script type='text/javascript'>");
                    out.println("Swal.fire({ icon: 'success', title: 'Success', text: 'You have successfully created your new plan 😀 Enjoy your trip 🎊🥳' }).then(result => {window.location = \"home.jsp\"});");
                    out.println("</script>");
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("Swal.fire({ icon: 'error', title: 'Oops...', text: 'Something went wrong!! Try Again' }).then(result => {window.location.reload()});");
                    out.println("</script>");
                }
            } else {
                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'error', title: 'Oops...', text: 'Something went wrong!! Try Again' }).then(result => {window.location.reload()});");
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(createNewPlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(createNewPlanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
