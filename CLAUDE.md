# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./gradlew assembleDebug

# Lint (ktlint)
./gradlew ktlintCheck
./gradlew ktlintFormat   # auto-fix formatting

# Tests
./gradlew test                         # unit tests
./gradlew connectedAndroidTest         # instrumented tests
./gradlew testDebugUnitTest --tests "dev.me.mysmov.ExampleUnitTest"  # single test
```

## Architecture

**Clean Architecture + MVI** across three layers:

- `domain/` — pure Kotlin: models, repository interfaces, use cases
- `data/` — implementations: Retrofit services, Room DAOs, data sources, `MovieRepositoryImpl`
- `feature/` — Compose screens + ViewModels

### MVI via `BaseViewModel`

Every screen follows: **Action → Event → State** (with one-shot **Effect** for navigation).

```
BaseViewModel<Action, Event, Effect, ViewState>
  ├── onAction(action)       → handleOnAction()
  ├── sendEvent(event)       → reduce(oldState, event) → viewState (StateFlow)
  └── sendEffect(effect)     → effect (SharedFlow, consumed once)
```

Each feature screen has an `Intention.kt` file defining all four types (e.g. `HomeIntention.kt` contains `HomeAction`, `HomeViewState`, `HomeEvent`, `HomeEffect`).

### Dependency Injection (Koin)

Four modules loaded in `MainApplication`:
- `networkModule` — Retrofit, OkHttpClient, `MovieService`, (add new API services here)
- `dataSourceModule` — Room DB, `RemoteMovieDataSource`, `LocalMovieDataSource`, `MovieRepositoryImpl`
- `useCaseModule` — use case singletons, all injected with `named(ModuleConstant.MOVIE_REPO)`
- `appModule` — ViewModels via `viewModel { }`

`MovieRepositoryImpl` delegates to `RemoteMovieDataSource` via Kotlin's `by` delegation. To add caching, implement methods directly in `MovieRepositoryImpl`.

### Network Layer

- `callApi { }` wraps Retrofit calls into `CallResult<T>` (Success / Error / Exception)
- `.transform { }` maps `CallResult` data without unwrapping
- TMDB base URL: `https://api.themoviedb.org/3/`, image base: `AppConstant.BASE_URL_IMAGE`
- Auth is set via Bearer token in `networkModule` header interceptor

### Categories

- `MovieCategory` (sealed class): `Popular`, `NowPlaying`, `Upcoming`, `TopRated` — maps to `/movie/{category}` path segments
- `TvCategory` (sealed class): `Popular`, `AiringToday`, `OnTheAir`, `TopRated` — maps to `/tv/{category}` path segments
- Both implement `MediaCategory` interface with a `name: String` property

### Data Flow Example (Home screen movies)

```
HomeScreen → HomeViewModel.onAction(InitPage)
  → GetMovieByCategoryUseCase.execute(param)
    → MovieRepository.getMoviesByCategory()
      → RemoteMovieDataSource → MovieService (Retrofit)
  → sendEvent(ShowPopularMovies) → reduce() updates HomeViewState
  → HomeScreen recomposes via viewState.collectAsState()
```

## Key Patterns

- DTOs live in `data/model/dto/` and have extension functions (`toCast()`, `toVideoTrailer()`) for mapping to domain models
- `MediaItem` is the shared domain model for both movies and TV shows
- `TvService.getTvByCategory()` exists but `RemoteMovieDataSource.getTvByCategory()` is a `TODO()` stub — the TV integration is incomplete
- `TvService` is defined but not yet registered in `networkModule` (see the comment `// can add another api service here`)
