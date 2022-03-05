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

function showChatRoomList(chatRoom) {

    // var chatroom = $("#chatroom_list").find(`#user_${whom['whomSessionId']}`)

    // if (chatroom == null) {
        $("#chatroom_list").append(`
                <li id="chatRoom_${chatRoom['chatroomId']}">
                    ${chatRoom['withWhom']}
                </li>`)
    // }

    $(`#chatRoom_${chatRoom['chatroomId']}`).click(() => {
        var chatRoomClaim = JSON.stringify({
            'chatroomId': chatRoom['chatroomId'],
            'who': username,
            'whoSessionId': sessionId,
            'whomSessionId': chatRoom['whomSessionId']
        });

        stompClient.send('/app/chatroom', {}, chatRoomClaim);
    })
}

function showChatRoom(chatRoom){
    $("#chatroom_window_whom").html('');
    $("#chatroom_window_output").html('');
    $("#chatroom_window_input").html('');

    $("#chatroom_window_whom").append(`whom: ${chatRoom['withWhom']}`);

    // var messages = chatRoom['messages'];
    //
    // // for(var message: messages) {
    // //
    // // }
    //
    // messages.entries.forEach(message => {
    //     $("#chatroom_window_output").append(`<p>${message['body']}</p>`)
    // });
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
            showChatRoom(chatRoom);
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