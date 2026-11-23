/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentmanagementsystem;

import java.util.ArrayList;
/**
 * Represents a regular student, inherits ID and name from person
 * Stores multiple subjects and grades in an ArrayList, and computes averages to meet requirements.
 * @author jpupc
 */
public class Student extends Person {
    
    // encapsulated list of subjects and grades for this student 
    private ArrayList<Subject> subjects;
    
    //Constructor initializes inherited Person attributes and empty subject list 
    public Student(String id, String name) {
        super(id, name);
        this.subjects = new ArrayList<>();
    }
    
    // adds a new subject or updates the grade if the subject already exists
    public void addOrUpdateSubject(String subjectName, double grade) {
        for (Subject sub : subjects) {
            if(sub.getName().equalsIgnoreCase(subjectName)) {
               sub.setGrade(grade);
               return;
               
            }
        }
        subjects.add(new Subject(subjectName, grade));
    }
    
    // returns the list of enrolled subjects
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }
    // Retrieves the grade for a specific subject, or -1 if not enrolled
    public double getSubjectGrade(String subjectName) {
        for (Subject sub : subjects) {
            if (sub.getName().equalsIgnoreCase(subjectName)) {
                return sub.getGrade();
            }
        }
        return -1.0;
     }
    
    // calculates the average grade across all recorded subjects
    public double calculateAverage() {
        if (subjects.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Subject sub : subjects ) {
            total += sub.getGrade();
        }
        return total / subjects.size();
    }
    
    // establishes academic standing (overridden in subclasses for polymorphism)
    public String getAcademicStanding() {
        double avg = calculateAverage();
        if (subjects.isEmpty()) {
            return "No Grades Recorded";
        }
        return (avg >=70.0) ? "Good Standing" : "Academic Probation";
    }
    
    // returns full student summary including type, average, standing, and subjects
    @Override
    public String toString() {
        return String.format("%s | Type: Regular | Avg: %.2f (%s) | Subjects: %s", super.toString(), calculateAverage(), getAcademicStanding(), subjects);
        }
    }
   
