package programs;

import java.util.*;

public class Demo {



    public static void main(String[] args) {

        StudentProcessing studentProcessing = new StudentProcessing();

        List<Student> studentList = studentProcessing.inputStudent();

        for (Student student : studentList)
            System.out.println(student.getName() + " " + student.getSchoolName());

        List<Student> sortedStudentList = studentProcessing.getSortStudentList(studentList);

        studentProcessing.createRollAndRegNo(sortedStudentList);

        studentProcessing.printFinalStudentList(studentList);
    }
}
