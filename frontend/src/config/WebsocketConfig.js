import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

let client = null;

const connect = (onMessageReceived, chatId) => {
  client = new Client({
    webSocketFactory: () => new SockJS('http://localhost:8080/chat'), // Use SockJS
    onConnect: () => {
      client.subscribe(`/topic/chat/${chatId}`, (message) => {
        onMessageReceived(JSON.parse(message.body));
      });
    },
    reconnectDelay: 5000,
  });

  client.activate();
};

const disconnect = () => {
  if (client) {
    client.deactivate();
  }
};

const sendMessage = (chatId, messageObj) => {
    if (client && client.connected) {
      client.publish({ 
        destination: `/app/chat/${chatId}/sendMessage`, 
        body: JSON.stringify(messageObj)
      });
    }
  };

export { connect, disconnect, sendMessage };
