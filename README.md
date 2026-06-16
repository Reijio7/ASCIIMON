# ASCIIMON

> Java-based ASCII battle game inspired by Pokémon

*WORKS ONLY ON UNIX!!!!!!!!*

---

## BUILD STATUS

![Java](https://img.shields.io/badge/Java-8-blue)
![Maven](https://img.shields.io/badge/Build-Maven-orange)
![Status](https://img.shields.io/badge/Project-Learning-lightgrey)

---

## OVERVIEW

ASCIIMON is a terminal-based battle game written in Java.  
It uses ASCII rendering via Lanterna and features a turn-based combat system with type advantages, music, and persistent settings.

---

## FEATURES

- Turn-based battle system
- Type effectiveness system
- ASCII graphics rendered in terminal
- Starter Pokémon selection
- Random enemy 
- Music system (menu + intro)
- Settings with persistence (settings.txt)

---

## TECH STACK

- Java 8
- Maven
- Lanterna (terminal UI)
- JavaZoom (MP3 playback)
- JavaCV / FFmpeg (video rendering)

---

## CONTROLS

### Main Menu

Arrow Up / Down - Navigate
Enter - Select


### Battle

A - Basic attack
S - Special attack


---

## COMBAT SYSTEM

Each Pokemon has:


HP
Attack
Type
ASCII sprite


### Type effectiveness


Fire > Grass
Water > Fire
Grass > Water
Electric > Water


### Damage multipliers


Super effective x1.25
Not very effective x0.75
Normal x1.0


---

## AUDIO SYSTEM

- Menu music plays if not muted
- Intro music + video on first launch
- Controlled via Settings system

---

## INTRO SYSTEM

- ASCII video renderer using FFmpeg + JavaCV
- Frame-based rendering in terminal
- Skippable via input (Enter / Space)

---

# BUILD AND RUN

### Compile project

mvn compile


### Run project

If configured with exec plugin:

mvn exec:java


Otherwise:

Run Main.java from your IDE
