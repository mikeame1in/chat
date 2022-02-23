var stompClient = null;
var username = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#chat_users").show();
    }
    else {
        $("#chat_users").hide();
    }
    // $("#greetings").html("");
}

function showConnectedUsers(users) {
    $("#chat_users").html('');
    users.forEach(user => $("#chat_users").append("<li>" + user['username'] + "</li>"));
}

function connect() {
    var socket = new SockJS('/chat');
    username = JSON.stringify({'username': $("#webchat_username").val()});

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);

        console.log('Connected: ' + frame);

        stompClient.send('/app/register', {}, username);
        stompClient.subscribe('/topic/users', function (users) {
            showConnectedUsers(JSON.parse(users.body).body);
        });
    });
}

function disconnect() {
    if (stompClient !== null | username !== null) {
        setConnected(false);

        stompClient.send('/app/unregister', {}, username);
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

$(function () {
    // $("form").on('submit', function (e) {
    //     e.preventDefault();
    // });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    // $( "#send" ).click(function() { sendName(); });
})