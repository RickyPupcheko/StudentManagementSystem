/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentmanagementsystem;

/**
 * Base super class representing a person in the Student Management System
 * Applies encapsulation for data hiding and serves as the parent class for inheritance 
 * @author jpupc
 */
public class Person {
  
    // private attributes to enforce encapsulation 
    private String id;
    private String name;
    
    // default no-arg constructor
    public Person(){
        this.id = "";
        this.name = "";
        
    }
    
    // Parameterized constructor to initialize ID and name
    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    
    }
    // getter for ID
    public String getId() {
        return id;
        
    }
    
    // setter for ID 
    public void setId(String id) {
        this.id = id;
    }
    
    // getter for name
    public String getName() {
        return name;
    }
    
    // setter for name
    public void setName(String name) {
        this.name = name;
    }
    
    // returns formatted ID and name details
    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name;
    }
}
