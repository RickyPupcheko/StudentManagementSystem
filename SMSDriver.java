/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentmanagementsystem;

import java.io.File;
import java.util.Scanner;

/**
 * Console driver for the Student Management System. 
 * Provides a menu-driven Scanner interface and handles user input errors.
 * @author jpupc
 */
public class SMSDriver {
    

    // Default text file used for saving and loading student records
    private static final String DATA_FILE = "students.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        // Automatically load existing records if students.txt is already present
        if (new File(DATA_FILE).exists()) {
            sms.loadFromFile(DATA_FILE);
        }

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAddStudent(scanner, sms);
                    break;
                case "2":
                    handleRecordGrade(scanner, sms);
                    break;
                case "3":
                    handleUpdateStudentName(scanner, sms);
                    break;
                case "4":
                    handleRemoveStudent(scanner, sms);
                    break;
                case "5":
                    handleViewStudent(scanner, sms);
                    break;
                case "6":
                    handleViewAllStudents(sms);
                    break;
                case "7":
                    sms.printStudentAveragesReport();
                    break;
                case "8":
                    handleSubjectReport(scanner, sms);
                    break;
                case "9":
                    sms.printSortedByGradeReport();
                    break;
                case "10":
                    sms.saveToFile(DATA_FILE);
                    break;
                case "11":
                    sms.loadFromFile(DATA_FILE);
                    break;
                case "0":
                    // Save records automatically before exiting
                    sms.saveToFile(DATA_FILE);
                    System.out.println("Exiting Student Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number from 0 to 11.");
            }
        }
        scanner.close();
    }

    // Displays all available console operations
    private static void printMenu() {
        System.out.println("\n==========================================");
        System.out.println("        STUDENT MANAGEMENT SYSTEM         ");
        System.out.println("==========================================");
        System.out.println("1.  Add New Student (Regular or Honors)");
        System.out.println("2.  Add or Update Subject Grade");
        System.out.println("3.  Update Student Name");
        System.out.println("4.  Remove Student");
        System.out.println("5.  Search & View Student Details");
        System.out.println("6.  View All Students");
        System.out.println("7.  Report: Average Grade per Student");
        System.out.println("8.  Report: Highest & Lowest Subject Grade");
        System.out.println("9.  Report: Students Sorted by Grade");
        System.out.println("10. Save Records to File (" + DATA_FILE + ")");
        System.out.println("11. Load Records from File (" + DATA_FILE + ")");
        System.out.println("0.  Save & Exit");
        System.out.println("==========================================");
    }

    // Prompts for student ID, name, and type (Regular vs. Honors)
    // Prompts for student ID, name, and type (Regular vs. Honors) 
    private static void handleAddStudent(Scanner scanner, StudentManagementSystem sms) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Student ID cannot be empty.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return;
        }

        System.out.print("Is this an Honors student? (y/n): ");
        String isHonors = scanner.nextLine().trim();

        Student newStudent;
        if (isHonors.equalsIgnoreCase("y")) {
            newStudent = new HonorsStudent(id, name);
        } else {
            newStudent = new Student(id, name);
        }

        if (sms.addStudent(newStudent)) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Error: A student with ID '" + id + "' already exists.");
        }
    }
    
    // Prompts for student ID, subject name, and a validated grade between 0 and 100
    private static void handleRecordGrade(Scanner scanner, StudentManagementSystem sms) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();

        if (sms.findStudentById(id) == null) {
            System.out.println("Student with ID '" + id + "' not found.");
            return;
        }

        System.out.print("Enter Subject Name: ");
        String subject = scanner.nextLine().trim();
        if (subject.isEmpty()) {
            System.out.println("Subject name cannot be empty.");
            return;
        }

        System.out.print("Enter Grade (0 - 100): ");
        try {
            double grade = Double.parseDouble(scanner.nextLine().trim());
            sms.recordSubjectGrade(id, subject, grade);
            System.out.println("Grade recorded successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter a numeric grade.");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    // Updates an existing student's name
    private static void handleUpdateStudentName(Scanner scanner, StudentManagementSystem sms) {
        System.out.print("Enter Student ID to update: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter New Name: ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) {
            System.out.println("New name cannot be empty.");
            return;
        }

        if (sms.updateStudentName(id, newName)) {
            System.out.println("Student name updated successfully.");
        } else {
            System.out.println("Student with ID '" + id + "' not found.");
        }
    }

    // Removes a student record by ID
    private static void handleRemoveStudent(Scanner scanner, StudentManagementSystem sms) {
        System.out.print("Enter Student ID to remove: ");
        String id = scanner.nextLine().trim();

        if (sms.removeStudent(id)) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student with ID '" + id + "' not found.");
        }
    }

    // Searches for and displays a single student's full details
    private static void handleViewStudent(Scanner scanner, StudentManagementSystem sms) {
        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine().trim();

        Student found = sms.findStudentById(id);
        if (found != null) {
            System.out.println("\n--- Student Details ---");
            System.out.println(found);
        } else {
            System.out.println("Student with ID '" + id + "' not found.");
        }
    }

    // Displays every student currently stored in the system
    private static void handleViewAllStudents(StudentManagementSystem sms) {
        if (sms.getAllStudents().isEmpty()) {
            System.out.println("No student records in the system.");
            return;
        }
        System.out.println("\n=== All Enrolled Students ===");
        for (Student s : sms.getAllStudents()) {
            System.out.println(s);
        }
    }

    // Prompts for a subject name and prints highest/lowest scores
    private static void handleSubjectReport(Scanner scanner, StudentManagementSystem sms) {
        System.out.print("Enter Subject Name for report: ");
        String subject = scanner.nextLine().trim();
        if (subject.isEmpty()) {
            System.out.println("Subject name cannot be empty.");
            return;
        }
        sms.printSubjectHighLowReport(subject);
    }
}

