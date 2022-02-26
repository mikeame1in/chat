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

function showChatRoomList(message) {
    var whom = JSON.parse(message).whom;
    var chatroom = $("#chatroom_list").find(`#user_${user['sessionId']}`)

    if (chatroom == null) {
        $("#chatroom_list").append(`
                <li id="user_${user['sessionId']}">
                    ${user['username']}
                </li>`)
    } else {
        chatroom.html('');
        chatroom.append("new message");
    }
}

function showChatRoom(who, whom){
    $("#chat_window").html('');
    $("#chat_window").append(`<div class="row text-center"><h3>Chat</h3></div>`);
     // `<div class="row text-center">\n  <h3>Chat with ${chatUsername}</h3>\n</div>\n<div id="chat_messages_${chatUsername}" class="row"></div>\n<br/>\n<div class="row">\n  <div class="col-md-4"><textarea id="chat_input_${chatUsername}"></textarea></div>\n  <div class="col-md-1"><input type="button" id="chat_send_to_${chatUsername}" value="Send"/></div>\n</div>`
}

function startChat(who, whom) {

    // showChatRoom(who, whom);

    var message = JSON.stringify(
        {
            'who': who,
            'whom': whom,
            'body': 'hello!'
        });

    stompClient.send('/app/message', {}, message);
}



function showConnectedUsers(users) {
    $("#chat_users").html('');
    users.forEach(user => $("#chat_users")
        .append(`
                <li id="user_${user['sessionId']}">
                    ${user['username']}
                </li>`)
                );
    users.forEach(user =>
        $(`#user_${user['sessionId']}`).click(() => {
            var who = document.querySelector('#webchat_username').value;
            var whom = document
                .querySelector(`#user_${user['sessionId']}`)
                .textContent.replace(/[\n\r]+|[\s]{2,}/g, ' ').trim();

            $("#chatroom_list").append(`<li>Chat with ${whom}</li>`);

            startChat(who, whom);
        }) )
}

function connect() {
    username = $("#webchat_username").val();

    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({},function (frame) {
        setConnected(true);

        console.log('Connected: ' + frame);

        var message = JSON.stringify({'username': username});
        stompClient.subscribe('/topic/users', function (users) {
            showConnectedUsers(JSON.parse(users.body).body);
        });
        stompClient.subscribe('/user/queue/chatroom', function (message) {
            console.log(JSON.parse(message).body);
            showChatRoomList(message);
        });
        stompClient.send('/app/register', {}, message);
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