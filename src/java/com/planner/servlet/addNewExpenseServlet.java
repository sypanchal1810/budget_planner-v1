package com.planner.servlet;

import com.planner.dao.PlanExpenseDao;
import com.planner.entities.PlanExpense;
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
public class addNewExpenseServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Get current user id from session
            HttpSession s = request.getSession();
            User user = (User) s.getAttribute("CurrentUser");
            if (user == null) {
                response.sendRedirect("login.jsp");
            }
            int user_id = user.getId();

            int plan_id = Integer.parseInt(request.getParameter("plan_id"));

            String expense_title = request.getParameter("expense-title");
            java.sql.Date expense_date = new java.sql.Date((dateFormat.parse(request.getParameter("expense-date"))).getTime());
            int expense_amount = Integer.parseInt(request.getParameter("amount-spent"));

            // Create PlanExpense object and set all data to that
            PlanExpense expense = new PlanExpense(plan_id, user_id, expense_title, expense_date, expense_amount);

            // Create PlanExpenseDao Object to store the user
            PlanExpenseDao peDao = new PlanExpenseDao(ConnectionProvider.getConnection());

            if (peDao.saveExpense(expense)) {
                out.println("<script type='text/javascript'>");
                out.println("Swal.fire({ icon: 'success', title: 'Success', text: 'New Expense successfully added to your plan 🥳' }).then(result => {window.location.reload()});");
                out.println("</script>");
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

    // <editor-fold>
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(addNewExpenseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(addNewExpenseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
