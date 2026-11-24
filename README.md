# Student Management System

A console-based Java application built in Apache NetBeans for managing student records, tracking subject grades, and saving data to a local text file.

## Classes (`com.mycompany.studentmanagementsystem`)

* **`Person.java`** – Base class that stores shared attributes (`id` and `name`).
* **`Subject.java`** – Stores a subject name and grade, making sure grades stay between `0` and `100`.
* **`Student.java`** – Extends `Person` to store an `ArrayList<Subject>`, calculate grade averages, and check academic standing (`70.0` minimum).
* **`HonorsStudent.java`** – Extends `Student` and overrides `getAcademicStanding()` (`85.0` minimum) and `toString()`.
* **`StudentManagementSystem.java`** – Handles adding, updating, searching, and removing students, printing the three grade reports, and reading/writing `students.txt`.
* **`SMSDriver.java`** – Runs the console menu and handles user input validation with `try-catch` blocks.

## Features

* Add regular or honors students to show different types of students, update names, record subject grades, search by ID, and remove records.
* Check user input so invalid menu choices or out-of-range grades (`< 0` or `> 100`) print an error message instead of crashing.
* Print three reports: student grade averages and standings, highest and lowest grades for a chosen subject, and students sorted from highest to lowest average.
* Save and load student records in `students.txt` using a pipe-delimited format (`ID|TYPE|Name|Subject: Grade`).

## How to Run

1. Open the `StudentManagementSystem` project in **Apache NetBeans**.
2. Run **`SMSDriver.java`** (`Shift + F6`).
3. Follow the console menu options (`1–11`) to manage records or run reports, and enter `0` to save and exit.
