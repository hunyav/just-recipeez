# JustRecipeez

Private, offline-first Android recipe app built with Kotlin + Jetpack Compose + Room.

## V1 scope
- Recipe list screen
- Recipe detail screen
- Add/edit recipe screen
- Local Room persistence (no backend, no auth, no cloud sync)
- Search by title / description / ingredients
- Favorite toggle
- CRUD operations
- Optional recipe image (gallery pick or camera capture)
- Import latest backup from server
- Export local recipes to server backup

## Tech stack
- Kotlin
- Jetpack Compose + Material 3
- Room
- ViewModel + StateFlow
- Compose Navigation

## Project structure
- `domain/model`: domain-level recipe model
- `data/local`: Room database, DAO, and entities
- `data/repository`: thin repository layer
- `feature/list`: recipe list UI + state + ViewModel
- `feature/detail`: recipe detail UI + state + ViewModel
- `feature/edit`: add/edit UI + state + ViewModel
- `navigation`: app navigation routes + NavHost
- `core/ui/theme`: Compose theme

## Notes
- This repository currently contains an initial V1 skeleton intended to compile in Android Studio.
- App icons and image loading are intentionally deferred for V1 scope discipline.


## Environment variables
- `BASE_URL`: backup API base URL (e.g. `https://example.com`)
- `BACKUP_API_TOKEN`: bearer token for backup API authorization

Both values are read at build time and exposed to app code via `BuildConfig`.
