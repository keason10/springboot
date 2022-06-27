var stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
    $("#chat").show();
  } else {
    $("#conversation").hide();
    $("#chat").hide();
  }
  $("#greetings").html("");
}

function connect() {
  console.log("connect-------------->start");
  if (!$("#name").val()) {
    return;
  }
  var socket = new SockJS('/chat');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    setConnected(true);
    stompClient.subscribe('/topic/greetings', function (greeting) {
      console.log("message========" + greeting);
      var decodedMsg=atob(JSON.parse(greeting.body).payload);
      console.log("message decodedMsg ========" + decodedMsg);
      showGreeting(JSON.parse(decodedMsg));
    });
  });
  console.log("connect-------------->end");
}

function disconnect() {
  console.log("disconnect-------------------> start");
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("disconnect-----------------> end");
}

function sendName() {
  console.log("sendName--------------------->start");
  stompClient.send("/app/hello", {}, JSON.stringify({
    'name': $("#name").val(),
    'content': $("#content").val()
  }));
  console.log("sendName--------------------->end");
}

function showGreeting(message) {
  $("#greetings").append(
      "<div>" + message.name + ":" + message.content + "</div>"
  );
}

$(function () {
  $("#content").click(function () {
    connect();
  });
  $("#disconnect").click(function () {
    disconnect()
  });
  $("#send").click(function () {
    sendName();
  });
});

