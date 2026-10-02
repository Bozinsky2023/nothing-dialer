# nothing-dialer
A native Android dialer app inspired by Nothing Phone design, built to replace the stock Google Dialer experience.

This project now includes a custom Compose-based dialer UI with:
- keypad input and number entry
- recent call list
- favorite contacts panel
- Nothing-inspired dark aesthetic
- dial action that opens the system dialer with the selected number

Project structure:
- `app/src/main/java/com/bozinsky/nothingdialer/MainActivity.kt` — main dialer screen and behavior
- `app/src/main/java/com/bozinsky/nothingdialer/ui/theme/*` — theme and colors
- `app/src/main/AndroidManifest.xml` — app metadata and permissions

Next planned enhancements:
- Contact permission and actual contact lookup
- Real call log integration
- Call screen for active incoming/outgoing calls
- Better Nothing Phone-specific iconography and animations

Open the project in Android Studio and sync Gradle to run the app.
