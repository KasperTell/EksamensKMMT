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

    private ArrayList<String> filePath;
    private HashMap<String, String> customerInfo;


    public CustomerPdf(ArrayList<String> filePath, HashMap<String, String> customerInfo)
    {


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
        insertPicture();


    }

    private void headerPicture() throws MalformedURLException {
        data = ImageDataFactory.create("Resources/Pictures/WUAV4.png");
        Image image = new Image(data);
        image.setFixedPosition(100, 750);
        image.setAutoScale(true);
        document.add(image);


    }


    private void customerInfo() {
        float oneColumnWidth[] = {600f}; //to kolonner sat i en array

        Table customerInfo = new Table(oneColumnWidth);
        customerInfo.addCell(new Cell().add(new Paragraph("Projekt nr. A")).setFontSize(12f).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph("Klaus Søren")).setFontSize(12f).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph("Gade vej 5A")).setFontSize(12f).setBorder(Border.NO_BORDER));
        customerInfo.addCell(new Cell().add(new Paragraph("6710" + " " + "Esbjerg V")).setBorder(Border.NO_BORDER));

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
        note.addCell(new Cell().add(new Paragraph("Her er der plads til at man kan skrive en hel del tekst." +
                "Her er der plads til at man kan skrive en hel del tekst." +
                "Her er der plads til at man kan skrive en hel del tekst." +
                "Her er der plads til at man kan skrive en hel del tekst." +
                "Her er der plads til at man kan skrive en hel del tekst." +
                "Her er der plads til at man kan skrive en hel del tekst." +
                "Her er der plads til at man kan skrive en hel del tekst." +
                "Her er der plads til at man kan skrive en hel del tekst.")).setFontSize(12f).setBorder(Border.NO_BORDER));

        document.add(note);
    }

    private void insertPicture() throws MalformedURLException {

        document.add(new AreaBreak());

        float oneColumnWidth[] = {600f}; //to kolonner sat i en array

        Table insertPicture = new Table(oneColumnWidth);

        data = ImageDataFactory.create("Resources/Pictures/ImagesSavedFromTechnicians/Flower0.jpg");
        Image image = new Image(data);

        insertPicture.addCell(new Cell().add(image.setHeight(700)).setBorder(Border.NO_BORDER));

        document.add(insertPicture);
    }



}
