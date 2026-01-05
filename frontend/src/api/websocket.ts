import { Client } from "@stomp/stompjs";

let client;

export function connectWS(roomId, onUpdate, onError, onPlayers) {
  client = new Client({
    brokerURL: "ws://localhost:8080/ws",
    reconnectDelay: 3000,
    onConnect: () => {
      client.subscribe(`/topic/puzzles/${roomId}`, (m) =>
        onUpdate(JSON.parse(m.body))
      );

      client.subscribe(`/topic/puzzles/${roomId}/errors`, (m) =>
        onError(JSON.parse(m.body).error)
      );

      client.subscribe(`/topic/puzzles/${roomId}/players`, (m) =>
        onPlayers(Number(m.body))
      );

      client.publish({
        destination: "/app/puzzle/join",
        body: roomId,
      });
    },
  });

  client.activate();
}

export function sendUpdate(payload) {
  client.publish({
    destination: "/app/puzzle/update",
    body: JSON.stringify(payload),
  });
}
