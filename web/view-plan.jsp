<%@page import="com.planner.dao.PlanExpenseDao"%>
<%@page import="com.planner.entities.PlanExpense"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.planner.dao.PlanDetailsDao"%>
<%@page import="com.planner.helper.ConnectionProvider"%>
<%@page import="com.planner.entities.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.planner.entities.PlanDetails"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    User user = (User) session.getAttribute("CurrentUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
    }

    int plan_id = Integer.parseInt(request.getParameter("plan_id").trim());

    String[] suffixes
            = //    0     1     2     3     4     5     6     7     8     9
            {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                //    10    11    12    13    14    15    16    17    18    19
                "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                //    20    21    22    23    24    25    26    27    28    29
                "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                //    30    31
                "th", "st"};

    PlanExpenseDao peDao = new PlanExpenseDao(ConnectionProvider.getConnection());
    ArrayList<PlanExpense> expenseList = peDao.getAllExpense(plan_id, user.getId());
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home | Control Budget</title>

    <!--Bootstrap CSS-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">

    <!--Font awesome-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <!--Sweetalert-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.5/dist/sweetalert2.min.css">

    <!--Custom CSS-->
    <link href="./css/index.css" rel="stylesheet" type="text/css" />
  </head>
  <body>

    <div id="display-message"></div>
    <div class="page-body">
      <%@include file = "include/header.jsp" %>
      <div class="home-page view-plan-page">
        <div class="container-fluid home-panel view-plan-panel">
          <div class="container">
            <%
                PlanDetailsDao planDao = new PlanDetailsDao(ConnectionProvider.getConnection());
                PlanDetails p = planDao.getPlan(user.getId(), plan_id);
            %>

            <div class="row row-detail">
              <div class="col-lg-8 col-md-6 col-sm-8 plans-row">
                <div class="panel">
                  <div class="panel-heading">
                    <h3><%= p.getTrip_title()%></h3>
                  </div>

                  <div class="panel-body">
                    <div class="budget-plan">
                      <b class="label">Budget</b>
                      <span class="value"> ₹ <%= p.getInitial_budget()%> </span>
                    </div>

                    <div class="budget-plan">
                      <%
                          int total_expense = 0;
                          if (!expenseList.isEmpty()) {
                              for (PlanExpense pe : expenseList) {
                                  total_expense += pe.getExpense_amount();
                              }
                          }
                          int remaining_amount = p.getInitial_budget() - total_expense;
                      %>
                      <b class="label">Remaining Budget</b>
                      <%
                          if (remaining_amount < 0) {
                      %>
                      <span class="value" style="color: red; font-weight: 600;"> Overspent By ₹ <%= Math.abs(remaining_amount)%> </span>
                      <%
                      } else {
                      %>
                      <span class="value"style="color: green; font-weight: 600;"> ₹ <%= remaining_amount%> </span>
                      <%
                          }
                      %>
                    </div>

                    <div class="budget-plan">
                      <b class="label">Total Person</b>
                      <span class="value"> <%= p.getTotal_person()%> Person </span>
                    </div>

                    <div class="budget-plan">
                      <b class="label">Trip Date</b>
                      <span class="value">
                        <%= new SimpleDateFormat("d").format(p.getFrom_date()) + suffixes[Integer.parseInt(new SimpleDateFormat("d").format(p.getFrom_date()))] + new SimpleDateFormat(" MMM").format(p.getFrom_date())%>
                        - <%= new SimpleDateFormat("d").format(p.getTo_date()) + suffixes[Integer.parseInt(new SimpleDateFormat("d").format(p.getTo_date()))] + new SimpleDateFormat(" MMM YYYY").format(p.getTo_date())%>
                      </span>
                    </div>
                  </div>
                </div>
              </div>

              <div class="panel col-lg-3 offset-lg-1 col-md-6 col-sm-6">
                <div class="panel-heading"> Add New Expense </div>

                <form id="add-expense-form" action="addNewExpenseServlet?plan_id=<%= plan_id%>" Method="POST" enctype="multipart/form-data">

                  <div class="form-group">
                    <label for="expense-title">Title: </label>
                    <input type="text" class="form-control" placeholder="Expense Name (Ex: Food Bill)" name="expense-title" required>
                  </div>

                  <div class="form-group">
                    <label for="expense-date">Date: </label>
                    <input type="date" class="form-control" placeholder="dd-mm-yyyy" min="<%= p.getFrom_date()%>" max="<%= p.getTo_date()%>" name="expense-date" required>
                  </div>

                  <div class="form-group">
                    <label for="amount-spent">Amount Spent: </label>
                    <input type="number" class="form-control" placeholder="Enter the amount(Ex: 500)" name="amount-spent" required>
                  </div>

                  <div class="form-group">
                    <button type="submit" name="submit" class="btn btn-center add-btn form-control">Add Expense</button>
                  </div>

                  <div class="form-group">
                    <div class='submit-loader' style="text-align: center; display: none">
                      <i class="fa fa-refresh fa-spin fa-3x"></i>
                    </div>
                  </div>

                </form>

              </div>
            </div>

            <%
                if (!expenseList.isEmpty()) {
            %>
            <div class="row expense-row">
              <div class="col-lg-8">

                <div class="row">
                  <div class="col-lg-10 offset-lg-1">
                    <div class="row">

                      <% for (PlanExpense pe : expenseList) {%>

                      <div class="col-lg-6" style="margin-bottom: 3rem">
                        <div class="panel">
                          <div class="panel-heading">
                            <h3><%= pe.getExpense_title()%></h3>
                          </div>

                          <div class="panel-body">
                            <div class="budget-plan">
                              <b class="label">Amount</b>
                              <span class="value"> ₹ <%= pe.getExpense_amount()%> </span>
                            </div>

                            <div class="budget-plan">
                              <b class="label">Paid On</b>
                              <span class="value"><%= new SimpleDateFormat("d").format(pe.getExpense_date()) + suffixes[Integer.parseInt(new SimpleDateFormat("d").format(pe.getExpense_date()))] + new SimpleDateFormat(" MMM YYYY").format(pe.getExpense_date())%>
                              </span>
                            </div>

                            <div class="budget-plan">
                              <b class="label">Expense Added On</b>
                              <span class="value"><%= new SimpleDateFormat("d").format(pe.getExpense_added_on()) + suffixes[Integer.parseInt(new SimpleDateFormat("d").format(pe.getExpense_added_on()))] + new SimpleDateFormat(" MMM YYYY").format(pe.getExpense_added_on())%>
                              </span>
                            </div>

                          </div>
                        </div>
                      </div>
                      <%
                          }
                      %>
                    </div>
                  </div>
                </div>

              </div>
            </div>
            <%
                }
            %>
          </div>

        </div>
      </div>
      <%@include file="include/footer.jsp" %>
    </div>

    <!-- JavaScript -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>

    <!--Sweet Alert-->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.5/dist/sweetalert2.all.min.js"></script>

    <script>
        $(document).ready(function () {
            console.log("Loaded...");

            $("#add-expense-form").on('submit', function (e) {
                e.preventDefault();
                $('.add-btn').hide();
                $('.submit-loader').show();

                let form = new FormData(this);

                $.ajax({
                    url: "addNewExpenseServlet?plan_id=<%= plan_id%>",
                    type: "POST",
                    data: form,
                    success: function (data, textStatus, jqXHR) {
                        console.log(data);
                        $('#display-message').html(data);
                        $('.add-btn').show();
                        $('.submit-loader').hide();
                    },
                    processData: false,
                    contentType: false,
                });
            });
        });
    </script>

  </body>
</html>