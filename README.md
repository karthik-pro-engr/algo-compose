# Algo Compose

An Android application for practicing algorithmic problem-solving through interactive Jetpack Compose experiences.

Instead of keeping algorithm practice as isolated console-based exercises, this project brings algorithmic techniques into an Android application with UI state management, ViewModels, reusable components, build conventions, automated validation, and beta distribution.

---

## Highlights

- Interactive algorithm practice using Jetpack Compose
- Prefix Sum problem solving
- Two Pointers
- Variable Size Window
- Monotonic Stack
- ViewModel-driven UI state
- Separation of presentation and calculation logic
- Reusable Gradle Convention Plugins
- Automated build, test, and lint validation
- Beta build distribution through Firebase App Distribution

---

## Problem-Solving Areas

The application contains interactive examples around several algorithmic patterns.

### Prefix Sum

Examples include:

- Balanced Energy
- Fuel Tank Balancer
- Satellite Signal Balancer

### Variable Size Window

Examples include:

- Budget Stay
- Music Playlist
- Video Requests

### Monotonic Stack

Examples include:

- Box Nesting
- River Gauge
- Stock Price Watcher
- Wind Gusts

### Two Pointers

The application also contains problem-solving exercises organized around two-pointer style techniques.

The goal is to practice recognizing algorithmic patterns and implementing them inside a maintainable Android application rather than keeping algorithm practice isolated from real application structure.

---

## Architecture

The application separates Android presentation concerns from calculation logic.

A simplified flow is:

```text
User Input
    ↓
Compose UI
    ↓
ViewModel
    ↓
Domain / Calculation Logic
    ↓
Result
    ↓
UI State
    ↓
Compose UI
```

The codebase contains dedicated components for:

- UI state
- UI events
- ViewModels
- Calculation logic
- Screen configuration
- Reusable UI components

---

## Example Structure

```text
app/src/main/java/com/karthik/pro/engr/algocompose/

├── domain/
│   ├── PrefixSumWithMonotonic.kt
│   ├── energy/
│   ├── stack/
│   └── vsw/
│
├── presentation/
│   └── ui/
│
├── stack/
│   └── monotonic/
│       ├── presentation/
│       ├── ui/
│       └── viewmodel/
│
├── twopointers/
│   └── prefixsum/
│       ├── presentation/
│       └── viewmodel/
│
├── feedback/
│
└── ui/
    ├── components/
    ├── theme/
    └── util/
```

---

## Build Engineering

This project consumes reusable Gradle Convention Plugins from:

```text
https://github.com/karthik-pro-engr/build-logic
```

The application convention plugin is:

```text
karthik.pro.engr.android.application
```

The convention plugin centralizes common Android, Kotlin, and Compose build configuration instead of repeating the same configuration across modules.

---

## CI/CD

GitHub Actions is used to automate application validation and beta distribution.

### Pull Requests and Protected Branches

The CI workflow validates:

- Android build
- Unit tests
- Android lint
- Build reports
- Generated artifacts

Required checks can be used as merge gates so that changes are validated before they are merged.

### Protected Release Builds

For protected pushes and release tags, the workflow can additionally:

- Prepare release signing configuration
- Build the Beta variant
- Generate artifacts
- Generate SHA-256 checksums

### Firebase App Distribution

Version tags can trigger a separate Firebase App Distribution workflow that:

1. Builds the Beta APK.
2. Generates the APK artifact.
3. Generates a SHA-256 checksum.
4. Uploads the build to Firebase App Distribution.

Signing credentials and Firebase credentials are supplied through GitHub Actions secrets rather than committed to the repository.

---

## Testing

The repository contains JVM unit tests and Android instrumentation tests alongside the application code.

Testing is part of the application validation workflow used by CI.

---

## Tech Stack

- Kotlin
- Android SDK
- Jetpack Compose
- ViewModel
- Gradle Kotlin DSL
- Gradle Convention Plugins
- JUnit
- GitHub Actions
- Firebase App Distribution

---

## Why This Project Exists

This project combines two areas of engineering practice:

```text
Algorithmic Problem Solving
            +
Modern Android Engineering
```

The goal is to practice algorithmic techniques while implementing them inside a maintainable Android application with:

- Clear state handling
- Separation of concerns
- ViewModel-based presentation
- Reusable UI components
- Automated validation
- CI/CD
- Beta distribution

---

## Engineering Areas Demonstrated

This project provides hands-on practice with:

- Translating algorithmic patterns into application code
- Separating calculation logic from Android UI concerns
- Managing UI state with ViewModels
- Building interactive Compose screens
- Reusing Gradle build conventions
- Automating application validation
- Producing distributable beta builds

---

## Status

Active Android and algorithm-practice project.
