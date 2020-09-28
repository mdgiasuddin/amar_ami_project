package programs;

import com.itextpdf.text.DocumentException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StudentProcessing {
    ExcelReadWriteDemo excelReadWriteDemo = new ExcelReadWriteDemo();
    ITextPdfDemo iTextPdfDemo = new ITextPdfDemo();


    public void processEveryThingBeforeExam() {
        XSSFWorkbook workbook = new XSSFWorkbook();

        processStudentFromExcelFile(workbook, Constants.CLASS_TEN, Constants.INPUT_EXCEL_FILE_NAME, Constants.CLASS_TEN_EXCEL_SHEET_NAME, Constants.CLASS_TEN_STARING_ROLL_NO
                , Constants.CLASS_TEN_STARING_REG_NO, Constants.CLASS_TEN_INCREASING_REG_NO, Constants.CLASS_TEN_PDF_FILE_NAME, Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_TEN_VERIFICATION_NUM);

        processStudentFromExcelFile(workbook, Constants.CLASS_EIGHT, Constants.INPUT_EXCEL_FILE_NAME, Constants.CLASS_EIGHT_EXCEL_SHEET_NAME, Constants.CLASS_EIGHT_STARING_ROLL_NO
                , Constants.CLASS_EIGHT_STARING_REG_NO, Constants.CLASS_EIGHT_INCREASING_REG_NO, Constants.CLASS_EIGHT_PDF_FILE_NAME, Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_EIGHT_VERIFICATION_NUM);

        processStudentFromExcelFile(workbook, Constants.CLASS_FIVE, Constants.INPUT_EXCEL_FILE_NAME, Constants.CLASS_FIVE_EXCEL_SHEET_NAME, Constants.CLASS_FIVE_STARING_ROLL_NO
                , Constants.CLASS_FIVE_STARING_REG_NO, Constants.CLASS_FIVE_INCREASING_REG_NO, Constants.CLASS_FIVE_PDF_FILE_NAME, Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_FIVE_VERIFICATION_NUM);


    }

    public void generateAttendanceSheetAllClass() {
        int [] distributionList = new int[] {5, 6, 3};
        String [] roomNumberList = new String[] {"Room : 109", "Room : 110", "Room : 111"};

        generateStudentAttendanceSheet(Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_TEN_EXCEL_SHEET_NAME+Constants.ATTENDANCE_SHEET, distributionList, roomNumberList);


    }

    public void processStudentFromExcelFile(XSSFWorkbook workbook, String className, String inputFileName, String sheetName, int rollNo, int staringRegNo, int increasingRegNo, String outputPdfFileName, String outputExcelFileName, int verificationNumber) {
        List<Student> studentList = excelReadWriteDemo.createStudentListFromExcel(inputFileName, sheetName);
        List<Student> sortedStudentList = getSortStudentList(studentList);


        createRollAndRegNo(sortedStudentList, rollNo, staringRegNo, increasingRegNo, verificationNumber);

        printFinalStudentList(workbook, sortedStudentList, outputPdfFileName, outputExcelFileName, className, sheetName);
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

    public void createRollAndRegNo(List<Student> studentList, int rollNo, int staringRegNo, int increasingRegNo, int verificationNumber) {
        Map<String, Integer> map = new HashMap<>();
        int schoolCodeSerial = 1;
        List<Integer> verificationNumberList = IntStream.range(verificationNumber, verificationNumber+Constants.NUMBER_OF_STUDENT).boxed().collect(Collectors.toList());
        Collections.shuffle(verificationNumberList);

        int i = 0;
        Random random = new Random();

        for (Student student : studentList) {

            student.setRoleNo(rollNo);

            if (map.get(student.getSchoolName()) == null) {
                map.put(student.getSchoolName(), schoolCodeSerial);
                schoolCodeSerial++;
            }

            int schoolCode = map.get(student.getSchoolName());
            int regNo = staringRegNo*10000 + schoolCode*1000 + increasingRegNo;

            student.setRegNo(regNo);

            rollNo++;
            increasingRegNo++;

            char c = (char) (random.nextInt(26) + 'A');
            student.setVerificationNo(c + ":" + verificationNumberList.get(i));
            i++;
        }
    }

    public void printFinalStudentList(XSSFWorkbook workbook, List<Student> studentList, String pdfFileName, String excelFileName, String className, String sheetName) {
        try {
            iTextPdfDemo.createPdf(studentList, pdfFileName, className);
            excelReadWriteDemo.generateStudentExcelFile(workbook, studentList, excelFileName, sheetName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void printResultSheet() {
        String excelFileName = "src/output_files/student_excel_list.xlsx";
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

        String pdfFileName = "src/output_files/final_result_sheet_five.pdf";
        iTextPdfDemo.generateFinalResultSheet(studentList, "FIVE", pdfFileName);
    }


    public void generateStudentAttendanceSheet(String fileName, String sheetName, int [] distributionList, String [] roomNumberList) {
       List<Student> studentList  = excelReadWriteDemo.createAttendanceSheetStudentListFromExcel(fileName, sheetName);

       int studentCovered = 0;
       for (int i=0; i<distributionList.length; i++) {
           int numberOfStudent = distributionList[i];
           String roomNumber = roomNumberList[i];

           System.out.println(roomNumber);
           for (int j=0; j<numberOfStudent; j++) {
               Student student = studentList.get(studentCovered+j);
               System.out.println(student.getName() + " " + student.getRoleNo() + " " + student.getRegNo() + " " + student.getVerificationNo());
           }

           studentCovered += numberOfStudent;

       }
    }
}
