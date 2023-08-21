package bgu.spl.net.impl;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Course {
    public short numOfCourse;
    public String name;
    public int[] kdam;
    public int maxOfStudent;
    public int currentNumOfStudents = 0;
    public ConcurrentLinkedQueue<User> registerToCourse = new ConcurrentLinkedQueue<>();


    public Course(short numOfCourse,String name,int[] kdam,int maxOfStudent){
        this.numOfCourse = numOfCourse;
        this.name = name;
        this.kdam = kdam;
        this.maxOfStudent = maxOfStudent;

    }



}
