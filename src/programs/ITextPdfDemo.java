package programs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ITextPdfDemo {



    public void createPdf(List<Student> studentList, String filename, String className) throws IOException, DocumentException {

        Rectangle pageSize = new Rectangle(594, 423);
        pageSize.setBackgroundColor(new BaseColor(230, 230, 250));
        float margin = 25;
        Document document = new Document(pageSize, margin, margin, margin, margin);

        BaseFont scriptMTBold = BaseFont.createFont(Constants.SCRIPT_MT_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont oldEnglish = BaseFont.createFont(Constants.OLD_ENGLISH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(Font.FontFamily.TIMES_ROMAN,10f, Font.ITALIC, BaseColor.BLACK);

        Font oldEnglish25 = new Font(oldEnglish, 25, Font.NORMAL, BaseColor.BLACK);
        Font oldEnglishIT18 = new Font(oldEnglish, 18, Font.ITALIC, BaseColor.BLACK);
        Font scriptMTBold11 = new Font(scriptMTBold, 11, Font.NORMAL, BaseColor.BLACK);


        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();

        Image logoImage = Image.getInstance(Constants.AMAR_AMI_LOGO);
        Image signImage = Image.getInstance(Constants.SIGNATURE_IMAGE);

        for (Student student : studentList) {

            logoImage.setAlignment(Element.ALIGN_LEFT);
            logoImage.setBorderWidth(20);


            PdfPTable imageTable = new PdfPTable(2);
            imageTable.setWidthPercentage(100);
            imageTable.setWidths(new int[]{1, 5});

            PdfPCell imageCell = new PdfPCell();
            imageCell.addElement(logoImage);
            imageCell.setBorder(PdfPCell.NO_BORDER);
            imageTable.addCell(imageCell);


            Font font2 = new Font(oldEnglish, 12, Font.NORMAL, BaseColor.BLACK);
            PdfPCell textCell = new PdfPCell();

            Paragraph paragraph = new Paragraph("Amar Ami\n", oldEnglish25);
            paragraph.add(new Chunk("Talent Evaluation Exam - 2020\n", font2));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            textCell.addElement(paragraph);
            textCell.setBorder(Rectangle.NO_BORDER);


            imageTable.addCell(textCell);
            imageTable.setSpacingAfter(0);

            Paragraph paragraph1 = new Paragraph("Admit Card", oldEnglishIT18);
            paragraph1.setSpacingAfter(30);
            paragraph1.setAlignment(Element.ALIGN_CENTER);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(90);
            table.setWidths(new int[]{5, 3});

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Name: " + student.getName(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Roll No: " + student.getRoleNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("School: " + student.getSchoolName(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Registration No: " + student.getRegNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Class: " + className, scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Class Roll: " + student.getSchoolRoleNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Centre: Betbaria Secondary School", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Exam Date: 09 Aug, 9.00 am", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            table.setSpacingAfter(30);


            signImage.setAlignment(Element.ALIGN_LEFT);

            PdfPTable table2 = new PdfPTable(3);
            table2.setWidths(new int[]{17, 2, 3});
            table2.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPCell signImageCell = new PdfPCell();
            signImageCell.addElement(signImage);
            signImageCell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(signImageCell);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPTable table3 = new PdfPTable(2);
            table3.setWidths(new int[]{8, 3});
            table3.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);

            cell = new PdfPCell(new Phrase("Controller of Exam", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);


            Paragraph line = new Paragraph(new Chunk(new LineSeparator(0.0F, 100.0F, BaseColor.BLACK, Element.ALIGN_LEFT, 1)));

            Paragraph paragraph2 = new Paragraph("Examinee must bring this card to the examination hall.", font);
            Paragraph paragraph3 = new Paragraph("Examinee can keep nothing with him/her in the exam hall except pen, pencil, geometry instruments & calculator.", font);

            document.open();
            document.add(imageTable);
            document.add(paragraph1);
            document.add(table);
            document.add(table2);
            document.add(table3);
            document.add(line);
            document.add(paragraph2);
            document.add(paragraph3);

            document.newPage();
        }

        document.close();
    }

    public void generateFinalResultSheet(List<Student> studentList, String className, String filename) {
        float margin = 25;
        Document document = new Document(PageSize.A4, margin, margin, margin, margin);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            Image logoImage = Image.getInstance(Constants.AMAR_AMI_LOGO_WHITE);
            Font largeFont = new Font(Font.FontFamily.TIMES_ROMAN, 20f, Font.NORMAL, BaseColor.BLACK);
            Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11f, Font.NORMAL, BaseColor.BLACK);


            logoImage.setAlignment(Element.ALIGN_LEFT);
            logoImage.setBorderWidth(18);


            PdfPTable imageTable = new PdfPTable(2);
            imageTable.setWidthPercentage(100);
            imageTable.setWidths(new int[]{1, 4});

            PdfPCell imageCell = new PdfPCell();
            imageCell.addElement(logoImage);
            imageCell.setBorder(PdfPCell.NO_BORDER);
            imageTable.addCell(imageCell);

            PdfPCell textCell = new PdfPCell();
            Paragraph paragraph = new Paragraph("Amar Ami\n", largeFont);
            paragraph.add(new Chunk("Talent Evaluation Exam - 2020\n", largeFont));
            paragraph.add(new Chunk("Result Sheet (Class : " +className+")\n", largeFont));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            textCell.addElement(paragraph);
            textCell.setBorder(Rectangle.NO_BORDER);


            imageTable.addCell(textCell);
            imageTable.setSpacingAfter(20);


            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[] {2, 10, 10, 3, 3});

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Sl.", normalFont));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Name", normalFont));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("School", normalFont));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Roll No.", normalFont));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Marks", normalFont));
            table.addCell(cell);

            int i = 1;
            for (Student student : studentList) {
                cell = new PdfPCell(new Phrase(i + ".", normalFont));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(student.getName(), normalFont));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(student.getSchoolName(), normalFont));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(student.getRoleNo()), normalFont));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(student.getMarks()), normalFont));
                table.addCell(cell);

                i++;
            }

            document.add(imageTable);
            document.add(table);

            document.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
