/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentmanagementsystem;

/**
 * Core system controller that manages student records, grade tracking,
 * report generation, sorting/searching, and text-file persistence.
 * @author jpupc
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class StudentManagementSystem {

    // Stores all Student and HonorsStudent objects (polymorphic collection)
    private ArrayList<Student> students;

    // Constructor initializes an empty student roster
    public StudentManagementSystem() {
        this.students = new ArrayList<>();
    }

    // Searches for a student by ID (case-insensitive) and returns the match or null
    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id.trim())) {
                return s;
            }
        }
        return null;
    }

    // Adds a new student if the ID is not already taken
    public boolean addStudent(Student student) {
        if (findStudentById(student.getId()) != null) {
            return false;
        }
        students.add(student);
        return true;
    }

    // Removes a student record by ID
    public boolean removeStudent(String id) {
        Student target = findStudentById(id);
        if (target != null) {
            students.remove(target);
            return true;
        }
        return false;
    }

    // Updates an existing student's name
    public boolean updateStudentName(String id, String newName) {
        Student target = findStudentById(id);
        if (target != null) {
            target.setName(newName);
            return true;
        }
        return false;
    }

    // Adds or updates a subject grade for a specific student
    public boolean recordSubjectGrade(String id, String subjectName, double grade) {
        Student target = findStudentById(id);
        if (target == null) {
            return false;
        }
        // Subject validates 0-100 range and throws IllegalArgumentException if invalid
        target.addOrUpdateSubject(subjectName, grade);
        return true;
    }

    // Returns the full list of students
    public ArrayList<Student> getAllStudents() {
        return students;
    }

    // Report 1: Displays each student's average grade and academic standing
    public void printStudentAveragesReport() {
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        System.out.println("\n=== Student Average Grades Report ===");
        for (Student s : students) {
            System.out.printf("ID: %-8s | Name: %-18s | Average: %6.2f | Standing: %s%n",
                    s.getId(), s.getName(), s.calculateAverage(), s.getAcademicStanding());
        }
    }

    // Report 2: Finds and displays the highest and lowest grade for a given subject
    public void printSubjectHighLowReport(String subjectName) {
        Student highestStudent = null;
        Student lowestStudent = null;
        double highestGrade = -1.0;
        double lowestGrade = 101.0;

        for (Student s : students) {
            double grade = s.getSubjectGrade(subjectName);
            if (grade >= 0.0) {
                if (grade > highestGrade) {
                    highestGrade = grade;
                    highestStudent = s;
                }
                if (grade < lowestGrade) {
                    lowestGrade = grade;
                    lowestStudent = s;
                }
            }
        }

        System.out.println("\n=== Subject Performance Report: " + subjectName + " ===");
        if (highestStudent == null) {
            System.out.println("No grades recorded for subject: " + subjectName);
        } else {
            System.out.printf("Highest Grade: %.2f (%s - ID: %s)%n",
                    highestGrade, highestStudent.getName(), highestStudent.getId());
            System.out.printf("Lowest Grade:  %.2f (%s - ID: %s)%n",
                    lowestGrade, lowestStudent.getName(), lowestStudent.getId());
        }
    }

    // Report 3: Sorts a copy of the student roster from highest to lowest average grade
    public void printSortedByGradeReport() {
        if (students.isEmpty()) {
            System.out.println("No student records available to sort.");
            return;
        }
        ArrayList<Student> sortedList = new ArrayList<>(students);

        // Standard bubble sort from highest to lowest average grade
        for (int i = 0; i < sortedList.size() - 1; i++) {
            for (int j = 0; j < sortedList.size() - 1 - i; j++) {
                if (sortedList.get(j).calculateAverage() < sortedList.get(j + 1).calculateAverage()) {
                    Student temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }
            }
        }

        System.out.println("\n=== Students Sorted by Average Grade (Highest to Lowest) ===");
        int rank = 1;
        for (Student s : sortedList) {
            System.out.printf("%d. %s%n", rank++, s.toString());
        }
    }

    // Saves all student records and subject grades to a text file
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Student s : students) {
                String type;
                if (s instanceof HonorsStudent) {
                    type = "HONORS";
                } else {
                    type = "REGULAR";
                }

                StringBuilder line = new StringBuilder();
                line.append(s.getId()).append("|")
                    .append(type).append("|")
                    .append(s.getName()).append("|");

                ArrayList<Subject> subs = s.getSubjects();
                for (int i = 0; i < subs.size(); i++) {
                    line.append(subs.get(i).getName())
                        .append(":")
                        .append(subs.get(i).getGrade());
                    if (i < subs.size() - 1) {
                        line.append(",");
                    }
                }
                writer.println(line.toString());
            }
            System.out.println("Successfully saved " + students.size() + " record(s) to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }
    
    // Loads student records and subject grades from a text file
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found: " + filename + " (starting with current records).");
            return;
        }

        ArrayList<Student> loadedStudents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                // Expected format: ID|TYPE|Name|Subject1:Grade1,Subject2:Grade2
                String[] parts = line.split("\\|", -1);
                if (parts.length < 3) {
                    continue;
                }
                String id = parts[0].trim();
                String type = parts[1].trim();
                String name = parts[2].trim();

                // Instantiate HonorsStudent or regular Student based on saved type
                Student student;
                if (type.equalsIgnoreCase("HONORS")) {
                    student = new HonorsStudent(id, name);
                } else {
                    student = new Student(id, name);
                }

                // Parse subject pairs if present
                if (parts.length >= 4 && !parts[3].trim().isEmpty()) {
                    String[] subjectEntries = parts[3].split(",");
                    for (String entry : subjectEntries) {
                        String[] subParts = entry.split(":");
                        if (subParts.length == 2) {
                            String subName = subParts[0].trim();
                            double grade = Double.parseDouble(subParts[1].trim());
                            student.addOrUpdateSubject(subName, grade);
                        }
                    }
                }
                loadedStudents.add(student);
            }
            this.students = loadedStudents;
            System.out.println("Successfully loaded " + students.size() + " record(s) from " + filename);
        } catch (IOException e) {
            System.out.println("Error reading data file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid data format in file: " + e.getMessage());
        }
    }
}