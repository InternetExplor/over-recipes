#!/usr/bin/env bash
# Публикует историю на GitHub: main из первого коммита, затем 8 pull request'ов по порядку.
# Требуется gh (GitHub CLI) с выполненным `gh auth login`.
set -euo pipefail

REPO_NAME="${1:-over-recipes}"
VISIBILITY="${2:---public}"

BRANCHES=(
  "feature/design-system|Design system: Material 3 theme, models, mock data"
  "feature/ui-screens|UI: welcome, home, recipe preview and full recipe screens"
  "feature/viewmodel-navigation|ViewModel layer, UI state and navigation graph"
  "feature/api-integration|Oshxona API integration with Retrofit"
  "feature/favorites-persistence|Persist favorites with DataStore"
  "feature/settings-language-theme|Settings: recipe language and app theme"
  "feature/local-account|Local account: registration, login, sign out"
  "feature/video-recipes|Video recipes: ExoPlayer and YouTube playback"
)

BOOTSTRAP="$(git rev-list --max-parents=0 HEAD)"

gh repo create "$REPO_NAME" $VISIBILITY --source=. --remote=origin --disable-wiki

# main начинается с пустого проекта, чтобы каждый PR был реальным
git push origin "$BOOTSTRAP:refs/heads/main"

for entry in "${BRANCHES[@]}"; do
  branch="${entry%%|*}"
  title="${entry##*|}"
  git push origin "$branch"
  gh pr create --base main --head "$branch" --title "$title" --body-file PULL_REQUESTS.md
  gh pr merge "$branch" --merge --delete-branch=false
done

git fetch origin
git checkout main
git reset --hard origin/main

echo "Готово: 8 pull request'ов созданы и влиты в main."
