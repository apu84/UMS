importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-messaging.js');


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

console.log("this service worker is working" );

messaging.setBackgroundMessageHandler(function(payload) {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
    // Customize notification here
    var notificationTitle = 'TITLE: IUMS';
    var notificationOptions = {
        body: 'Your ABC Has been updated',
        icon: '/firebase-logo.png'
    };

    return self.registration.showNotification(notificationTitle,
        notificationOptions);
});