package programs;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ExcelReadWriteDemo {

    List<String[]> createExcelRowList(List<Student> studentList) {
        List<String[]> allRowList = new ArrayList<>();

        String [] headerRow = new String[] {"Sl.", "Name", "School Name", "Class", "School Roll No.", "Verification No", "Registration No", "Roll No", "Marks"};

        allRowList.add(headerRow);

        int i = 1;
        for (Student student : studentList) {
            String mark = String.valueOf(40 + new Random().nextInt(140));
            String [] otherRow = new String[] {i+".", student.getName(), student.getSchoolName(), student.getClassName(), String.valueOf(student.getSchoolRoleNo())
                    , student.getVerificationNo(), String.valueOf(student.getRegNo()), String.valueOf(student.getRoleNo()), mark};

            allRowList.add(otherRow);
            i++;
        }

        return allRowList;
    }


    public void generateStudentExcelFile(XSSFWorkbook workbook, List<Student> studentList, String fileName, String sheetName) {
        XSSFSheet sheet = workbook.createSheet(sheetName + Constants.STUDENT_LIST);
        List<String[]> allRowList = createExcelRowList(studentList);
        generateExcelFileFromList(workbook, sheet, allRowList, fileName);

    }

    public void generateExcelFileFromList( XSSFWorkbook workbook, XSSFSheet sheet, List<String[]> allRowList, String fileName){

        int i = 0, j;
        XSSFRow row;
        for (String[] rowList : allRowList) {
            row = sheet.createRow(i);
            j = 0;
            for (String column : rowList) {
                XSSFCell cell = row.createCell(j);

                if (NumberStringUtils.isNumeric(column))
                    cell.setCellValue(Double.parseDouble(column));
                else
                    cell.setCellValue(column);
                j++;
            }
            i++;
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
            workbook.write(fileOutputStream);

            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Student> createStudentListFromInputExcelFile(String fileName, String sheetName, String className) {
        List<Student> studentList = new ArrayList<>();
        try {
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);

            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet xssfSheet = workbook.getSheet(sheetName);

            Iterator<Row> rowIterator = xssfSheet.iterator();

            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String studentName = row.getCell(0).getStringCellValue().trim();
                String schoolName = row.getCell(1).getStringCellValue().trim();
                int schoolRollNo = (int) row.getCell(2).getNumericCellValue();

                Student student = new Student(studentName, schoolName, className, schoolRollNo);
                studentList.add(student);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public List<Student> createStudentListFromFormattedExcelFile(String fileName, String sheetName) {
        List<Student> studentList = new ArrayList<>();
        try {
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);

            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet xssfSheet = workbook.getSheet(sheetName);

            Iterator<Row> rowIterator = xssfSheet.iterator();

            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String studentName = row.getCell(1).getStringCellValue().trim();
                String schoolName = row.getCell(2).getStringCellValue().trim();
                String className = row.getCell(3).getStringCellValue().trim();
                int schoolRollNo = (int) row.getCell(4).getNumericCellValue();
                String verificationNo = row.getCell(5).getStringCellValue().trim();
                int regNo = (int) row.getCell(6).getNumericCellValue();
                int rollNo = (int) row.getCell(7).getNumericCellValue();
                double marks = row.getCell(8).getNumericCellValue();

                Student student = new Student(studentName, schoolName, className, schoolRollNo, rollNo, regNo, marks, verificationNo);
                studentList.add(student);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return studentList;
    }


}
