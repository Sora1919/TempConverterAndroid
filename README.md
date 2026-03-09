# Temperature Converter (Android - Kotlin)

A simple Android application built with **Kotlin** in **Android Studio** that converts temperature values between **Celsius (°C)**, **Fahrenheit (°F)**, and **Kelvin (K)**.  
This project was developed for **Mobile Development Coursework 2 – Question 1**.

---

## Features

- ✅ Convert between **Celsius**, **Fahrenheit**, and **Kelvin**
- ✅ Choose **From** and **To** scales using dropdown menus (Spinners)
- ✅ Input validation:
  - Shows error if input is not a valid number
  - Shows error if value is out of a reasonable range
  - Prevents invalid Kelvin values (below 0)
- ✅ User-friendly UI using Material components
- ✅ **Swap** button (↔) to swap input/output scales quickly
- ✅ **Clear** button to reset input, result, and selections

---

## Screens / Flow

1. Enter temperature value  
2. Select **From** scale  
3. Select **To** scale  
4. Tap **Convert** to see the result  
5. Optional:
   - Tap **↔ Swap** to switch scales
   - Tap **Clear** to reset

---

## Temperature Conversion Formulas

- **C → F**: `(C × 9/5) + 32`
- **F → C**: `(F − 32) × 5/9`
- **C → K**: `C + 273.15`
- **K → C**: `K − 273.15`

The app converts:
1) From selected scale → **Celsius**  
2) Celsius → selected output scale

---

## Tech Stack

- **Language:** Kotlin  
- **IDE:** Android Studio  
- **UI:** XML + Material Design Components  
- **Architecture:** Simple Activity-based (beginner-friendly)

---

## How to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/<your-username>/<your-repo-name>.git
  
2. Open the project in Android Studio
3. Wait for Gradle Sync to finish
4. Run on an Android Emulator or Physical Device
