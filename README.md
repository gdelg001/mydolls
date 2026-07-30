# My Doll Collection App

An Android application for cataloguing a personal collection of fashion
dolls. Each doll is stored in a local SQLite database with its release details,
attributes, and a photo. The app supports create, read, update and delete operations.

**Student:** Geraldine Delgado
---

## Project Description

Collectors of dolls track their doll collection for easy identification and cataloging.
Details tracked include model number, the year of release, the original cost, the body
type, and the wig and eye colors. Dolls are looked up from a list by name and photo.
Keeping track of a large collection manually is difficult, especially when one lives in a 
physically limited space.

My Doll Collection App adds your collection to the phone. The collection is
shown as a scrolling list of dolls and its corresponding image. Tapping a doll opens a details 
screen with its photo and doll information. Dolls can be added, edited and removed
from within the app. Also, a photograph can be associated to a doll by using the phone's photo 
gallery. All data persists locally in SQLite, so the collection is
available offline and survives restarting the app.

---

## Features

### Collection management (CRUD)

- **Create:** Add a doll through a form that has nine attribute fields and an optional photo
- **Read:** Browse all dolls as a scrolling list, and open any doll for its full doll details
- **Update:** Edit any dolls current information by pulling up a pre-filled form
- **Delete:** Remove a doll from the details screen

### Photographs

- Add a photo for any doll by using the device's gallery
- Persistable read permission is taken on the chosen image, so photographs
  survive restarting the app
- Dolls without a photo use a placeholder icon
- Both bundled drawables and gallery photographs are handled through a shared code path

### Input validation

- All nine fields are required. Blank fields are rejected.
- Fields accept letters, digits, spaces and the characters `. , / -`, which
  permits real values such as `No.F-588`, `10,000 JPY` and `Jun Planning` , while
  rejecting invalid symbols.
- Failures are reported on the offending field via `setError`. Every
  invalid field is flagged at once.
- The year field uses a numeric keyboard

### Exception handling

- Database inserts, updates, and deletes are wrapped in `try`/`catch`
- A failed save reports the problem with a `Toast` and leaves the form open, so
  the user does not lose what they typed.
- Failure to persist a photo's read permission is caught separately and does
  not prevent the doll from being saved.

### Sound and animation

- Looping background music on the opening screen
- A distinct sparkle sound effect for each of the five starter dolls when a doll button is clicked. 
- A fallback sparkle sound when clicking the button for all dolls added later
- Sound playback waits until all audio files have finished decoding
- A bounce animation on the opening screen's button, and a scale animation when a
  doll's photograph is tapped

---

## How to Use the App

1. **Opening screen:** Background music begins playing. Tap **Doll Collection**.
2. **Collections list:** Every doll in the database is listed alphabetically by name with a
   thumbnail photo. Scroll if the collection is longer than the screen. Tap a doll to
   open it.
3. **Detail screen:** Shows the doll's photo and all of the doll's attributes (ex: model, company, 
   brand, year, cost, body type, wig color and eye color). Tap
   the doll image to hear music and see the photo animate.
4. **Adding a doll:** Tap the pink **+** button at the bottom right of the
   collections list screen. Fill in all nine fields. Optionally tap **Choose Photo** to
   attach an image. Tap **Save**. **Cancel** discards the entry.
5. **Editing a doll:** Open the doll's details. Tap **Edit**. The form opens pre-filled.
   Edit any fields and tap **Save**. You are returned to the detail screen
   with the new information shown.
6. **Deleting a doll:** Open the doll's details and tap **Delete**. You are returned to
   the collections list screen with the doll removed. 

---

## Technologies Used

| Area | Technology |
|---|---|
| Language | Java 8 |
| Platform | Android, minimum SDK 24, compiled and targeted against SDK 34 |
| Database | SQLite, accessed through Room 2.4.0 |
| UI | Android Views with XML layouts — `ConstraintLayout` 2.1.4, `GridLayout` 1.0.0, `ScrollView`, `LinearLayout` |
| Components | AppCompat 1.6.1, Material Components 1.12.0, AndroidX Activity 1.9.0 |
| Theming | Material 3 dark theme with a custom pink and purple palette |
| Audio | `SoundPool` for short effects, `MediaPlayer` for background music |
| Animation | View animations (`res/anim`) and property animators (`res/animator`) |
| Photo selection | AndroidX Activity Result API with the `OpenDocument` contract |
| Build | Gradle with Kotlin DSL and a version catalog, Android Gradle Plugin 8.13.2 |
| Tested on | Pixel 6 emulator, API 32 |

