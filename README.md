# 🗣️ VoiceLog – Offline Voice-Based Contact Logger

**VoiceLog** is an Android app that passively listens while open, identifies who you're talking to using speaker recognition, and logs every encounter with name, phone number, and timestamp — all processed locally with no internet required.

---

## 📱 Features

- 🎤 **Live Voice Detection** – Microphone starts listening automatically whenever the app window is active
- 🧠 **Local Voiceprint Matching** – Matches speech to previously saved voiceprints using cosine similarity
- 🧾 **Contact Assignment** – Automatically displays saved name and optional phone number for recognized speakers
- ❓ **Unknown Handling** – Prompts you to enter name/number when a new voice is detected
- 💾 **Learning Mode** – Saves new voices after you provide their details
- 🗂️ **Conversation Log** – Time-stamped history of each interaction
- 🚫 **Self-Voice Filtering** – Optionally mark your own voice so it won't be logged

---

## 🔒 Privacy-First Design

- No transcription
- No internet access
- All voice data and embeddings are stored locally and encrypted (if desired)

---

## 🛠 Tech Stack

| Component        | Technology                    |
|------------------|-------------------------------|
| Language         | Kotlin                        |
| UI Framework     | Jetpack Compose (or XML)      |
| Audio Input      | Android `AudioRecord` API     |
| Voice Embedding  | `ECAPA-TDNN` via ONNX Runtime |
| Database         | `Room` (Android SQLite)       |
| Matching Logic   | Cosine Similarity             |

---

## 📦 Folder Structure

VoiceLog/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/voicelog/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── VoiceProcessor.kt
│   │   │   │   ├── Embedder.kt
│   │   │   │   ├── Matcher.kt
│   │   │   │   ├── SpeakerDB.kt
│   │   │   │   ├── LogDB.kt
│   │   │   │   └── AddSpeakerDialog.kt
│   │   │   └── res/
│   │   │       └── layout/
│   │   └── AndroidManifest.xml
├── model/
│   └── ecapa-tdnn.onnx
└── README.md

---

## 🚀 Getting Started

### 1. Clone the Repo
```bash
git clone https://github.com/yourusername/voicelog.git
cd voicelog
```

### 2. Open in Android Studio

- Open the project directory.
- Make sure ONNX Runtime Mobile is installed via Gradle.

### 3. Add the ECAPA-TDNN Model

- Place the `ecapa-tdnn.onnx` file in the `model/` folder.
- Model must take ~3–10 seconds of raw audio and return a speaker embedding vector.

### 4. Run the App

- Connect your Android device (API 26+ recommended)
- Hit Run ▶️
- Start talking — the app will identify known voices and let you add new ones.

---

## 📌 TODO

- [ ] ONNX inference integration
- [ ] Real-time mic buffering (5–10 sec)
- [ ] Embedding matching and UI updates
- [ ] Log view & export
- [ ] (Optional) Encrypted voiceprint storage

---

## 🤝 License

MIT or proprietary – your choice depending on commercial use plans.

---

## 💡 Notes

This is a local-only MVP and does **not** run in the background by design. It listens and logs only while the app window is active.
The microphone begins recording automatically once the app is launched and stops when you leave the app.
When an unknown speaker is heard, you'll be asked to enter their name and optional phone number so the app can recognize them next time.
You can also mark a captured voice as your own so it will be ignored in future sessions.
