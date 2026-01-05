# Real-Time Collaborative Logic Puzzle Platform

A full-stack real-time collaborative 4×4 logic puzzle platform where multiple users can create or join rooms and solve puzzles together with live synchronization.

> **Internship Coding Challenge Submission**

---

## 🔗 Repository
GitHub: https://github.com/PiyushPb/real-time-logic-puzzle

---

## 📌 Overview

This project implements a **server-authoritative**, **real-time collaborative puzzle system**.  
Each puzzle acts as a **room**, supporting **up to 2 active players** simultaneously, while additional users can join as **spectators**.

Key highlights:
- Real-time updates using **WebSockets (STOMP)**
- Optimistic UI with rollback
- Concurrency-safe backend
- Clean separation of backend and frontend
- No UI component libraries (Tailwind CSS only)

---

## 🧩 Puzzle Rules (4×4 Logic Puzzle)

- Grid size: **4×4**
- Allowed values: **1–4**
- `0` represents an empty cell
- Rules:
  - No duplicate numbers in any **row**
  - No duplicate numbers in any **column**
- Validation is enforced **server-side only**

---

## 🏗️ Architecture

### High-Level Flow

1. User creates or joins a room
2. Backend creates or fetches the puzzle
3. Users connect via WebSocket
4. Updates are validated and broadcast in real time
5. Server remains the single source of truth

---

## ⚙️ Backend

### Tech Stack
- Java 21
- Spring Boot 3
- Spring WebSocket (STOMP)
- Spring Data JPA (Hibernate)
- H2 In-Memory Database
- Maven

### Core Backend Features

- **REST APIs**
  - Create puzzle
  - Fetch puzzle state
- **WebSocket APIs**
  - Join room
  - Real-time puzzle updates
  - Player count updates
- **Concurrency Handling**
  - Optimistic locking for REST
  - Server-authoritative updates for WebSocket
- **Room Management**
  - Max 2 players per room
  - Spectator support
  - Automatic cleanup on disconnect
- **Validation**
  - Grid shape, value range, row/column uniqueness
- **Error Handling**
  - REST: HTTP status codes
  - WebSocket: Error topics

### Backend Endpoints

#### REST
```
POST   /api/puzzles          -> Create a new puzzle (room)
GET    /api/puzzles/{id}     -> Fetch puzzle state
```

#### WebSocket
```
/ws                          -> WebSocket endpoint
/app/puzzle/join             -> Join a room
/app/puzzle/update           -> Send puzzle updates
/topic/puzzles/{id}          -> Receive puzzle updates
/topic/puzzles/{id}/players  -> Player count updates
/topic/puzzles/{id}/errors   -> Error messages
```

---

## 🎨 Frontend

### Tech Stack
- React 18 (Vite)
- Redux Toolkit
- React Router DOM
- STOMP.js
- Tailwind CSS

### Frontend Features

- Lobby page (Create / Join room)
- Room page with:
  - Live puzzle grid
  - Player / Spectator role display
  - Player count display
- Optimistic UI updates
- Automatic rollback on invalid moves
- Real-time synchronization across tabs

---

## 🧑‍🤝‍🧑 Multiplayer Rules

| Role        | Capabilities |
|------------|--------------|
| Player (1–2) | Can modify the puzzle |
| Spectator   | Read-only view |

- Max **2 active players** per room
- Spectators automatically become players when a slot frees up
- Player count updates in real time

---

## 🧪 How to Run Locally

### Backend

```bash
cd backend
mvn clean spring-boot:run
```

- Runs on: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2`

---

### Frontend

```bash
cd frontend
npm install
npm run dev
```

- Runs on: `http://localhost:5173`

---

## 🖼️ Screenshots

### Lobby Page
<img width="1920" height="914" alt="image" src="https://github.com/user-attachments/assets/c38b0cb2-35a9-4dde-a922-ca057d9e05a4" />

### Room View (Player)
<img width="1920" height="911" alt="image" src="https://github.com/user-attachments/assets/eadd431d-e7b5-48de-a406-f9afaae516a7" />
<img width="1920" height="913" alt="image" src="https://github.com/user-attachments/assets/6ab01108-3237-4287-95d1-c67a5c8909ef" />

Player 2 view

<img width="1920" height="911" alt="image" src="https://github.com/user-attachments/assets/5437f09f-6d13-4a33-9707-3cfaf9d9e61b" />


### Room View (Spectator)
<img width="1920" height="899" alt="image" src="https://github.com/user-attachments/assets/f3563311-56cb-4b6d-a66e-1d2327f0f225" />

---

## ✅ Assignment Requirements Mapping

| Requirement | Status |
|------------|-------|
| Real-time synchronization | ✅ |
| No race conditions | ✅ |
| WebSocket (STOMP) | ✅ |
| Server-side validation | ✅ |
| Optimistic UI + rollback | ✅ |
| Clean code structure | ✅ |
| Original implementation | ✅ |

---

## 🧠 Design Decisions

- **Server-authoritative state** to avoid conflicts
- **Separate REST and WebSocket update strategies**
- **Session-based room membership**
- **Minimal but meaningful test coverage**
- **No over-engineering**

---

## 🚀 Possible Enhancements

- Authentication & usernames
- Turn-based play
- Puzzle completion detection
- Persistent database (PostgreSQL)
- Puzzle templates / difficulty levels

---

## 👤 Author

**Piyush Pardeshi**  
Full Stack Developer (MERN + Spring Boot)  
GitHub: https://github.com/PiyushPb

---

## 📄 License

This project is created for an internship coding assignment and is intended for educational and evaluation purposes.
