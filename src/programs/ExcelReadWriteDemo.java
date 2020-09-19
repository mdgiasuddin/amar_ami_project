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

public class ExcelReadWriteDemo {

    public void generateStudentExcelFile(List<Student> studentList, String fileName) {
        List<String[]> allRowList = new ArrayList<>();

        String [] headerRow = new String[] {"Sl.", "Name", "School Name", "School Roll No.", "Roll No", "Registration No"};

        allRowList.add(headerRow);

        int i = 1;
        for (Student student : studentList) {
            String [] otherRow = new String[] {i+".", student.getName(), student.getSchoolName(), String.valueOf(student.getSchoolRoleNo())
                    , String.valueOf(student.getRoleNo()), String.valueOf(student.getRegNo())};

            allRowList.add(otherRow);
            i++;
        }

        generateExcelFileFromList(allRowList, fileName);
    }

    public void generateExcelFileFromList(List<String[]> allRowList, String fileName){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();


        int i = 0, j;
        XSSFRow row;
        for (String[] rowList : allRowList) {
            row = sheet.createRow(i);
            j = 0;
            for (String column : rowList) {
                XSSFCell cell = row.createCell(j);
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

    public List<Student> createStudentListFromExcel(String fileName) {
        List<Student> studentList = new ArrayList<>();
        try {
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);

            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet xssfSheet = workbook.getSheet("student");

            Iterator<Row> rowIterator = xssfSheet.iterator();

            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String studentName = row.getCell(0).getStringCellValue().trim();
                String schoolName = row.getCell(1).getStringCellValue().trim();
                int schoolRollNo = (int) row.getCell(2).getNumericCellValue();

                Student student = new Student(studentName, schoolName, schoolRollNo);
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
