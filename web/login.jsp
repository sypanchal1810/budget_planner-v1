<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login | Control Budget</title>

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
      <%@include file="include/header.jsp" %>
      <div class="login form-page">
        <div class="container-fluid login-panel form-panel">
          <div class="row align-items-center">
            <div class="panel col-lg-4 offset-lg-4 col-md-6 offset-md-3 col-sm-6 offset-sm-3 align-self-center">

              <div class="panel-heading">
                Log Into Your Account
              </div>

              <form id="login-form" action="loginServlet" method="POST">

                <div class="form-group">
                  <label for="email">Email: </label>
                  <input type="email" class="form-control" placeholder="example@abc.com" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="email" required>
                </div>

                <div class="form-group">
                  <label for="password">Password: </label>
                  <input type="password" class="form-control" placeholder="••••••••" name="password" minlength="8" required>
                </div>

                <div class="form-group">
                  <button type="submit" name="submit" class="btn btn-center login-btn form-control">Login</button>
                </div>

                <div class="form-group">
                  <div style="text-align: center" >
                    Don't have an account?
                    <a href="./signup.jsp">
                      Create new account here
                    </a>
                  </div>
                </div>

                <div class="form-group">
                  <div class='submit-loader' style="text-align: center; display: none">
                    <i class="fa fa-refresh fa-spin fa-3x"></i>
                  </div>
                </div>

              </form>
            </div>
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

            $("#login-form").on('submit', function (e) {
                e.preventDefault();
                $('.login-btn').hide();
                $('.submit-loader').show();

                let form = new FormData(this);
                $.ajax({
                    url: "loginServlet",
                    type: "POST",
                    data: form,
                    success: function (data, textStatus, jqXHR) {
                        console.log(data);
                        $('#display-message').html(data);
                        $('.submit-btn').show();
                        $('.submit-loader').hide();
                    },
                    processData: false,
                    contentType: false
                });
            });
        });
    </script>


  </body>
</html>