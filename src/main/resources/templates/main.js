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
    let token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtb2hhbWVkMDg1NTU1QGdtYWlsLmNvbSIsImV4cCI6MTY1MTc3NTA1NX0.DDon7shMG-dFhcfcEvtjwJOQQpmSTGDwGR5POxFWd7n1QzpeXqMol608-wZvADaQti7zbYVsls60hyvT3CfgvA";

    let socket = new SockJS(URL + '/connect');

    stomp = Stomp.over(socket);
    stomp.connect({"Authorization": "Bearer " + token}, function(frame) {
        console.log('Connected: ' + frame);
            stomp.subscribe('/live-guard/global-notification', function(messageOutput) {
            sendMessage(JSON.parse(messageOutput.body));
        });

        stomp.subscribe('/user/live-guard/private-notification', function(messageOutput) {
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