---

## Installation and Running

### Requirements

- Android Studio (Ladybug or newer recommended)
- Android SDK Platform 34
- An emulator or physical device running Android 7.0 (API 24) or later

### Steps

1. Clone the repository:

   ```
   git clone https://github.com/gdelg001/mydolls.git
   ```

2. Open Android Studio. Choose **Open**. Select the cloned
   `MyDollCollectionApp` folder.

3. Wait for Gradle to sync. All dependencies resolve from Maven Central and
   Google's Maven repository. 

4. Create an emulator using **Tools → Device Manager**, or connect a physical
   device with USB debugging enabled.

5. Press **Run** or Shift+F10.

On first launch, the app creates `doll.db` and seeds it with five starter dolls.
Seeding only happens when the table is empty, so edits are never overwritten 
on future launches.

### Note on `local.properties`

This file is intentionally excluded from version control because it contains an
absolute path to the SDK on one particular machine. Android Studio regenerates it
automatically when the project is opened.

---

## Project Structure

```
app/src/main/
├── java/edu/delgado/mydollcollectionapp/
│   ├── MainActivity.java           opening screen, background music
│   ├── DollsListActivity.java      scrolling collection list, add button
│   ├── DollsDetailsActivity.java   one doll's details, edit and delete
│   ├── DollEditActivity.java       add/edit form, validation, photo picker
│   ├── DollDataModel.java          Room entity — one doll
│   ├── DollDao.java                Room DAO — the SQL surface
│   ├── DollDatabase.java           Room database definition
│   ├── ManageDollsRepo.java        singleton repository, seed data
│   ├── DollImageHelper.java        resolves drawable names and photo URIs
│   └── SoundEffects.java           singleton SoundPool wrapper
└── res/
    ├── layout/                     four activity layouts plus one list row
    ├── drawable/                   doll artwork, backgrounds, placeholder icon
    ├── raw/                        background music and sound effects
    ├── anim/ and animator/         button bounce and image scale animations
    └── values/                     colours, strings, theme
```

### Design notes

- **`ManageDollsRepo`** is a singleton and the only class that touches the DAO.
  Activities depend on the repository rather than on Room directly, which keeps
  database access in one place.
- **`DollImageHelper`** is a stateless utility class. A doll's image may
  be either a bundled drawable name or a `content://` URI from the gallery. The
  decision about how to load an image exists in one method that all three screens call.
- **`SoundEffects`** is a singleton, so the five audio files are decoded once for
  the whole application.
- **The collection list is data-driven.** One row layout, `item_doll.xml`, is
  inflated once per doll, so the list reflects what is in the database.

---

## Database

A single SQLite database, `doll.db`. Schema version 2, holding one table.

### Table `DollDataModel`

| Column | Type | Notes |
|---|---|---|
| `doll_id` | INTEGER | Primary key, auto-generated |
| `doll_name` | TEXT | Not null |
| `doll_year` | TEXT | Year of release |
| `doll_cost` | TEXT | Original cost, stored as text to keep the currency, e.g. `10,000 JPY` |
| `doll_model` | TEXT | Manufacturer's model number |
| `doll_body` | TEXT | Body type |
| `doll_wig` | TEXT | Wig color |
| `eyes` | TEXT | Eye color |
| `doll_company` | TEXT | Manufacturer |
| `doll_brand` | TEXT | Product line |
| `doll_image` | TEXT | A bundled drawable name, or a `content://` URI |

Rows are returned ordered by `doll_name`, case-insensitively.

---

## Limitations and Future Improvements

- Background music plays only on the opening screen. Moving it to an
  application-scoped component would let it continue across all screens.
- Deleting a doll takes effect immediately. A confirmation prompt is to be added in future updates.
- Cost and year are stored as text rather than numbers, so they cannot be filtered numerically.
- The list is ordered alphabetically by default. No search or sort options are currently available.
- Database access runs on the main thread. For a larger collection, access would need moving to 
  a background thread.