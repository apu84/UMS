var config = {
    apiKey: "AIzaSyArP6rXmkpIkUSF4qlnpUAIYTv8wpdjrGM",
    authDomain: "fcm-integration-fac11.firebaseapp.com",
    databaseURL: "https://fcm-integration-fac11.firebaseio.com",
    projectId: "fcm-integration-fac11",
    storageBucket: "fcm-integration-fac11.appspot.com",
    messagingSenderId: "485026400295"
};

firebase.initializeApp(config);
const messaging = firebase.messaging();

requestPermission();

messaging.onTokenRefresh(function() {
    messaging.getToken().then(function(refreshedToken) {
        setTokenSentToServer(false);
        sendTokenToServer(refreshedToken);
    }).catch(function(err) {
        console.log('Unable to retrieve refreshed token ', err);
    });
});

messaging.onMessage(function(payload) {
    var notificationTitle = payload.notification.title;
    var notificationOptions = {
        body: payload.notification.body,
        icon: '../images/branding.jpg'
    };
    new Notification(notificationTitle, notificationOptions);
});

function generateToken() {
    messaging.getToken().then(function(currentToken) {
        if (currentToken) {
            sendTokenToServer(currentToken);
        } else {
            console.log('No Instance ID token available. Request permission to generate one.');
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

function sendTokenToServer(currentToken) {
    if (!isTokenSentToServer()) {
        $.ajax({
            crossDomain: true,
            type: "POST",
            async: true,
            url: window.location.origin + '/ums-webservice-academic/fcmToken',
            contentType: 'application/json',
            data: '{"entries" : { "fcmToken":"' + currentToken + '" } }',
            headers: {
                "Authorization": JSON.parse(sessionStorage.getItem("ums.token"))["access_token"],
                "Accept": "application/json"
            },
            success: function (response) {
                setTokenSentToServer(true);
            },
            error: (function (error) {
                console.log("error in saving");
                console.log(error);
            })
        });
    } else {
        console.log('Token already sent to server so won\'t send it again ' +
            'unless it changes');
    }
}


function requestPermission() {
    messaging.requestPermission().then(function() {
        generateToken();
    }).catch(function(err) {
        console.log('Unable to get permission to notify.', err);
    });
}

function deleteToken() {
    messaging.getToken().then(function(currentToken) {
        messaging.deleteToken(currentToken).then(function() {
            setTokenSentToServer(false);
            generateToken();
        }).catch(function(err) {
            console.log('Unable to delete token. ', err);
        });
    }).catch(function(err) {
        console.log('Error retrieving Instance ID token. ', err);
    });
}