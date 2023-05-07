<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="header">
  <nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a class="navbar-brand" href="./index.jsp">Ct₹l Budget</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav navbar-right ml-auto nav-links">

        <%
            if (session.getAttribute("CurrentUser") != null) {
        %>
        <li class="nav-item">
          <a class="nav-link" href="home.jsp">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="aboutus.jsp">About Us</a>
        </li>
        <!--        <li class="nav-item">
                  <a class="nav-link" href="settings.jsp">Change Password</a>
                </li>-->
        <li class="nav-item">
          <a class="nav-link" href="logoutServlet">Logout</a>
        </li>
        <% } else { %>
        <li class="nav-item">
          <a class="nav-link" href="aboutus.jsp">About Us</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="signup.jsp">Sign Up</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="login.jsp">Login</a>
        </li>
        <% }%>

      </ul>
    </div>
  </nav>
</div>


<!--<nav class="navbar navbar-expand-lg navbar-dark bg-dark navbar-inverse navbar-fixed-top">

    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="./../index.jsp">Ct₹l Budget</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">

                <li><a href="aboutus.jsp"><span class="glyphicon glyphicon-info-sign"></span> About Us</a></li>
                <li><a href="contactus.jsp"><span class="glyphicon glyphicon-info-sign"></span> Contact Us</a></li>
                <li><a href="signup.jsp"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                <li><a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>


            </ul>
        </div>
    </div>
</nav>-->