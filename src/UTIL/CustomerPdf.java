package UTIL;

import GUI.Model.CustomerInfo;
import GUI.Model.CustomerModel;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import static com.itextpdf.kernel.colors.DeviceGray.GRAY;

public class CustomerPdf {

    String path;
    PdfWriter pdfWriter;
    PdfDocument pdfDocument;
    Document document;

    String[] tekst;

    ImageData data;


    ArrayList<String> imagePath;
    HashMap<CustomerInfo,String> customerInfo;

     private String noteString,title;


    public CustomerPdf(ArrayList<String> imagePath, HashMap<CustomerInfo,String> customerList, String noteString, String title) {
        this.imagePath = imagePath;
        this.customerInfo = customerList;
        this.noteString=noteString;
        this.title=title;


    }




    public String makePdf() throws Exception {


        if (customerInfo.get("Company")==null)
        path = "Resources/PDF/"+customerInfo.get(CustomerInfo.FirstName)+" "+customerInfo.get(CustomerInfo.Lastname)+ " "+title+" installations dokumentation.pdf";
        else
            path = "Resources/PDF/"+customerInfo.get(CustomerInfo.Company)+ " "+title+ " installations dokumentation.pdf";

        pdfWriter = new PdfWriter(path);
        pdfDocument = new PdfDocument(pdfWriter);
        document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);


        page1();
        page2();
        page3();
        page4();
        document.close();

        return path;
    }



    private void page1() throws MalformedURLException {

        insertPicture("Resources/Pictures/AV top billede2.png",350,0,500); //Top billedet
        whiteSpace(350);


        tekst=new String[] {"","Installations dokumentation","",customerInfo.get(CustomerInfo.Company),"",title};


        insertTable2Row(16,300,tekst,false);
        insertPicture("Resources/Pictures/AV bund billede.png",0,0,120); //bund billedet

    }

    private void page2() throws MalformedURLException {

        document.add(new AreaBreak());
        insertPicture("Resources/Pictures/PDF side 2.png",120,10,680); //Hel skærms billede

    }


    public void page3() throws Exception {

        document.add(new AreaBreak());

        insertPicture("Resources/Pictures/WUAV4.png",730, 35,80); //Top billedet
        whiteSpace(50);


        CustomerModel customerModel=new CustomerModel();
                String town=customerModel.TownToZipCode(Integer.parseInt(customerInfo.get(CustomerInfo.ZipCode)));

            tekst=new  String[]{"Firma",customerInfo.get(CustomerInfo.Company),"Adresse",customerInfo.get(CustomerInfo.Address),"Postkode",customerInfo.get(CustomerInfo.ZipCode)+ " "+town,"Email"
                    ,customerInfo.get(CustomerInfo.Mail),"Telefon",customerInfo.get(CustomerInfo.PhoneNumber)};



        insertTable2Row(12,100,tekst,true);

        whiteSpace(20);
        createDivider();
        whiteSpace(6);
        note();
        whiteSpace(6);
        createDivider();
    }


    private void page4() throws MalformedURLException {

        for (int i = 0; i < imagePath.size(); i++) {

            if (Files.exists(Path.of(imagePath.get(i)))) //check om filen eksisterer
            insertPictureOnNewPage(imagePath.get(i));
        }
    }



    private void insertTable2Row( int textSize, int firstCellWidth, String[] tekst, boolean textLeft) {

        float twoColumnWidth[] = {firstCellWidth,600-firstCellWidth}; //to kolonner sat i en array
        Table table2 = new Table(twoColumnWidth);

        if (textLeft)
            for (int i = 0; i < tekst.length; i++) {
                table2.addCell(new Cell().add(new Paragraph(tekst[i])).setFontSize(textSize).setBorder(Border.NO_BORDER));
            }
        else
            for (int i = 0; i < tekst.length; i++) {
                table2.addCell(new Cell().add(new Paragraph(tekst[i])).setFontSize(textSize).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));



            }

        document.add(table2);


    }

    private void whiteSpace(int height) {
        Paragraph oneSp = new Paragraph("\n").setFontSize(height);
        document.add(oneSp); //tilføjer en linje med afstand
    }


    private void createDivider() {

        float fullWidth[] = {600};

        Border gBorder = new SolidBorder(GRAY, 1f);
        Table divider = new Table(fullWidth);
        divider.setBorder(gBorder);
        document.add(divider);
    }


    private void note() {
        float oneColumnWidth[] = {600f}; //to kolonner sat i en array

        Table note = new Table(oneColumnWidth);

       if (noteString!=null)
        note.addCell(new Cell().add(new Paragraph(noteString).setFontSize(12f)).setBorder(Border.NO_BORDER));

        document.add(note);
    }

    private void insertPictureOnNewPage(String imagePath) throws MalformedURLException {

        document.add(new AreaBreak());

        float oneColumnWidth[] = {600f}; //to kolonner sat i en array

        Table insertPicture = new Table(oneColumnWidth);

        data = ImageDataFactory.create(imagePath);
        Image image = new Image(data);

        insertPicture.addCell(new Cell().add(image.setHeight(700)).setBorder(Border.NO_BORDER));

        document.add(insertPicture);
    }

    private void insertPicture(String imagePath, int bottom,int left,int height) throws MalformedURLException {


        data = ImageDataFactory.create(imagePath);
        Image image = new Image(data);
        image.setFixedPosition(left, bottom);
        image.setHeight(height);

        document.add(image);
    }




}
