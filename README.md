# Sudoku Solver Application

## Overview
This is a Java Swing-based interactive Sudoku Solver that allows users to input Sudoku puzzles on a 9x9 grid, validates input to ensure only digits 1-9 are allowed, and solves puzzles using a backtracking algorithm. The UI is responsive and features dynamic scaling of fonts and buttons.

## Features
- Interactive 9x9 grid for puzzle input.
- Real-time validation allowing only digits 1–9.
- Solve button that quickly solves the puzzle.
- Reset button to clear the board.
- Dynamic font and button size adjustment on window resize.
- User-friendly and visually distinct buttons.

## Requirements
- Java Development Kit (JDK) 8 or later.
- No additional external libraries required.

## Getting Started
1. Compile the source code:
    ```
    javac SudokuSolverApp.java
    ```
2. Run the application:
    ```
    java SudokuSolverApp
    ```
3. Enter numbers in the grid to form your Sudoku puzzle.
4. Click **Solve** to automatically solve the puzzle.
5. Use **Reset** to clear all cells and start anew.

## Code Structure
- `SudokuSolverApp.java` contains the main class implementing UI and solving logic.
- Uses `DocumentFilter` for input validation.
- Implements a classic backtracking algorithm to solve Sudoku.

## Usage Tips
![Demo Video](./demo.mp4)
- Only enter digits from 1 to 9 in the cells.
- Leave cells blank to represent empty spots.
- Resizing window dynamically adjusts the size of text fields and buttons.
- The Reset button clears the entire board instantly.

## License
This project is open-source and available for modification and distribution.

## Acknowledgments
Inspired by common Sudoku solving algorithms and classic Java Swing tutorials.
