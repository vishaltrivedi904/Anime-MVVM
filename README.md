# Anime Hub 🎬

A modern, production-ready Android application for browsing top anime using the Jikan API. Built with Clean Architecture, Jetpack Compose, and comprehensive security features.

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

- 📱 **Browse Top Anime** - Infinite scroll with Paging 3
- 🎬 **Watch Trailers** - In-app trailer player
- 📖 **Detailed Info** - Synopsis, genres, studios, ratings
- 💾 **Offline Support** - Room database caching
- 🌍 **Localization** - English, Hindi, Japanese
- 🎨 **Dynamic Theming** - Material 3 with light/dark mode
- 🔒 **Security Hardened** - SSL pinning, encrypted storage

## Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | Clean Architecture, MVVM |
| DI | Hilt |
| Networking | Retrofit, OkHttp, Gson |
| Database | Room |
| Pagination | Paging 3 |
| Images | Coil |
| Navigation | Navigation Compose |
| Security | EncryptedSharedPreferences, CertificatePinner |
| Testing | JUnit, MockK, Turbine |

## Security Features

| Feature | Implementation |
|---------|----------------|
| SSL Pinning | Real SHA-256 hash from api.jikan.moe |
| HTTPS Only | Cleartext traffic disabled |
| Root Detection | Checks for su binaries |
| Emulator Detection | Build fingerprint analysis |
| Encrypted Storage | EncryptedSharedPreferences |
| Code Obfuscation | ProGuard/R8 enabled |

## API

Uses [Jikan API v4](https://docs.api.jikan.moe/) - Unofficial MyAnimeList API.

**Endpoints used:**
- `GET /top/anime` - Top anime list
- `GET /anime/{id}` - Anime details

## How to Run

```bash
# Clone the repository
git clone https://github.com/yourusername/anime-hub.git
cd anime-hub

# Build and install
./gradlew installDebug
```

## Generate SSL Pin Hash

To update the SSL certificate hash:

```bash
# Extract public key
openssl s_client -connect api.jikan.moe:443 -servername api.jikan.moe </dev/null \
| openssl x509 -pubkey -noout > public_key.pem

# Generate SHA-256 hash
openssl pkey -in public_key.pem -pubin -outform der \
| openssl dgst -sha256 -binary \
| openssl enc -base64
```

Current hash: `sha256/Sb8dCtDxF3fMIdaM+UeXIdTiKHZvztzF2jjszZEcQd4=`

## Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## Project Structure

```
app/src/main/java/.../
├── core/
│   ├── network/        # NetworkErrorHandler
│   └── security/       # SecurityManager, SecureStorage
├── data/
│   ├── local/          # Room database, DAO
│   ├── model/          # DTOs
│   ├── remote/         # Retrofit API
│   └── repository/     # Repository implementation
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

- Android Studio Hedgehog or later
- JDK 17
- Min SDK 24
- Target SDK 35

## License

MIT License

---

## Contact

- **GitHub:** [yourusername](https://github.com/yourusername)
- **LinkedIn:** [yourname](https://linkedin.com/in/yourname)
- **Email:** your.email@example.com

---

Made with ❤️ for learning and professional development.
