# Smart Pantry Manager

**Module:** Mobile App Development 700  
**Technology:** Java, Android Studio, XML, SQLite, RecyclerView and Android Intents.

## Application purpose
Smart Pantry Manager helps users reduce food waste by tracking ingredients available at home and suggesting recipes that can be prepared strictly from those ingredients.

## Assignment requirements implemented
- Pantry Create, Read, Update and Delete (CRUD)
- SQLite persistence between app sessions
- 18 pre-loaded recipes stored in SQLite on first database creation
- RecyclerView with custom adapters for pantry and recipe lists
- Pantry List, Add/Edit Ingredient, Suggested Recipes, Recipe Detail and Settings screens
- Android Intents for screen-to-screen navigation and data passing
- Toolbar navigation menu plus screen buttons
- Input validation for required fields, quantity, units and expiry-date format
- Strict recipe matching: every recipe ingredient must be present in sufficient quantity
- Basic ingredient-name normalisation for singular/plural differences
- Unit conversion for g/kg and ml/l, with common piece/can aliases
- Clear zero-match feedback
- No Maps, GPS, location services, payments or financial transactions

## Database choice
SQLite was selected because the application is local, does not require user accounts or cloud synchronisation, and the assignment explicitly supports SQLite for on-device persistent storage.

## Running the project
1. Open the project folder in Android Studio.
2. Allow Gradle to sync.
3. Create or select an Android emulator (or connect a test device).
4. Run the `app` configuration.
5. Test pantry CRUD, persistence, strict recipe matching, recipe details and settings.

## GitHub requirement
The assignment requires a public GitHub repository with at least 10 genuine incremental commits. The commit history must reflect the actual development process. Do not create all commits immediately before submission.

## Visual Branding
The application uses the supplied fresh-ingredients photograph (`pantry_background.jpg`) as a subtle background across the main application screens. The supplied `Recipes-Banner.jpg` is included as the Smart Pantry Manager in-app recipe logo/header. These assets are stored in `app/src/main/res/drawable/`.
