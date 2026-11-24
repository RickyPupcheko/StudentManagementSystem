/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentmanagementsystem;


public class HonorsStudent extends Student {
/**
 *Subclass of Student represents an honors student to satisfy requirement of handling different types of students. 
 * @author jpupc
 */  

    // Constructor delegates initialization to the Student superclass
    public HonorsStudent(String id, String name) {
    super(id, name);
}

// overides standing evaluation with a higher 85.0 honors requirement
@Override
public String getAcademicStanding() {
    double avg = calculateAverage();
    if (super.getSubjects().isEmpty()) {
        return "No Grades Recorded";
    }
    return (avg >= 85.0) ? "Honors Standing" : "Honors Probation";
}

//Overrides string formatting to identify Honors Students Status
@Override
public String toString() {
    return String.format("%s | Type: Honors | Avg: %.2f (%s) | Subjects: %s", "ID: " +getId() + " | Name: " + getName(), calculateAverage(), getAcademicStanding(), getSubjects());

    }
}

