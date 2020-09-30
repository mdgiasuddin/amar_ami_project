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
        String [] roomNumberList = new String[] {"Room No : 109", "Room No : 110", "Room No : 111"};

        generateStudentAttendanceSheet(Constants.CLASS_TEN, Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_TEN_EXCEL_SHEET_NAME+Constants.STUDENT_LIST, distributionList, roomNumberList, Constants.CLASS_TEN_ATTENDANCE_SHEET);


        distributionList = new int[] {8, 6, 4};
        roomNumberList = new String[] {"Room No : 119", "Room No : 115", "Room No : 113"};
        generateStudentAttendanceSheet(Constants.CLASS_EIGHT, Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_EIGHT_EXCEL_SHEET_NAME+Constants.STUDENT_LIST, distributionList, roomNumberList, Constants.CLASS_EIGHT_ATTENDANCE_SHEET);


        distributionList = new int[] {7, 4, 6};
        roomNumberList = new String[] {"Room No : 219", "Room No : 215", "Room No : 123"};
        generateStudentAttendanceSheet(Constants.CLASS_FIVE, Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_FIVE_EXCEL_SHEET_NAME+Constants.STUDENT_LIST, distributionList, roomNumberList, Constants.CLASS_FIVE_ATTENDANCE_SHEET);

    }

    public void processStudentFromExcelFile(XSSFWorkbook workbook, String className, String inputFileName, String sheetName, int rollNo, int staringRegNo, int increasingRegNo, String outputPdfFileName, String outputExcelFileName, int verificationNumber) {
        List<Student> studentList = excelReadWriteDemo.createStudentListFromInputExcelFile(inputFileName, sheetName, className);
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
            student.setVerificationNo(c + ": " + verificationNumberList.get(i));
            i++;
        }
    }

    public void printFinalStudentList(XSSFWorkbook workbook, List<Student> studentList, String pdfFileName, String excelFileName, String className, String sheetName) {
        try {
            iTextPdfDemo.generateAdmitCard(studentList, pdfFileName);
            excelReadWriteDemo.generateStudentExcelFile(workbook, studentList, excelFileName, sheetName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    public void printResultSheet() {
        List<Student> classTenStudentList = excelReadWriteDemo.createStudentListFromFormattedExcelFile(Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_TEN_EXCEL_SHEET_NAME+Constants.STUDENT_LIST);
        List<Student> classEightStudentList = excelReadWriteDemo.createStudentListFromFormattedExcelFile(Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_EIGHT_EXCEL_SHEET_NAME+Constants.STUDENT_LIST);
        List<Student> classFiveStudentList = excelReadWriteDemo.createStudentListFromFormattedExcelFile(Constants.OUTPUT_EXCEL_FILE_NAME, Constants.CLASS_FIVE_EXCEL_SHEET_NAME+Constants.STUDENT_LIST);

        Collections.sort(classTenStudentList, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return (int) (o2.getMarks()-o1.getMarks());
            }
        });
        iTextPdfDemo.generateFinalResultSheet(classTenStudentList, Constants.CLASS_TEN, Constants.CLASS_TEN_RESULT_SHEET);

        Collections.sort(classEightStudentList, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return (int) (o2.getMarks()-o1.getMarks());
            }
        });
        iTextPdfDemo.generateFinalResultSheet(classEightStudentList, Constants.CLASS_EIGHT, Constants.CLASS_EIGHT_RESULT_SHEET);

        Collections.sort(classFiveStudentList, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return (int) (o2.getMarks()-o1.getMarks());
            }
        });
        iTextPdfDemo.generateFinalResultSheet(classFiveStudentList, Constants.CLASS_FIVE, Constants.CLASS_FIVE_RESULT_SHEET);

        printPrizeGivingList(classTenStudentList, classEightStudentList, classFiveStudentList);

    }

    public void printPrizeGivingList(List<Student> classTenStudentList, List<Student> classEightStudentList, List<Student>classFiveStudentList) {
        List<Student> classTenMeritList = classTenStudentList.subList(0, 6);
        List<Student> classEightMeritList = classEightStudentList.subList(0, 6);
        List<Student> classFiveMeritList = classFiveStudentList.subList(0, 6);

        List<Student> classTenSchoolWiseList = new ArrayList<>();
        List<Student> classEightSchoolWiseList = new ArrayList<>();
        List<Student> classFiveSchoolWiseList = new ArrayList<>();

        Set<String> schoolNameSet = new HashSet<>();
        for (Student student : classTenStudentList.subList(6, classTenStudentList.size())) {
            if (!schoolNameSet.contains(student.getSchoolName())) {
                classTenSchoolWiseList.add(student);
                schoolNameSet.add(student.getSchoolName());
            }
        }

        schoolNameSet = new HashSet<>();
        for (Student student : classEightStudentList.subList(6, classEightStudentList.size())) {
            if (!schoolNameSet.contains(student.getSchoolName())) {
                classEightSchoolWiseList.add(student);
                schoolNameSet.add(student.getSchoolName());
            }
        }

        schoolNameSet = new HashSet<>();
        for (Student student : classFiveStudentList.subList(6, classFiveStudentList.size())) {
            if (!schoolNameSet.contains(student.getSchoolName())) {
                classFiveSchoolWiseList.add(student);
                schoolNameSet.add(student.getSchoolName());
            }
        }


        List<Student> prizeGivingStudentList = new ArrayList<>();
        prizeGivingStudentList.addAll(classTenMeritList);
        prizeGivingStudentList.addAll(classEightMeritList);
        prizeGivingStudentList.addAll(classFiveMeritList);

        prizeGivingStudentList.addAll(classTenSchoolWiseList);
        prizeGivingStudentList.addAll(classEightSchoolWiseList);
        prizeGivingStudentList.addAll(classFiveSchoolWiseList);

        int [] studentNumberList = new int[] {classTenMeritList.size(), classEightMeritList.size(), classFiveMeritList.size()
                , classTenSchoolWiseList.size(), classEightSchoolWiseList.size(), classFiveSchoolWiseList.size()};

        String [] category = new String[] {"Class 10 Merit List", "Class 8 Merit List", "Class 5 Merit List", "Class 10 School-Wise List"
                , "Class 8 School-Wise List", "Class 5 School-Wise List"};

        iTextPdfDemo.generatePrizeGivingPdfFile(prizeGivingStudentList, studentNumberList, category, Constants.PRIZE_GIVING_PDF_FILE);

    }


    public void generateStudentAttendanceSheet(String className, String excelFileName, String sheetName, int [] distributionList, String [] roomNumberList, String pdfFileName) {
       List<Student> studentList  = excelReadWriteDemo.createStudentListFromFormattedExcelFile(excelFileName, sheetName);

       iTextPdfDemo.generateAttendanceSheet(studentList, className, distributionList, roomNumberList, pdfFileName);

    }
}
