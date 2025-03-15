# Dev's Calculator

A Java Swing calculator with a modern UI, supporting various math functions. It uses the JavaScript engine (Nashorn) for expression evaluation, allowing features like exponentiation, trigonometric functions, logarithms, etc.

## Features
- Multi-line display
- Rounded buttons, dark theme
- Basic operators: `+`, `-`, `×`, `÷`, `=`
- Advanced operators: `^`, `1/e`, `log`, `ln`, factorial (`x!`), random (`Rand`)
- Parentheses: `(`, `)`
- Trig functions: `sin`, `cos`
- Constants: `π`, `e`
- Sign toggle: `±`
- Clear: `AC`

## Requirements
- **Java 14+**  
- **Maven** (if building/running via Maven)

## Build & Run
1. mvn clean compile exec:java
   mvn exec:java
2. If you're on Java 15+, Maven will download the Nashorn dependency automatically.

### Using Maven
1. Clone the repo:
   ```sh
   git clone https://github.com/your-username/devs-calculator.git
   cd devs-calculator
2. Build & run:

## Project Structure
devs-calculator/ ├── pom.xml └── src └── main └── java ├── DevsCalculator.java └── RoundedButton.java
- **pom.xml**: Maven build configuration, including dependencies and the main class to run.
- **src/main/java/**: All Java source files.
- **target/**: Generated build artifacts (ignored by Git).
- **DevsCalculator.java**: The main class with `public static void main(String[] args)`.
- **RoundedButton.java**: A custom button class for rounded UI elements.