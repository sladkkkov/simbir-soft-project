// 'use strict';
//
// var messageForm = document.guerySelector('#messageForm');
// var messageInput = document.guerySelector('#text');
// var messageArea = document.guerySelector('#messageArea');
// var connectingElement = document.guerySelector('#.connecting');
// var roomNumber = document.location.pathname.substr(document.location.pathname.lastIndexOf("message") + 8);
//
// var stompClient = null;
// var username = null;
//
// function onConnected() {
//     stompClient.subscribe('/topic/public', onMessageReceived);
//     connectingElement.classList.add('hidden');
// }
//
// function onError(error) {
//     connectingElement.textContent = 'Could not connect to WebSocket server. Refresh this page';
//     connectingElement.style.color = 'red';
// }
//
// function send(event) {
//     if (!stompClient) {
//         username = document.querySelector('#name').textContent.trim();
//
//         if (username) {
//             var socket = new SockJs('/chat-websocket');
//             stompClient = Stomp.over(socket);
//
//             stompClient.connect
//             ({},onConnected(), onError());
//         }
//         event.preventDefault();
//     } else{
//         var messageContent = messageInput.value.trim();
//     }
// }
//
// function onMessageReceived(payload) {
//     var message = JSON.parse(payload.body);
//
//     if((message.room === roomNumber && message.room != 'bot') || (message.author === username){
//     var messageElement = document.createElement('li');
//
//     messageElement.classList.add('chat-message');
//     var usernameElement = document.createElement('spam');
//     var usernameText = document.createTextNode(message.author + ' (' + message.creationDate + ') ');
//     usernameElement.appendChild(usernameText);
//     usernameElement.appendChild(usernameElement);
//
//     var TextElement = document.createElement('p');
//     textElement.innerHTML = message.text;
//
//     messageElement.appendChild(textElement);
//
//     messageArea.appendChild(messageElement);
//     }
// }
// messageForm.addEventListener('submit', send,true);
// document.querySelector('#send').click();