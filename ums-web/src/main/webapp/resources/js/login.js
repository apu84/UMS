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

  function getUserAndStartApplication(credentials, user) {
    var user = {
      firstName: user['firstName'],
      surName: user['surName'],
      username: user['username']
    };
    startApplication(credentials, user);
  }

  function startApplication(credentials, user) {
    document.cookie = "UMS.credentials=" + credentials + "; path=/";
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