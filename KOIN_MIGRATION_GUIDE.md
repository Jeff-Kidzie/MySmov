## Koin Dependency Injection Migration Guide

This document explains how to use Koin for dependency injection in your app, replacing Hilt.

### What Was Changed

1. **Dependencies Updated**
   - Removed: `Hilt Android`, `Hilt Compiler`, `Hilt Navigation Compose`
   - Added: `Koin Android`, `Koin Androidx Compose`

2. **Files Modified**
   - `gradle/libs.versions.toml` - Updated dependencies
   - `app/build.gradle.kts` - Updated plugins and dependencies
   - `MainApplication.kt` - Changed from `@HiltAndroidApp` to Koin initialization
   - `MainActivity.kt` - Removed `@AndroidEntryPoint` annotation
   - `HomeViewModel.kt` - Removed `@HiltViewModel` annotation
   - `HomeScreen.kt` - Updated to use `koinViewModel()` for injection

3. **New Files Created**
   - `di/AppModule.kt` - Koin module for dependency definitions

### How to Use Koin with ViewModels and HomeScreen

#### 1. Define Dependencies in AppModule

In `di/AppModule.kt`:

```kotlin
package dev.me.mysmov.di

import dev.me.mysmov.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    viewModel { HomeViewModel() }
}
```

#### 2. Initialize Koin in Application Class

In `MainApplication.kt`:

```kotlin
package dev.me.mysmov

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import dev.me.mysmov.di.appModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()                    // Enable logging
            androidContext(this@MainApplication)  // Provide Android context
            modules(appModule)                 // Load DI modules
        }
    }
}
```

#### 3. Inject ViewModel in Composable

In `HomeScreen.kt`:

```kotlin
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    // Use viewModel here
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen")
    }
}
```

### Key Differences from Hilt

| Hilt | Koin |
|------|------|
| `@HiltAndroidApp` on Application class | `startKoin { }` in `onCreate()` |
| `@AndroidEntryPoint` on Activities | No annotation needed |
| `@HiltViewModel` on ViewModel | No annotation needed |
| `@Inject` constructor parameters | Define in module with `viewModel { }` |
| Compile-time DI | Runtime DI |

### Adding More Dependencies

To add more ViewModels or dependencies:

1. **Add ViewModel to AppModule:**
```kotlin
val appModule = module {
    viewModel { HomeViewModel() }
    viewModel { ProfileViewModel() }  // Add new ViewModel
}
```

2. **Inject in Composable:**
```kotlin
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel()) {
    // Use viewModel
}
```

### Adding Services and Repositories

To add services or repositories:

```kotlin
val appModule = module {
    // Singletons
    single { MovieRepository() }
    single { ApiService() }
    
    // ViewModels (can access services via constructor)
    viewModel { HomeViewModel(get()) }  // 'get()' injects MovieRepository
}
```

Then in ViewModel:
```kotlin
class HomeViewModel(private val repository: MovieRepository) : BaseViewModel<HomeAction, HomeViewState>(HomeViewState()) {
    // Use repository
}
```

### Android Manifest

The `MainApplication` is already configured in `AndroidManifest.xml`:
```xml
<application android:name=".MainApplication" ... >
```

No changes needed here.

### Testing with Koin

For tests, you can create separate test modules and reload Koin:

```kotlin
@Before
fun setup() {
    stopKoin()
    startKoin {
        modules(testModule)  // Load test module
    }
}

@After
fun tearDown() {
    stopKoin()
}
```

### Troubleshooting

**Issue:** `No ViewModel of class X found`
- **Solution:** Make sure the ViewModel is registered in `appModule` using `viewModel { }`

**Issue:** `Could not find parameter 'get()' in module`
- **Solution:** Ensure the dependency you're trying to inject is defined in the module

**Issue:** `NoBeanDefFoundException`
- **Solution:** Check that your modules are loaded with `modules(appModule)` in Koin initialization

