# Как устроена история

Ветки образуют цепочку: каждая следующая создана от предыдущей, поэтому
pull request показывает ровно свои изменения, без чужих коммитов.

```
main (bootstrap)
 └─ feature/design-system
     └─ feature/ui-screens
         └─ feature/viewmodel-navigation
             └─ feature/api-integration
                 └─ feature/favorites-persistence
                     └─ feature/settings-language-theme
                         └─ feature/local-account
                             └─ feature/video-recipes
```

Pull request'ы вливаются в `main` строго по порядку: #1, #2, … #8.
Описания всех восьми — в `PULL_REQUESTS.md`.
