# Hexadecimal and Decimal Calculator Projects

This repository contains two Android applications developed to practice numeral system conversions, application state management, and creating operation histories.

## Project Structure

The repository is divided into two independent sub-projects:

### 1. Virtual-Hex-Keyboard
A virtual Numpad-style keyboard for Android, capable of recording and displaying a history of pressed keys in hexadecimal format (0-9, A-F).
* **Features:**
  * Intuitive user interface.
  * Recording and displaying input history in real-time.
  * Deletion (Back) and validation (OK) functionalities.

### 2. Hex-Dec-Calculator
A functional Android calculator that allows performing arithmetic operations and instant conversion between base 10 (Decimal) and base 16 (Hexadecimal).
* **Features:**
  * Performing basic calculations (+, -, *).
  * Dynamic switching between decimal (10) and hexadecimal (16) display.
  * Detailed history of performed operations (e.g., `125 + 45 = 170`, `select base 16 -> 3F`).

## Technologies Used

* **Programming Language:** Kotlin
* **Platform / Framework:** Android SDK
* **Development Environment (IDE):** Android Studio

## How to Run

1. Clone this repository to your local machine: `git clone https://github.com/vbaltaru/Hex-Decimal-Calculator.git`
2. Open Android Studio and select "Open an existing Project".
3. Navigate to the cloned repository and open the specific project folder (Virtual-Hex-Keyboard or Hex-Dec-Calculator).
4. Wait for the Gradle sync to complete.
5. Run the application on an Android Emulator or a physical Android device connected via USB/Wi-Fi.
