package UTIL;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.itextpdf.kernel.colors.DeviceGray.GRAY;

public class CustomerPdf {

    String path = "installations dokumentation.pdf";
    PdfWriter pdfWriter;
    PdfDocument pdfDocument;
    Document document;

    ImageData data;


    ArrayList<String> imagePath;
    HashMap<String,String> customerInfo;

     private String noteString;


    public CustomerPdf(ArrayList<String> imagePath, HashMap<String,String> customerList, String noteString) {
        this.imagePath = imagePath;
        this.customerInfo = customerList;
        this.noteString=noteString;

    }




    public void makePdf() throws FileNotFoundException, MalformedURLException {
        pdfWriter = new PdfWriter(path);
        pdfDocument = new PdfDocument(pdfWriter);
        document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        pageThree();

        document.close();

    }

    public void pageThree() throws MalformedURLException {

        pdfDocument.addNewPage();
        headerPicture();
        headerSpace();
        customerInfo();
        createOneSpace();
        createDivider();
        createOneSpace();
        note();

        for (int i = 0; i < imagePath.size(); i++) {
            insertPicture(imagePath.get(i));
        }




    }

    private void headerPicture() throws MalformedURLException {
        data = ImageDataFactory.create("Resources/Pictures/WUAV4.png");
        Image image = new Image(data);
        image.setFixedPosition(100, 750);
        image.setAutoScale(true);


        document.add(image);


    }


    private void customerInfo() {
        float twoColumnWidth[] = {100f,400f}; //to kolonner sat i en array

        String firstName=customerInfo.get("FirstName");
        String address=customerInfo.get("Address");
        String ZipCode=customerInfo.get("ZipCode");
        String email=customerInfo.get("Mail");
        String phoneNumber=customerInfo.get("PhoneNumber");

        Table customerInfo = new Table(twoColumnWidth);
        customerInfo.addCell(new Cell().add(new Paragraph("Name:")).setFontSize(12f).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph(firstName)).setFontSize(12f).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph("Address:")).setFontSize(12f).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph(address)).setFontSize(12f).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph("ZipCode:")).setFontSize(12f).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph(ZipCode)).setFontSize(12f).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph("Email:")).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph(email)).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph("phoneNumber:")).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph(phoneNumber)).setBorder(Border.NO_BORDER));

        document.add(customerInfo);
    }


    private void createOneSpace() {
        Paragraph oneSp = new Paragraph("\n").setFontSize(6);
        document.add(oneSp); //tilføjer en linje med afstand
    }


    private void headerSpace() {
        Paragraph oneSp = new Paragraph("\n").setFontSize(30);
        document.add(oneSp); //tilføjer en linje med afstand
    }


    private void createDivider() {

        float fullWidth[] = {600};

        Border gBorder = new SolidBorder(GRAY, 2f);
        Table divider = new Table(fullWidth);
        divider.setBorder(gBorder);
        document.add(divider);
    }


    private void note() {
        float oneColumnWidth[] = {600f}; //to kolonner sat i en array

        Table note = new Table(oneColumnWidth);

        note.addCell(new Cell().add(new Paragraph(noteString).setFontSize(12f)).setBorder(Border.NO_BORDER));

        document.add(note);
    }

    private void insertPicture(String imagePath) throws MalformedURLException {

        document.add(new AreaBreak());

        float oneColumnWidth[] = {600f}; //to kolonner sat i en array

        Table insertPicture = new Table(oneColumnWidth);

        data = ImageDataFactory.create(imagePath);
        Image image = new Image(data);

        insertPicture.addCell(new Cell().add(image.setHeight(700)).setBorder(Border.NO_BORDER));

        document.add(insertPicture);
    }



}
