var Authentication = (function () {
  function Authentication() {
  }

  var endpointUrl = window.location.origin + '/ums-webservice-common/login';


  Authentication.prototype.authenticate = function () {
    eraseAllCookies();
    var userName = document.getElementById('userName').value;
    var password = document.getElementById('password').value;

    $(".loaderDiv").show();
    $("#login_btn").hide();

    var credentials = "Basic " + btoa(userName + ":" + password);
    $.ajax({
      crossDomain: true,
      type: "GET",
      async: true,
      url: endpointUrl,
      dataType: 'json',
      withCredentials: true,
      headers: {
        "Authorization": credentials,
        "Accept": "application/json"
      },
      success: function (user) {
        console.log("submitting the form !!");
        $('#loginForm').submit();
        credentials = "Basic " + btoa(userName + ":" + user.token);
        getUserAndStartApplication(credentials, user);

      },
      fail: function (msg) {
        console.log("failed....");
      },
      error: (function (httpObj, textStatus) {

        if (httpObj.status == 200) {
          startApplication();
        }
        else if (httpObj.status == 401) {
          presentErrorMessage("Incorrect username or password!");
        }
        else {
          presentErrorMessage("Login Failed! " + textStatus);
        }
        $(".loaderDiv").hide();
        $("#login_btn").show();
      })
    });
  };

  Authentication.prototype.forgotPassword = function () {
    eraseAllCookies();
    var userId = $('#userId_forgotPassword').val();

    $(".loaderDiv").show();
    $("#btn_forgotPassword").hide();
    var credentials = "Basic " + btoa("dpregistrar" + ":" + "12345");
    $.ajax({
      crossDomain: true,
      type: "PUT",
      async: true,
      url: window.location.origin + '/ums-webservice-common/forgotPassword',
      contentType: 'application/json',
      data:'{"userId":"'+userId+'"}',
      withCredentials: true,
      headers: {
        "Authorization": credentials,
        "Accept": "application/json"
      },
      success: function (response) {

        if(response.code=="OK"){
          $("#errorDiv").hide();
          $(".successdDiv").show();
          $(".fPasswordDiv").hide();
          $(".loaderDiv").hide();
          $("#btn_forgotPassword").show();
        }
        else if(response.code=="KO"){
          $("#errorDiv").show();
          $("#errorDiv").html("<b>Sorry</b>, "+response.message);
          $(".loaderDiv").hide();
          $("#btn_forgotPassword").show();
        }
        $("#login_msg").hide();

      },
      error: (function (httpObj, textStatus) {
        $(".loaderDiv").hide();
        $("#btn_forgotPassword").show();
      })
    });
  };


  Authentication.prototype.changePassword = function () {
    eraseAllCookies();

    var resetToken=$("#password_reset_token").val();
    var userId=$("#user_id").val();
    var newPassword=$("#new_password").val();
    var confirmNewPassword=$("#confirm_new_password").val();

    if(newPassword!=confirmNewPassword){
      alert("New Password and Confirm New Password are not equal.");
      return;
    }

    $(".loaderDiv").show();
    $("#btn_change_password").hide();

    var credentials = "Basic " + btoa("dpregistrar" + ":" + "12345");
    $.ajax({
      crossDomain: true,
      type: "PUT",
      async: true,
      url: window.location.origin + '/ums-webservice-common/forgotPassword/resetPassword1',
      contentType: 'application/json',
      data:'{"userId":"'+userId+'","passwordResetToken":"'+resetToken+'","newPassword":"'+newPassword+'","confirmNewPassword":"'+confirmNewPassword+'"}',
      withCredentials: true,
      headers: {
        "Authorization": credentials,
        "Accept": "application/json"
      },
      success: function (response) {

        credentials = "Basic " + btoa(userId + ":" + newPassword);
        if(response.code=="OK"){

          var user={"userId":userId,"userName":userId};
          getUserAndStartApplication(credentials, user);
        }
        else if(response.code=="KO"){

          $(".loaderDiv").hide();
          $("#btn_change_password").show();

          $("#errorDiv").show();
          $("#errorDiv").html("<b>Sorry</b>, "+response.message);

        }
        //   $("#login_msg").hide();

      },
      error: (function (httpObj, textStatus) {
        $(".loaderDiv").hide();
        $("#btn_change_password").show();

      })
    });
  };



  function getUserAndStartApplication(credentials, user) {
    var user = {
      userId: user['userId'],
      userName: user['userName']
    };
    var credential = {
      credential: credentials
    };
    startApplication(credential, user);
  }

  function startApplication(credentials, user) {
    document.cookie = "UMS.credentials=" + JSON.stringify(credentials) + "; path=/";
    document.cookie = "UMS.user=" + JSON.stringify(user) + "; path=/";
    var params = getQueryParams();
    if (isValidRedirectTo()) {
      window.location.href = decodeURIComponent(params.redirectTo);
    }
    else {
      window.location.href = getBaseAppUrl() + 'iums/#/';
    }
  }

  function isValidRedirectTo() {
    var params = getQueryParams();
    if (params.redirectTo) {
      var redirectTo = decodeURIComponent(params.redirectTo);
      var hostName = getHostname(redirectTo);
      if (isSameHost(hostName) && isUMSApp(redirectTo)) {
        return true;
      }
    }
    return false;
  }

  function isUMSApp(redirectTo) {
    var origin = getOrigin(redirectTo);
    return redirectTo.replace(origin, '').indexOf('/ums-web') == 0;
  }

  function isSameHost(hostname) {
    var currentHostName = window.location.hostname;
    return currentHostName === hostname;
  }

  function getHostname(href) {
    var l = getLocation(href);
    return l.hostname;
  }

  function getOrigin(href) {
    var l = getLocation(href);
    return l.origin;
  }

  function getLocation(href) {
    var l = document.createElement("a");
    l.href = href;
    return l;
  }

  function getQueryParams() {
    var params = {};
    var pairs = window.location.search.replace('?', '').split('&');
    for (var i = 0; i < pairs.length; i++) {
      var split = pairs[i].split('=');
      if (split[0] != '') {
        params[split[0]] = split[1];
      }
    }
    return params;
  }

  function eraseAllCookies() {
    var cookies = document.cookie.split(";");
    for (var i = 0; i < cookies.length; i++) {
      eraseCookie(cookies[i].split("=")[0]);
    }
  }

  function eraseCookie(name) {
    var date = new Date();
    date.setTime(date.getTime() + (-1 * 24 * 60 * 60 * 1000));
    var expires = "; expires=" + date.toGMTString();
    document.cookie = name + "=" + "" + expires + "; path=/";
  }

  function presentErrorMessage(msg) {
    document.getElementById("login_msg").textContent = msg;
    $("#login_msg").show();
  }

  function getBaseAppUrl() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1) + 1);
  }

  return Authentication;
})();