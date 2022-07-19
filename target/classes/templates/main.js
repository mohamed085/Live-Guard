let btn = document.querySelector('#btn');
let div = document.querySelector('#div');
let userName = null;
let stomp = null;
let URL = "http://localhost:8081/api"

// function connectSocket(event) {
//     let socket = new SockJS(URL + '/connect');
//
//     stomp = Stomp.over(socket);
//     stomp.connect({}, function(frame) {
//         console.log('Connected: ' + frame);
//         stomp.subscribe('/live-guard/4', function(messageOutput) {
//             sendMessage(JSON.parse(messageOutput.body));
//         });
//     });
//
//     event.preventDefault()
// }

function connectSocket(event) {
    let socket = new SockJS(URL + '/connect');

    stomp = Stomp.over(socket);
    stomp.connect({}, function(frame) {
        console.log('Connected: ' + frame);
            stomp.subscribe('/live-guard/notification', function(messageOutput) {
            sendMessage(JSON.parse(messageOutput.body));
        });

        stomp.subscribe('/live-guard/notification/3', function(messageOutput) {
            sendMessage(JSON.parse(messageOutput.body));
        });
    });

    event.preventDefault()
}


function sendMessage(msg) {
    let div = document.querySelector('#div');
    let p = document.createElement('p');
    // p.appendChild(document.createTextNode("id: " + msg.id + ", lng: " + msg.lng + ", lat: " + msg.lat + ", date: " + msg.date));
    p.appendChild(document.createTextNode(msg.content))
    div.appendChild(p);
}


btn.addEventListener('click',connectSocket)
