# Badger
![Badger banner](https://github.com/lpvlnc/badger/blob/main/res/images/main_menu_background.png)

# 🕹️ Java 2D Top-Down Game

 This project began as a final test for the **Programming C** discipline at Faculdade Dom Bosco de Porto Alegre, to explore team work, object-oriented programming, and project structure.
 We choose to make a game because at the time i was starting my studies in game development, so i had a base for a game engine in Java, presented it to my colleagues and they liked the idea.
 After the version was presented in class, **additional improvements** were made to enhance responsiveness and gameplay experience.

---

## 🎮 About the Game

 It's a simple 2D **top-down game** developed in **Java**, featuring two unique levels. The main objective is to **collect items while avoiding dangerous enemies**.

  - In **Level 1**, the player must collect **5 chocolates**. Once collected, a **pyramid door** opens, allowing progression to the next stage.
  - In **Level 2**, the player must collect **10 ancient parchments** to complete the game.

---

## 🧠 Game Features

- **Three enemy types** with different behaviors:
  - 🦂 **Scorpion**: Moves vertically, deals **2 hearts** of damage.
  - 🐍 **Snake**: Moves horizontally in a zigzag pattern, **instantly kills** the player.
  - 🪲 **Scarab**: Uses **A* pathfinding** to chase the player, deals **1 heart** of damage.

- **Player Abilities**:
  - 🏃‍♂️ **Running (SHIFT)** consumes stamina that regenerates when not running.
  - ❤️ **5 health points** (hearts).
  - 🧪 **Steroid power-up (CTRL)**: Grants invincibility and max stamina temporarily, followed by a weakened state (1 heart and slower movement).
  - 👑 **Crown power-up (SPACE)**: Temporarily reveals the map and allows the player to **see through walls**.
  - 📱 **Invisibility Gadget (E)**: Temporarily makes the player invisible to enemies (but still vulnerable to contact).

- **Health pickups**: Regain hearts after taking damage.
- **Responsive resolution**: Maintains aspect ratio across any screen size.
- **Some development features**:
  - Own Game Engine.
  - Spritesheet used to load sprites and animations.
  - Map constructed using tiles.
  - Levels are built using **bitmap images**.
  - Some tiles/sprites are randomized for **slightly different experiences** each time.
  - A* algorithm implementation.
- **Integrated audio system**: Sound effects and music enhance the immersive experience.

---

## 🏫 Academic Context
 This project was created as part of our final test for the **Programming C** course. We opted for Java to better utilize object-oriented programming concepts that we have learn during our classes.

---

## 🔄 Post-Submission Updates
  - Improved screen resolution scaling.
  - Bug fixes and minor performance enhancements.
  - UI polish and improved input responsiveness.
  - Changed enemies to animals/insects that exists in Egypt.

---

## 👨‍💻 Contributors
  - Leonardo Pinheiro Valença
  - Marcos Gabriel Koslovski
  - Thaiane Ribeiro

---

## 📦 How to Run

Download the .exe release, run and play!
or
Build locally:
 1. Make sure you have Java 8 or higher installed
 2. Clone this repository
 3. Use any IDE that can run the project (It was developed using IntelliJ)

If you have any trouble running the project get in touch with me through my LinkedIn page (on profile)

---

![Badger logo](https://github.com/lpvlnc/badger/blob/main/res/images/icon-big.png)
