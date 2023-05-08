package UTIL;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import static com.itextpdf.kernel.colors.DeviceGray.GRAY;

public class CustomerPdf {

    String path = "installations dokumentation.pdf";
    PdfWriter pdfWriter;
    PdfDocument pdfDocument;
    Document document;

    ImageData data;

public void makePdf() throws FileNotFoundException, MalformedURLException {
    pdfWriter = new PdfWriter(path);
    pdfDocument = new PdfDocument(pdfWriter);
    document = new Document(pdfDocument);

    pdfDocument.setDefaultPageSize(PageSize.A4);


    data= ImageDataFactory.create("Resources/Pictures/WuavLogo.png");
    com.itextpdf.layout.element.Image image = new Image(data);
    image.setFixedPosition(1, 1);
    document.add(image);



   //createHeader();

createDivider();



    document.close();

}


    private void createHeader()
    {
        float oneColumnWidth[] = {600f}; //to kolonner sat i en array

        Table header = new Table(oneColumnWidth);
        header.addCell(new Cell().add(new Paragraph("Dato")).setFontSize(20f).setBold().setBorder(Border.NO_BORDER).setBackgroundColor(GRAY).setBold());
        header.addCell(new Cell().add(new Paragraph("DDD")).setFontSize(20f).setBorder(Border.NO_BORDER).setBold());

        document.add(header);
    }


    private void createOneSpace()
    {
        Paragraph oneSp = new Paragraph("\n").setFontSize(6);
        document.add(oneSp); //tilføjer en linje med afstand
    }


    private void createDivider() {

        float fullWidth[] = {600};

        Border gBorder = new SolidBorder(GRAY, 2f);
        Table divider = new Table(fullWidth);
        divider.setBorder(gBorder);
        document.add(divider);
    }



}
