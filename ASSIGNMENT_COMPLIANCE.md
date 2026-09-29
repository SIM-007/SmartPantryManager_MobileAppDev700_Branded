# Smart Pantry Manager – Assignment Compliance Checklist

This checklist maps the current project implementation to the Mobile App Development 700 practical assignment. Items that require the student's own evidence (GitHub history, screenshots, narration and signature) are intentionally marked as student action items.

| Assignment requirement | Current implementation | Status |
|---|---|---|
| Java + Android Studio | Java Activities, XML layouts, Android project | Implemented |
| Pantry management CRUD | `DatabaseHelper` insert/update/delete/read + Add/Edit screen + PantryAdapter | Implemented |
| Pantry list bound to database | `RecyclerView` + `PantryAdapter` + SQLite | Implemented |
| 15–20 seeded recipes | 18 recipes inserted when SQLite database is first created | Implemented |
| Suggested Recipes | `SuggestedRecipesActivity` | Implemented |
| Strict matching | Every required ingredient must exist and have sufficient quantity | Implemented |
| Partial matches excluded | Matching fails immediately when any required ingredient/quantity is missing | Implemented |
| Basic messy-name handling | Lower-case, punctuation removal and simple singular/plural normalisation | Implemented |
| Unit differences | g/kg and ml/l conversion; common pieces/cans aliases | Implemented |
| Recipe details | `RecipeDetailActivity` displays ingredients and preparation steps | Implemented |
| Settings/profile screen | `SettingsActivity` with persistent expiry-date display preference | Implemented |
| Zero-match feedback | “No recipes match your pantry yet…” message | Implemented |
| Minimum five screens | Pantry List, Add/Edit Ingredient, Suggested Recipes, Recipe Detail, Settings | Implemented |
| Intents | Main screen opens other Activities; recipe data passed through Intent extras | Implemented |
| RecyclerView + custom Adapter | `PantryAdapter` and `RecipeAdapter` | Implemented |
| Working navigation element | Android toolbar overflow menu with Add Ingredient, Suggested Recipes and Settings | Implemented |
| Input validation | Required fields, positive quantity, supported units and YYYY-MM-DD expiry format | Implemented |
| Persistent database | SQLite via `SQLiteOpenHelper` | Implemented |
| Data survives app restart | SQLite database is stored on-device | Implemented |
| No Maps/GPS/location features | No location permissions, Maps SDK or GPS functionality | Implemented |
| No payment processing | No payment or financial transaction functionality | Implemented |
| README | Project README includes description, database choice and setup instructions | Implemented |
| Public GitHub repository | Must be created/maintained by student | Student action required |
| Minimum 10 genuine incremental commits | Must be created during actual development | Student action required |
| 5–7 minute video | Must be recorded by student with voice narration | Student action required |
| Screenshots of every screen/function | Must be captured from the running app | Student action required |
| Written report | Must be completed with actual screenshots, diagrams, code snippets and challenges | Student action required |
| Signed declaration | Must be signed by student | Student action required |
| Final ZIP under 50 MB | Source + video + report must be compressed appropriately | Student action required |

## Suggested genuine GitHub development sequence

Use these as development milestones. Only commit a step after you have actually completed and tested it.

1. `Create Smart Pantry Manager Android project`
2. `Add SQLite database and pantry ingredient model`
3. `Implement pantry create and read operations`
4. `Add RecyclerView custom pantry adapter`
5. `Implement pantry edit and delete operations`
6. `Seed recipe collection in SQLite`
7. `Implement strict recipe matching algorithm`
8. `Add suggested recipes and recipe detail screens`
9. `Add settings and toolbar navigation`
10. `Improve validation and unit/name normalisation`
11. `Test persistence and strict matching edge cases`
12. `Update README and assignment evidence`

Do not create these commits artificially in one batch. The assignment specifically states that GitHub history is evidence of the development process.

## Branding update
- Added supplied fresh-ingredients image as the application background on the core screens.
- Added supplied Recipes banner as the in-app logo/header.
- Added a translucent content overlay so text remains readable over the food photograph.
