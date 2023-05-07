<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.planner.entities.PlanDetails"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.planner.helper.ConnectionProvider"%>
<%@page import="com.planner.dao.PlanDetailsDao"%>
<%@page import="com.planner.entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("CurrentUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
    }

    String[] suffixes
            = //    0     1     2     3     4     5     6     7     8     9
            {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                //    10    11    12    13    14    15    16    17    18    19
                "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                //    20    21    22    23    24    25    26    27    28    29
                "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                //    30    31
                "th", "st"};
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

    <div class="page-body">
      <%@include file = "include/header.jsp" %>
      <div class="home-page">
        <div class="container-fluid home-panel">
          <div class="container">
            <%
                PlanDetailsDao planDao = new PlanDetailsDao(ConnectionProvider.getConnection());
                ArrayList<PlanDetails> planList = planDao.getAllPlan(user.getId());
                if (planList.isEmpty()) {
            %>
            <div class="heading">
              <h1>You don't have any active plans</h1>
            </div>
            <div class="row">
              <div class="col-md-4 offset-md-4 offset-lg-4 col-sm-6 offset-sm-3">
                <div class="col-lg-10 offset-lg-1">
                  <a href="create-new-plan.jsp">
                    <div class="create-new-plan">
                      <span class="fa fa-plus-square"></span> Create New Plan
                    </div>
                  </a>
                </div>
              </div>
            </div>

            <% } else { %>

            <div class="heading">
              <h1>Your plans</h1>
            </div>

            <div class="row">

              <% for (PlanDetails p : planList) {%>

              <div class="col-md-6 col-lg-4 col-sm-8 offset-sm-2 offset-md-3 offset-lg-0 plans-row">
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

                    <div class="budget-plan">
                      <b class="label">Plan Created On</b>
                      <span class="value"> <%= new SimpleDateFormat("d").format(p.getCreatedAt()) + suffixes[Integer.parseInt(new SimpleDateFormat("d").format(p.getCreatedAt()))] + new SimpleDateFormat(" MMM YYYY").format(p.getCreatedAt())%> </span>
                    </div>

                    <a href="view-plan.jsp?plan_id= <%= p.getPlan_id()%>">
                      <button class="btn btn-center view-plan-btn form-control">View Plan</button>
                    </a>
                  </div>

                </div>
              </div>
              <%
                  }
              %>
            </div>
            <%
                }
            %>
          </div>

        </div>
        <a href="create-new-plan.jsp">
          <div class="create-new-plan-button">
            <span class="fa fa-plus-circle fa-5x"></span>
          </div>
        </a>
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

  </body>
</html>