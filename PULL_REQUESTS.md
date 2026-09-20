# OverRecipes — история разработки

Приложение с рецептами на Jetpack Compose поверх Oshxona API (данные Zira.uz).
Работа разбита на восемь pull request'ов, каждый следующий основан на предыдущем.

| # | Ветка | Что добавляет |
|---|-------|----------------|
| 1 | `feature/design-system` | Material 3 тема, доменные модели, мок-данные |
| 2 | `feature/ui-screens` | Экраны: приветствие, главный, краткий и полный рецепт |
| 3 | `feature/viewmodel-navigation` | ViewModel, UI-состояния, навигация |
| 4 | `feature/api-integration` | Retrofit + Oshxona API, обработка ошибок |
| 5 | `feature/favorites-persistence` | Избранное в DataStore |
| 6 | `feature/settings-language-theme` | Язык рецептов и тема оформления |
| 7 | `feature/local-account` | Локальный аккаунт: регистрация, вход, выход |
| 8 | `feature/video-recipes` | Видеорецепты: ExoPlayer и YouTube |

---

## PR #1 — Design system: Material 3 theme, models, mock data

**Ветка:** `feature/design-system` → `main`

Фундамент приложения до единой строчки UI.

- Цветовая схема Material 3 с тёплым оранжевым сидом, светлая и тёмная версии
  заданы отдельно, а не инверсией друг друга.
- Типографическая шкала: serif для заголовков, sans для текста.
- Шкала скруглений (`Shapes`).
- Доменные модели `Recipe`, `Ingredient`, `CookStep`, `Category` — по файлу на модель.
- Мок-данные, разбитые по категориям, и форматтеры времени, количества и отзывов.

**Как проверить:** проект собирается, поведение не меняется — это подготовка.

---

## PR #2 — UI: welcome, home, recipe preview and full recipe screens

**Ветка:** `feature/ui-screens` → `main`

Весь интерфейс на мок-данных.

- Экран приветствия с полноэкранной обложкой.
- Главный экран: поиск, фильтр категорий, карусель рекомендованного, список рецептов.
- Краткий экран рецепта: шторка поверх фото с рейтингом, описанием и кнопкой «Смотреть рецепт».
- Полный рецепт: параллакс-шапка, пересчёт ингредиентов по порциям, отмечаемые шаги.
- Избранное и профиль, нижняя навигация.
- Переиспользуемые компоненты вынесены в `ui/components` — по файлу на компонент.
- Подключены Coil (загрузка изображений) и Navigation Compose.

**Как проверить:** пройти весь путь приветствие → главный → краткий → полный рецепт.

---

## PR #3 — ViewModel layer, UI state and navigation graph

**Ветка:** `feature/viewmodel-navigation` → `main`

Разделение состояния и представления.

- `HomeUiState`, `FavoritesUiState`, `RecipeUiState`, `ProfileUiState` — по файлу на состояние.
- `HomeViewModel`, `FavoritesViewModel`, `RecipeViewModel`, `ProfileViewModel`
  отдают `StateFlow` через `stateIn(WhileSubscribed)`.
- `RecipeViewModel` получает `recipeId` из `SavedStateHandle`.
- Поиск построен на `flatMapLatest` — новый запрос отменяет предыдущий.
- Навигация вынесена в `Destinations`, `NavigationActions` и `NavGraphBuilder`-расширения.
- Ручной DI-контейнер `AppGraph` и интерфейс `RecipeRepository` с мок-реализацией.

**Как проверить:** экраны не хранят данных, состояние переживает поворот экрана.

---

## PR #4 — Oshxona API integration with Retrofit

**Ветка:** `feature/api-integration` → `main`

Переход с моков на реальный сервер.

- Retrofit + kotlinx.serialization + OkHttp с логированием.
- Эндпоинты `api/v1/recipes/`, `recipes/{id}`, `categories/`, `categories/{key}/recipes`,
  `search/` с параметрами `lang`, `page`, `per_page`, `q`, `limit`.
- В Swagger у ответов не описаны схемы, поэтому методы возвращают `JsonElement`,
  а `RecipeMapper` разбирает его защитно: перебирает варианты имён полей,
  понимает и массив, и обёртку, принимает ингредиенты строками и объектами.
- Репозиторий отдаёт `Result<T>`, `Throwable.toUserMessage()` переводит ошибки в текст.
- В состояниях появился `errorMessage`, на экранах — `ErrorState` с кнопкой «Повторить».
- Минимальная длина поискового запроса — сервер отклоняет слишком короткие с 422.

**Как проверить:** включить и выключить интернет — приложение показывает данные и ошибку с повтором.

---

## PR #5 — Persist favorites with DataStore

**Ветка:** `feature/favorites-persistence` → `main`

- `FavoritesDataStore` на DataStore Preferences, `FavoritesRepository` отдаёт `Flow<Set<Int>>`.
- Добавлен класс `OverRecipesApplication`, инициализирующий `AppGraph` контекстом.
- Лайк на любом экране мгновенно виден на остальных — источник данных один.

**Как проверить:** добавить рецепт в избранное, закрыть приложение, открыть снова.

---

## PR #6 — Settings: recipe language and app theme

**Ветка:** `feature/settings-language-theme` → `main`

- `SettingsRepository` на DataStore: язык рецептов (`ru` / `uz`) и тема (system / light / dark).
- Язык уходит в параметр `lang` всех запросов, главный экран перезагружается при смене.
- Тема применяется сразу в `MainActivity`.
- `CategoryTitles` подставляет русские названия категорий, когда сервер отдаёт только ключи.
- Диалоги выбора в профиле вместо нерабочих строк-заглушек.

**Как проверить:** сменить язык и тему в профиле, вернуться на главный экран.

---

## PR #7 — Local account: registration, login, sign out

**Ветка:** `feature/local-account` → `main`

- Локальный аккаунт в DataStore: имя, почта, случайная соль и SHA-256 от соли с паролем.
- Экраны регистрации и входа с валидацией и переключателем видимости пароля.
- `AuthState` (Loading / SignedOut / SignedIn) решает стартовый экран:
  если аккаунт есть, приветствие больше не показывается.
- Выход из аккаунта возвращает на приветствие и очищает стек навигации.

**Как проверить:** зарегистрироваться, перезапустить приложение — открывается сразу главный экран.

---

## PR #8 — Video recipes: ExoPlayer and YouTube playback

**Ветка:** `feature/video-recipes` → `main`

- `VideoUrls` определяет тип ссылки: YouTube или прямой медиафайл.
- Прямые файлы (`.mp4`, `.m3u8`, `.webm`, `.mpd`) играет Media3 ExoPlayer с полноэкранным режимом.
- YouTube встраивается через IFrame API в WebView; ошибка встраивания ловится
  JS-мостом, и вместо чёрного экрана показывается превью с переходом в YouTube.
- Play-бейдж на обложке рецепта, у которого есть видео.
- `configChanges` на активности, чтобы поворот не обрывал воспроизведение.

**Как проверить:** открыть рецепт с видео, запустить, развернуть на весь экран, повернуть устройство.
