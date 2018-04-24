var config = {
    apiKey: "AIzaSyDmUDs-kXZyWQa52tmyJcA8rlMKPP-bgM0",
    authDomain: "push-notification-demo-6b41d.firebaseapp.com",
    databaseURL: "https://push-notification-demo-6b41d.firebaseio.com",
    projectId: "push-notification-demo-6b41d",
    storageBucket: "push-notification-demo-6b41d.appspot.com",
    messagingSenderId: "75416653943"
};
firebase.initializeApp(config);
const messaging = firebase.messaging();

messaging.requestPermission()
    .then(function() {
        console.log('Have Permission.');
        retrieveCurrentRegistrationToken();
    }).catch(function(err) {
    console.log('Unable to get permission to notify.', err);
});

messaging.onMessage(function(payload) {
    console.log('Message received. ', payload);
});


function retrieveCurrentRegistrationToken() {
    messaging.getToken().then(function(currentToken) {
        if (currentToken) {
            sendTokenToServer(currentToken);
            //updateUIForPushEnabled(currentToken);
        } else {
            console.log('No Instance ID token available. Request permission to generate one.');
            //updateUIForPushPermissionRequired();
            setTokenSentToServer(false);
        }
    }).catch(function(err) {
        console.log('An error occurred while retrieving token. ', err);
        setTokenSentToServer(false);
    });
}

function setTokenSentToServer(sent) {
    window.localStorage.setItem('sentToServer', sent ? 1 : 0);
}

function isTokenSentToServer() {
    return window.localStorage.getItem('sentToServer') === 1;
}

function sendTokenToServer(fcmToken) {
    console.log("In sendTokenToServerMehtod()");
    $.ajax({
        crossDomain: true,
        type: "POST",
        async: true,
        url: window.location.origin + '/ums-webservice-academic/fcmToken/save',
        contentType: 'application/json',
        data: '{"entries" : { "fcmToken":"' + fcmToken + '" } }',
        headers: {
            "Authorization": JSON.parse(sessionStorage.getItem("ums.token"))["access_token"],
            "Accept": "application/json"
        },
        success: function (response) {
            console.log("successfull in saving");
            console.log(response);
        },
        error: (function (error) {
            console.log("error in saving");
            console.log(error);
        })
    });
}

function deleteToken() {
    messaging.getToken().then(function (currentToken) {
        messaging.deleteToken(currentToken).then(function () {
            console.log('Token deleted.');
            setTokenSentToServer(false);
        }).catch(function (err) {
            console.log('Unable to delete token. ', err);
        });
    }).catch(function (err) {
        console.log('Error retrieving Instance ID token. ', err);
    });
}