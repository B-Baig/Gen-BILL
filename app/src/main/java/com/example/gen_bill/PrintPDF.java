package com.example.gen_bill;

import android.os.Environment;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class PrintPDF {

    int invoiceNo;
    String name;
    String number;
    long invoiceDate;
    String item1;
    int itemQty1;
    int itemAmount1;
    String item2;
    int itemQty2;
    int itemAmount2;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public PrintPDF(int invoiceNo, String name, String number, long invoiceDate, String item1, int itemQty1, int itemAmount1, String item2, int itemQty2, int itemAmount2) {
        this.invoiceNo = invoiceNo;
        this.name = name;
        this.number = number;
        this.invoiceDate = invoiceDate;
        this.item1 = item1;
        this.itemQty1 = itemQty1;
        this.itemAmount1 = itemAmount1;
        this.item2 = item2;
        this.itemQty2 = itemQty2;
        this.itemAmount2 = itemAmount2;
    }






    public void getPDF() throws FileNotFoundException
    {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "Generated Invoice.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        float columnWidth1[]={120,220,120,100};
        Table table1 = new Table(columnWidth1);

        table1.addCell(new Cell().add(new Paragraph("Customer Name")).setFontSize(14).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(name)).setFontSize(14).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Invoice No")).setFontSize(14).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(invoiceNo+"")).setFontSize(14).setBorder(Border.NO_BORDER));


        table1.addCell(new Cell().add(new Paragraph("Mobile No.").setFontSize(14)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(number+"").setFontSize(14)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Date: ").setFontSize(14)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(dateFormat.format(invoiceDate)).setFontSize(14)).setBorder(Border.NO_BORDER));



        float columnWidth2[]={360,100,100};
        Table table2 = new Table(columnWidth2);

        table2.addCell(new Cell().add(new Paragraph("Item Description")));
        table2.addCell(new Cell().add(new Paragraph("Qty")));
        table2.addCell(new Cell().add(new Paragraph("Amount")));

        table2.addCell(new Cell().add(new Paragraph(item1)));
        table2.addCell(new Cell().add(new Paragraph(itemQty1+"")));
        table2.addCell(new Cell().add(new Paragraph(itemAmount1+"")));

        table2.addCell(new Cell().add(new Paragraph(item2)));
        table2.addCell(new Cell().add(new Paragraph(itemQty2+"")));
        table2.addCell(new Cell().add(new Paragraph(itemAmount2+"")));

        table2.addCell(new Cell(1,2).add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(itemAmount1+itemAmount2))));


        document.add(table1);
        document.add(new Paragraph("\n"));
        document.add(table2);
        document.close();




    }}
