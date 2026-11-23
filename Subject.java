/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentmanagementsystem;

/**
 *Represents an academic subject and its grade for a student
 * Enforces encapsulation and validates that grades remain between 0 and 100.
 * @author jpupc
 */
public class Subject {
    
    
    // private fields for data hiding (encapsulation)
    private String name;
    private double grade;
    
    // Constructor Initializes subject name and validates the grade range
    public Subject(String name, double grade) {
        this.name = name;
        setGrade(grade);
    }
    
    // getter for subject name
    public String getName() {
        return name;
    }
    
    // setter for subject name
    public void setName(String name) {
        this.name = name;
    }
    
    //getter for numberic grade
    public double getGrade() {
        return grade;
    }
    
    // Validates that grade is between 0 and 100 before updating 
    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException(
                     "Invalid grade (" + grade +  "). Grade must be between 0 and 100.");
        }
        this.grade = grade;
    }
    
    // Returns formatted subject and grade string
    @Override
    public String toString() {
        return String.format("%s: %.2f", name, grade);
        
        }
    }
