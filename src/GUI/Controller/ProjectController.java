package GUI.Controller;

import BE.*;
import DAL.FilesDAO;
import GUI.Model.*;
import PersonsTypes.PersonTypeChooser;
import UTIL.CustomerPdf;
import UTIL.ShowFile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ProjectController extends BaseController {
    @FXML
    private TextArea NotesTextArea, NotesTextAreaProject;
    @FXML // Main view for this window
    private AnchorPane mainViewAnchorPane;

    @FXML //All buttons within this window
    private Button deleteFileButton, saveFileButton, saveNoteButton, addTechButton, removeTechButton, drawButton, openMainWindowButton, openPDFButton;

    @FXML //Tab-Pane containing all the project information
    private TabPane informationTabPane;

    @FXML //All tabs within the informationTabPane
    private Tab contactTab, filesTab, notesTab, techsTab;

    @FXML //Table-View containing project files
    private TableView<ProjectFiles> fileTable;

    @FXML // Image-View containing a preview of a selcted file
    private ImageView filesPreviewImageView;

    @FXML //Combo-Box containing all Techs.
    private ComboBox<User> listTechsComboBox;

    @FXML //All columns within the filesTab
    private TableColumn filesPictureColumn, filesNameColumn, filesDateColumn, filesIncludedColumn;

    @FXML //List-View containing Techs on project
    private ListView<User> techsOnProjectListView;

    private Project selectedProject, selectedProjectStorage;
    private ProjectFiles selectedfile;
    private File file;
    private String filePath = "Resources/Pictures/ImagesSavedFromTechnicians";
    private Path target = Paths.get(filePath);
    private UserModel userModel;
    private CustomerModel customerModel;
    private ProjectFilesModel projectFilesModel;
    private ProjectModel projectModel;

    private ShowFile showFile=new ShowFile();

    /**
     * Set up the view when the view is getting shown.
     */
    @Override
    public void setup()  {
        //Initializing all our models.
        userModel = getModel().getUserModel();
        customerModel = getModel().getCustomerModel();
        projectFilesModel = getModel().getProjectFilesModel();
        projectModel = getModel().getProjectModel();
        selectedProject = projectModel.getSelectedProject();



        //Setting the information of the listviews and combobox.
        listenerMouseClickPicture();
        setupFiles();
        pictureToButton();
        setInformation();
        enableDisableTab();
        listenerTabs();
    }

    private void listenerTabs() {

        informationTabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {

                        if(informationTabPane.getSelectionModel().getSelectedIndex() == 1 ||informationTabPane.getSelectionModel().getSelectedIndex() == 2)
                            filesPreviewImageView.setImage(null);

                    }
                }
        );

    }

    private void setInformation() {
        NotesTextAreaProject.setText(selectedProject.getNote());
        NotesTextAreaProject.setWrapText(true);
        listTechsComboBox.setItems(userModel.getAllTechnicians());

        try {
            techsOnProjectListView.setItems(userModel.getAllTechniciansOnProject(selectedProject.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void pictureToButton() {

        String[] listOfToolTips = {"Go back to previous window", "Open project PDF", "Draw up a technical drawing"};

        Button[] listOfButtons = {openMainWindowButton, openPDFButton, drawButton};

        String[] listOfFiles = {"Pictures/ButtonImages/GoBack.png", "Pictures/ButtonImages/OpenPDF.png", "Pictures/ButtonImages/Paint.png"};

        for (int i = 0; i < listOfToolTips.length; i++) {

            Image img = new Image(listOfFiles[i]);
            ImageView view = new ImageView(img);
            Tooltip tip = new Tooltip(listOfToolTips[i]);

            listOfButtons[i].setTooltip(tip);
            listOfButtons[i].setGraphic(view);
        }
    }


    private void enableDisableTab()
    {
        PersonTypeChooser personTypeChooser=new PersonTypeChooser();
        techsTab.setDisable(personTypeChooser.enableTab());
    }

    /**
     * Listener for the tableview containing files for opening files.
     */
    public void listenerMouseClickPicture() {
        fileTable.setOnMouseClicked(event -> {
            selectedfile = fileTable.getSelectionModel().getSelectedItem();


                    // Check if the selected file is an image file (JPEG, PNG, or JPG)
                    if (selectedfile!=null)
                    {
                        filesPreviewImageView.setImage(null);
                        String fileName = selectedfile.getFilePath().toLowerCase();
                        if (fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".jpg")) {

                            
                            if (Files.exists(Path.of(selectedfile.getFilePath()))) //check om filen eksisterer
                            {
                                Image image = null;
                                try {
                                    image = new Image(new FileInputStream(selectedfile.getFilePath()));
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                filesPreviewImageView.setImage(image);
                            }
                            else
                            {
                                showFile.showErrorBox("File does not exits","File message");
                            }
                        }

                    }


            if (event.getClickCount() == 2) {
                showFile.showFile(selectedfile.getFilePath());
                    }
        });
    }


    /**
     * Set up the files information column in the tableview.
     */
    @FXML
    private void setupFiles()  {


        int projectNumber = selectedProject.getId();
        filesPictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
        filesNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        filesDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        filesIncludedColumn.setCellValueFactory(new PropertyValueFactory<>("usedBox"));


        try {
            fileTable.setItems(projectFilesModel.getAllFilesFromProject(projectNumber));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            projectFilesModel.fileLoopStop(); //Stopper tidligere løkker i projectFiles inden ny startes
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        projectFilesModel.observer(); //Her startes en løkke, der observere ændringer i CheckBox
    }


    @FXML
    private void saveNoteAction(ActionEvent actionEvent) {
        String note = NotesTextAreaProject.getText();

        try {
            projectModel.changeNote(note, selectedProject.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set up the information about the customer in the main view when a project is selected.
     */

    private HashMap<CustomerInfo, String> makeCustomerMap() {

        HashMap<CustomerInfo, String> customerInfo = new HashMap<>();

        if (selectedProject != null) {
            //Setting the information in the labels.
            int customerID = selectedProject.getCustomerID();
            Customer customer = null;

            try {
                customer = customerModel.loadCustomer(customerID);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            customerInfo.put(CustomerInfo.Company, customer.getCompanyName());
            customerInfo.put(CustomerInfo.FirstName, customer.getFirstName());
            customerInfo.put(CustomerInfo.Lastname, customer.getLastName());
            customerInfo.put(CustomerInfo.Address, customer.getAddress());
            customerInfo.put(CustomerInfo.ZipCode, String.valueOf(customer.getZipCode()));
            customerInfo.put(CustomerInfo.Mail, customer.getMail());
            customerInfo.put(CustomerInfo.PhoneNumber, String.valueOf(customer.getPhoneNumber()));
        }
        return customerInfo;
    }

    /**
     * Save a new file from the users pc.
     *
     * @param actionEvent
     */
    @FXML
    private void handleSaveNewFile(ActionEvent actionEvent)  {
        //Opens the default file explore
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File explore");
        //Filter what type of files should be shown in the file explore.
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        file = fileChooser.showOpenDialog(stage);
        //If a file is selected. Copy it to the resource folder.
        if (file != null) {

            try {
                Files.copy(file.toPath(), target.resolve(file.toPath().getFileName()), REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String filename=file.toPath().getFileName().toString();
            LocalDate saveDate= LocalDate.now();




            ProjectFiles projectFiles=new ProjectFiles(1,selectedProject.getId(),filename ,"Resources/Pictures/ImagesSavedFromTechnicians/"+filename,saveDate,null,null);
            try {
                projectFilesModel.createNewFile(projectFiles);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public void handleOpenCustomerDoc()  {

        ArrayList<String> imagePath = new ArrayList<>();

        for (ProjectFiles projectFiles : fileTable.getItems()) {
            if (projectFiles.getUsedBox().isSelected())
                imagePath.add(projectFiles.getFilePath());
        }
        HashMap<CustomerInfo, String> customerMap = makeCustomerMap();
        CustomerPdf customerPdf = new CustomerPdf(imagePath, customerMap, selectedProject.getNote(), selectedProject.getTitle());
        String path = null;

        try {
            path = customerPdf.makePdf();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ShowFile showFile = new ShowFile();
        showFile.showFile(path);
    }



    public void handleOpenMainWindow (ActionEvent actionEvent)  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/MainWindow.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pane.getStylesheets().add(PersonTypeChooser.personTypes.getCSS());
        mainViewAnchorPane.getChildren().setAll(pane);

        MainController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();
    }

    public void handleDraw (ActionEvent actionEvent)  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/Draw/DrawWindow.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            displayError(e);
            throw new RuntimeException(e);
        }
        root.getStylesheets().add("/GUI/View/Draw/DrawWindow.css");

        DrawController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();

        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleDeleteFileComputer ()  {


        ProjectFiles fileToDelete = fileTable.getSelectionModel().getSelectedItem();

        try {
            projectFilesModel.deleteFile(fileToDelete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        File file = new File(fileToDelete.getFilePath());
        file.delete();
    }



    /**
     * Delete a selected image files from the list.
     * @param actionEvent
     *
     */



        public void handleDeleteFile (ActionEvent actionEvent)  {
            if (selectedfile != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete File");
                //alert.setContentText("Delete?");
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);

                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {

                        handleDeleteFileComputer();
                        try {
                            projectFilesModel.deleteFile(selectedfile);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }


                        filesPreviewImageView.setImage(null);
                    }

                });

            }
        }


    /**
     * Return to main view
     * @param actionEvent
     */
    @FXML
    private void handleAddTech (ActionEvent actionEvent){
        //Setting the data for the variables and calls the method from the model.

        if (listTechsComboBox.getSelectionModel().getSelectedItem()!=null)
        {
            int projectID = selectedProject.getId();
            int technicianID = listTechsComboBox.getSelectionModel().getSelectedItem().getId();

            try {
                userModel.moveTechnician(technicianID, projectID);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Handle what happens when the "Remove technician" button is clicked.
     * Removes a selected user/employee from a selected project.
     * @param actionEvent
     */
    @FXML
    private void handleRemoveTech (ActionEvent actionEvent){
        //Setting the data for the variables and calls the method from the model.

        if (techsOnProjectListView.getSelectionModel().getSelectedItem()!=null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Technician");
            //alert.setContentText("Delete?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    User selectedTechnician = techsOnProjectListView.getSelectionModel().getSelectedItem();
                    int projectID = selectedProject.getId();

                    try {
                        userModel.removeTechnicianFromProject(selectedTechnician, projectID);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }
            });

        }
        }

}


