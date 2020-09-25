package programs;

import com.itextpdf.text.DocumentException;

import java.io.*;
import java.util.*;

public class StudentProcessing {
    ExcelReadWriteDemo excelReadWriteDemo = new ExcelReadWriteDemo();
    ITextPdfDemo iTextPdfDemo = new ITextPdfDemo();



    public void processEveryThingBeforeExam() {
        String inputFileName = "src/files/student_input_list.xlsx";
        String inputSheetName = "class_ten";

        processStudentFromExcelFile(inputFileName, inputSheetName);


    }

    public void processStudentFromExcelFile(String fileName, String sheetName) {
        List<Student> studentList = excelReadWriteDemo.createStudentListFromExcel(fileName, sheetName);
        List<Student> sortedStudentList = getSortStudentList(studentList);
        createRollAndRegNo(sortedStudentList);

        String outputPdfFileName = "src/admitCards/ClassFive.pdf";
        String outputExcelFileName = "src/admitCards/class_five_list.xlsx";
        String outputSheetName = "class_five";

        printFinalStudentList(sortedStudentList, outputPdfFileName, outputExcelFileName, "Five", outputSheetName);
    }

    public List<Student> getSortStudentList(List<Student> studentList) {
        List<Student> resultList = new ArrayList<>();
        Random random = new Random();

        int listLength = studentList.size();
        System.out.println("Length Outside: " + listLength);
        while (listLength > 0) {
            int count = 0;
            while (true) {
                int randIndex = random.nextInt(listLength);
                System.out.println("Random number : " + randIndex);
                if (resultList.size() == 0 || !resultList.get(resultList.size()-1).getSchoolName().equals(studentList.get(randIndex).getSchoolName())) {
                    resultList.add(studentList.get(randIndex));

                    Student tempStudent = studentList.get(randIndex);
                    studentList.set(randIndex, studentList.get(listLength-1));
                    studentList.set(listLength-1, tempStudent);
                    listLength--;

                    System.out.println("Length Inside: " + listLength);

                    count = 0;
                    break;
                }
                count++;

                if (count > 200)
                    break;
            }

            if (count > 200)
                break;

        }
        for (int i=0; i<listLength; i++) {
            Student student = studentList.get(i);

            for (int j=1; j<resultList.size(); j++) {
                if (!resultList.get(j-1).getSchoolName().equals(student.getSchoolName()) && !resultList.get(j).getSchoolName().equals(student.getSchoolName())) {
                    resultList.add(j, student);
                    break;
                }
            }
        }
        return resultList;
    }

    public void createRollAndRegNo(List<Student> studentList) {
        Map<String, Integer> map = new HashMap<>();
        int schoolCodeSerial = 1;
        int roleNo = 971221;
        int staringRegNo = 57;
        int increasingRegNo = 331;
        for (Student student : studentList) {
            student.setRoleNo(roleNo);

            if (map.get(student.getSchoolName()) == null) {
                map.put(student.getSchoolName(), schoolCodeSerial);
                schoolCodeSerial++;
            }

            int schoolCode = map.get(student.getSchoolName());
            int regNo = staringRegNo*10000 + schoolCode*1000 + increasingRegNo;

            student.setRegNo(regNo);

            roleNo++;
            increasingRegNo++;
        }
    }

    public void printFinalStudentList(List<Student> studentList, String pdfFileName, String excelFileName, String className, String sheetName) {
        try {
            iTextPdfDemo.createPdf(studentList, pdfFileName, className);
            excelReadWriteDemo.generateStudentExcelFile(studentList, excelFileName, sheetName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void printResultSheet() {
        String excelFileName = "src/admitCards/student_excel_list.xlsx";
        List<Student> studentList = excelReadWriteDemo.createStudentListFromExcelWithMarks(excelFileName);
        System.out.println("Marks: ");

        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return (int) (o2.getMarks()-o1.getMarks());
            }
        });
        for (Student student : studentList) {
            System.out.println(student.getRoleNo() + " " + student.getMarks());
        }

        String pdfFileName = "src/admitCards/final_result_sheet_five.pdf";
        iTextPdfDemo.generateFinalResultSheet(studentList, "FIVE", pdfFileName);
    }
}
