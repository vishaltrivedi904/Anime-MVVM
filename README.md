# Anime Hub 🎬

A modern Android application for browsing top anime using the Jikan API. Built with Clean Architecture, Jetpack Compose, and security best practices.

> **Educational Project** - This project is for learning and educational purposes only.

## Screenshots

| Light Theme | Dark Theme |
|-------------|------------|
| ![Home Light](screenshots/home_light.png) | ![Home Dark](screenshots/home_dark.png) |
| ![Detail Light](screenshots/detail_light.png) | ![Detail Dark](screenshots/detail_dark.png) |

## Architecture

This project follows **Clean Architecture** with three distinct layers:

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │
│  • Jetpack Compose Screens              │
│  • ViewModels with StateFlow            │
│  • Navigation Compose                   │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│             Domain Layer                │
│  • Domain Models                        │
│  • Repository Interfaces                │
│  • UseCases                             │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│              Data Layer                 │
│  • Repository Implementation            │
│  • Retrofit API Service                 │
│  • Room Database                        │
│  • DTOs & Mappers                       │
└─────────────────────────────────────────┘
```

## Features

- 📱 Browse Top Anime with infinite scroll
- 🎬 Watch Trailers in-app
- 📖 Detailed Info with synopsis, genres, studios
- 💾 Offline Support via Room caching
- 🌍 Multi-language (English, Hindi, Japanese)
- 🎨 Light/Dark Theme toggle
- 🔒 Security features (SSL pinning, encrypted storage)

## Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | Clean Architecture, MVVM |
| DI | Hilt |
| Networking | Retrofit, OkHttp |
| Database | Room |
| Pagination | Paging 3 |
| Images | Coil |
| Navigation | Navigation Compose |

## API

Uses [Jikan API v4](https://docs.api.jikan.moe/) - Unofficial MyAnimeList API.

## How to Run

```bash
git clone https://github.com/yourusername/anime-hub.git
cd anime-hub
./gradlew installDebug
```

## Project Structure

```
app/src/main/java/.../
├── core/
│   ├── network/        # Error handling
│   ├── security/       # Security utilities
│   └── settings/       # Theme & language
├── data/
│   ├── local/          # Room database
│   ├── model/          # DTOs
│   ├── remote/         # Retrofit API
│   └── repository/     # Repository impl
├── di/                 # Hilt modules
├── domain/
│   ├── model/          # Domain models
│   ├── repository/     # Interfaces
│   └── usecase/        # Business logic
└── presentation/
    ├── components/     # Reusable UI
    ├── navigation/     # Nav graph
    ├── screen/         # Composable screens
    ├── theme/          # Material 3 theme
    └── viewmodel/      # ViewModels
```

## Requirements

- Android Studio Hedgehog+
- JDK 17
- Min SDK 24, Target SDK 35

---

*For educational and learning purposes only.*
