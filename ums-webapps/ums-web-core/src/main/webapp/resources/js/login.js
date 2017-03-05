var Authentication = (function () {
    var endpointUrl;

    function Authentication(pEndpoint) {
        endpointUrl = pEndpoint;
    }

    Authentication.prototype.authenticate = function (pUserName, pPassword) {
        var userName = pUserName ? pUserName : document.getElementById('userName').value;
        var password = pPassword ? pPassword : document.getElementById('password').value;

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
                credentials = "Basic " + btoa(userName + ":" + user.token);
                getUserAndStartApplication(credentials, user);
            },
            fail: function (msg) {
                console.error("Login failed....");
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
        var userId = $('#userId_forgotPassword').val();
        var emailAddress = $('#emailAddress_forgotPassword').val();
        var recoverBy = $('input[name=recoverByRadio]:checked').val();

        $(".loaderDiv").show();
        $("#btn_forgotPassword").hide();

        $.ajax({
            crossDomain: true,
            type: "PUT",
            async: true,
            url: window.location.origin + '/ums-webservice-academic/forgotPassword',
            contentType: 'application/json',
            data:'{"userId":"'+userId+'", "emailAddress":"'+emailAddress+'", "recoverBy":"'+recoverBy+'"}',

            success: function (response) {

                if (response.responseType == "SUCCESS") {
                    $("#errorDiv").hide();
                    $(".successdDiv").show();
                    $(".fPasswordDiv").hide();
                    $(".loaderDiv").hide();
                    $("#btn_forgotPassword").show();
                }
                else if (response.responseType != "ERROR") {
                    $("#errorDiv").show().html("<b>Sorry</b>, " + response.message);
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
        var _this = this;
        var resetToken = $("#password_reset_token").val();
        var userId = $("#user_id").val();
        var newPassword = $("#new_password").val();
        var confirmNewPassword = $("#confirm_new_password").val();

        if (newPassword != confirmNewPassword) {
            alert("New Password and Confirm New Password are not equal.");
            return;
        }

        $(".loaderDiv").show();
        $("#btn_change_password").hide();
        $.ajax({
            crossDomain: true,
            type: "PUT",
            async: true,
            url: window.location.origin + '/ums-webservice-academic/forgotPassword/resetPassword',
            contentType: 'application/json',
            data: '{"userId":"' + userId + '","passwordResetToken":"' + resetToken + '","newPassword":"' + newPassword + '","confirmNewPassword":"' + confirmNewPassword + '"}',
            success: function (response) {
                if (response.responseType == "SUCCESS") {
                    _this.authenticate(userId, newPassword);
                }
                else if (response.responseType == "ERROR") {
                    $(".loaderDiv").hide();
                    $("#btn_change_password").show();
                    $("#errorDiv").show().html("<b>Sorry</b>, " + response.message);
                }
            },
            error: (function (httpObj, textStatus) {
                $(".loaderDiv").hide();
                $("#btn_change_password").show();

            })
        });
    };


    function getUserAndStartApplication(credentials, user) {
        var userObject = {
            userId: user['userId'],
            userName: user['userName']
        };
        startApplication(credentials, userObject);
    }

    function startApplication(credentials, user) {
        sessionStorage.setItem("ums.token", credentials);
        sessionStorage.setItem("ums.user", JSON.stringify(user));
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

    function presentErrorMessage(msg) {
        document.getElementById("login_msg").textContent = msg;
        $("#login_msg").show();
    }

    function getBaseAppUrl() {
        return window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1) + 1);
    }

    return Authentication;
})();
