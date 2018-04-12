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

console.log("In service worker ------ after inti firebase()");
const messaging = firebase.messaging();

console.log("const messaging ----- ");
console.log(messaging);

messaging.setBackgroundMessageHandler(function(payload) {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
    // Customize notification here
    var notificationTitle = 'Background Message Title';
    var notificationOptions = {
        body: 'Background Message body.'
    };
    return self.registration.showNotification(notificationTitle,
        notificationOptions);
});