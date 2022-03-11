var stompClient = null;
var username = null;
var sessionId = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#chat_users").show();
    }
    else {
        $("#chat_users").hide();
    }
}

function addChatRoom(event) {
    var chatroom = document.createElement('li');
    chatroom.id = `chatRoom_${event['chatroomId']}`;
    chatroom.textContent = `${event['whom']}`;

    var eventWindow = document.createElement('div');
    eventWindow.id = `event_window_${event['chatroomId']}`;
    eventWindow.textContent = `${event['eventType']}`;

    chatroom.appendChild(eventWindow);

    const chatroomList = document.querySelector('#chatroom_list');
    chatroomList.appendChild(chatroom);
}

function showChatRoomList(event) {
    if (event['eventType'] == "CREATE") {
        addChatRoom(event);
    }
    if (event['eventType'] == "NEW_MESSAGE") {
        var chatroom = document
            .querySelector(`#chatRoom_${event['chatroomId']}`);

        if (chatroom != null) {
            var eventWindow = chatroom.querySelector(`#event_window_${event['chatroomId']}`);
            eventWindow.textContent = `${event['eventType']}`;
        }
        else {
            addChatRoom(event);
        }
    }

    $(`#chatRoom_${event['chatroomId']}`).click(() => {
        var chatRoomClaim = JSON.stringify({
            'chatroomId': event['chatroomId'],
            'who': username,
            'whoSessionId': sessionId,
            'whomSessionId': event['whomSessionId']
        });

        stompClient.send('/app/chatroom', {}, chatRoomClaim);
    })
}

function showChatRoom(chatRoom){
    $("#chatroom_window_whom").html('');
    $("#chatroom_window_output").html('');
    $("#chatroom_window_input").html('');

    var withWhom = chatRoom['withWhom'];

    $("#chatroom_window_whom").append(`whom: ${withWhom}`);

    // var messages = chatRoom['messages'];
    //
    // // for(var message: messages) {
    // //
    // // }
    //
    // messages.entries.forEach(message => {
    //     $("#chatroom_window_output").append(`<p>${message['body']}</p>`)
    // });

    $("#send").click(() => {
        var message = JSON.stringify({
            'chatroomId': chatRoom['chatroomId'],
            'who': chatRoom['who'],
            'whoSessionId': chatRoom['whoSessionId'],
            'withWhom': chatRoom['withWhom'],
            'whomSessionId': chatRoom['whomSessionId'],
            'body': $("#chatroom_window_input").val()
        });

        stompClient.send('/app/message', {}, message);
    })
}

function showConnectedUsers(users) {
    $("#chat_users").html('');

    users.forEach(user => {
        if (user['username'] == username) sessionId = user['sessionId'];

        $("#chat_users").append(`
                        <li id="user_${user['sessionId']}">
                            ${user['username']}
                        </li>`)

        $(`#user_${user['sessionId']}`).click(() => {
            var chatroomClaim = JSON.stringify({
                'who': username,
                'whoSessionId': sessionId,
                'withWhom': user['username'],
                'whomSessionId': user['sessionId']});

            stompClient.send('/app/create_chatroom', {}, chatroomClaim);
        });
    })
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
        stompClient.subscribe('/user/queue/chatroom_list', function (event) {
            showChatRoomList(JSON.parse(event.body));
        });
        stompClient.subscribe('/user/queue/chatroom', function (chatRoom) {
            showChatRoom(JSON.parse(chatRoom.body));
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
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    // $( "#send" ).click(function() { sendName(); });
})