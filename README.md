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
- Tag recipes and include tags in search
- Bottom navigation with Recipes and Settings tabs
- Typewriter-like typography with parchment-inspired light theme

## Data notes
- `ingredients` and `instructions` are stored as **lists of strings**.
- In Add/Edit, users enter them as multiline text (one line per item).
- In Detail, each ingredient and instruction is shown with a checkbox for progress tracking.

## Backup payload shape (export)
```json
{
  "schemaVersion": 1,
  "source": "just-recipeez",
  "deviceName": "earth-witch",
  "payload": {
    "recipes": [
      {
        "id": "1",
        "title": "...",
        "description": "...",
        "tags": ["..."],
        "ingredients": ["..."],
        "instructions": ["..."],
        "notes": "...",
        "prepMinutes": 5,
        "cookMinutes": 20,
        "servings": 12,
        "favorite": true,
        "imageUri": "",
        "createdUtc": 0,
        "updatedUtc": 0
      }
    ]
  }
}
```

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

## Environment variables
- `BASE_URL`: backup API base URL (e.g. `https://example.com`)
- `BACKUP_API_TOKEN`: bearer token for backup API authorization

Both values are read at build time and exposed to app code via `BuildConfig`.
