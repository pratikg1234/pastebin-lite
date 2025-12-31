📝 Pastebin Lite – Frontend

A lightweight Pastebin-style frontend built using React + Vite, allowing users to create and view text pastes via shareable links.
This frontend communicates with a Spring Boot backend API and is deployed as a static site on Render.

🚀 Live Demo

Frontend:
👉 https://pastebin-lite-frontend.onrender.com

🧱 Tech Stack
Layer	Technology
Framework	React (Vite)
Language	JavaScript
Routing	React Router
HTTP Client	Axios
Build Tool	Vite
Hosting	Render (Static Site)

⚙️ Environment Variables

Create a .env file in the frontend root directory.

.env
VITE_API_BASE_URL=http://localhost:8080

For Production (Render)

Set this in Render → Environment Variables:

VITE_API_BASE_URL=https://pastebin-lite-2-vgtz.onrender.com
For Production (Render)

Set this in Render → Environment Variables:

VITE_API_BASE_URL=https://pastebin-lite-2-vgtz.onrender.com

🛠️ Running Locally
1️⃣ Prerequisites

Node.js ≥ 18

Backend running locally on http://localhost:8080

2️⃣ Install Dependencies
npm install

3️⃣ Start Development Server
npm run dev

App will run at:

http://localhost:5173