# IFC Calendar (International Fixed Calendar)

An Android application built with Jetpack Compose that implements a viewer for the **International Fixed Calendar (IFC)**.

## What is the IFC?
The International Fixed Calendar (also known as the Cotsworth plan or the Eastman plan) is a proposal for calendar reform. It consists of:
- **13 months** of exactly **28 days** each.
- Every month starts on a Sunday and ends on a Saturday.
- An extra month, **Sol**, is inserted between June and July.
- One extra day at the end of the year (Year Day) that belongs to no month or week.
- A Leap Day every four years.

## Features
- **Gregorian to IFC Conversion**: Automatic conversion of the current system date.
- **Interactive Calendar**: Navigate through all 13 months.
- **Modern UI**: Built entirely with Jetpack Compose and Material 3.
- **Dynamic Selection**: Select days to see their details.

## Technical Stack
- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Design System**: Material 3
- **Date Handling**: `kotlinx-datetime`
- **Architecture**: MVVM (ViewModel + StateFlow)

## Project Structure
- `dev.enseor.ifccalendar.logic`: Contains `IfcDate`, the core logic for calendar conversion.
- `dev.enseor.ifccalendar.ui`: UI related components, themes, and the `CalendarViewModel`.
- `dev.enseor.ifccalendar.ui.components`: Reusable Compose components like `MonthGrid`.

## Getting Started
### Prerequisites
- Android Studio Ladybug or newer.
- Android SDK 36 (target).
- Minimum Android SDK 24.

### Building
1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle.
4. Run on an emulator or physical device.

---
*Developed by Enseor*
