package GUI.Controller;

import BE.*;
import GUI.Model.*;
import PersonsTypes.PersonTypeChooser;
import UTIL.CustomerPdf;
import UTIL.ShowFile;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ProjectController extends BaseController {

    public TextArea NotesTextArea;
    @FXML // Main view for this window
    private AnchorPane mainViewAnchorPane;

    @FXML //All buttons within this window
    private Button deleteFileButton, saveFileButton, saveNoteButton, addTechButton,removeTechButton, drawButton, openMainWindowButton;

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
    private TableColumn  filesPictureColumn, filesNameColumn, filesDateColumn, filesIncludedColumn;

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
    private int projectNumber;
    private boolean onOpenProjectList;

    PersonTypeChooser personTypeChooser = new PersonTypeChooser();

    /**
     * Set up the view when the view is getting shown.
     */
    @Override
    public void setup() {
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
        NotesTextArea.setText(selectedProject.getNote());
        NotesTextArea.setWrapText(true);
    }

    private void pictureToButton() {
        String[] listOfFiles = {"Pictures/Arrow.png","Pictures/Arrow2.png"};

        String[] listOfToolTips = {"Add technician to project", "Remove technician from project"};

        Button[] listOfButtons ={addTechButton, removeTechButton};

        for (int i = 0; i < listOfFiles.length; i++) {

            Image img = new Image(listOfFiles[i]);
            ImageView view = new ImageView(img);
            Tooltip tip = new Tooltip(listOfToolTips[i]);

            listOfButtons[i].setGraphic(view);
            listOfButtons[i].setTooltip(tip);
        }
    }


    /**
     * Listener for the tableview containing files for opening files.
     */
    public void listenerMouseClickPicture()
    {
        fileTable.setOnMouseClicked(event -> {
             selectedfile = fileTable.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2) { //Her vises filen, når man dobbeltklikker.
                try {

                    ShowFile showFile=new ShowFile();
                    showFile.showFile(selectedfile.getFilePath());
                } catch (Exception e) {
                    displayError(e);
                }
            }

        });
    }

    /**
     * Open a file with the standard desktop program.
     */


    /**
     * Set up the files information column in the tableview.
     */
    @FXML
    private void setupFiles() {
            int projectNumber = selectedProject.getId();
            filesPictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
            filesNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            filesDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            filesIncludedColumn.setCellValueFactory(new PropertyValueFactory<>("usedBox"));
            try {
                fileTable.setItems(projectFilesModel.getAllFilesFromProject(projectNumber));
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
            projectFilesModel.fileLoopStop(); //Stopper tidligere løkker i projectFiles inden ny startes
            projectFilesModel.observer(); //Her startes en løkke, der observere ændringer i CheckBox
    }


    @FXML
    private void openFileAction(ActionEvent actionEvent) {
    }


    @FXML
    private void saveNoteAction(ActionEvent actionEvent) {

        String note= NotesTextArea.getText();

        try {
            projectModel.changeNote(note,selectedProject.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Handle what happens when the "Add technician" button is clicked.
     * Adds a selected user/employee to a selected project.
     * @param actionEvent
     */
    @FXML
    private void addTechnicianAction(ActionEvent actionEvent) {
        //Setting the data for the variables and calls the method from the model.
        int projectID = selectedProject.getId();
        //int technicanID = lstTechnicians.getSelectionModel().getSelectedItem().getId();
        try {
           // userModel.moveTechnician(technicanID, projectID);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Handle what happens when the "Remove technician" button is clicked.
     * Removes a selected user/employee from a selected project.
     * @param actionEvent
     */
    @FXML
    private void removeTechnicianAction(ActionEvent actionEvent) {
        //Setting the data for the variables and calls the method from the model.
        User selectedTechnician = techsOnProjectListView.getSelectionModel().getSelectedItem();
        int projectID = selectedProject.getId();
        try {
            userModel.removeTechnicianFromProject(selectedTechnician, projectID);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }




    /**
     * Set up the information about the customer in the main view when a project is selected.
     */

    private HashMap<String,String> makeCustomerMap() {

        HashMap<String,String> customerInfo = new HashMap<>();

        if (selectedProject != null) {
            //Setting the information in the labels.
            int customerID = selectedProject.getCustomerID();
            Customer customer = null;
            try {
                customer = customerModel.loadCustomer(customerID);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }


            customerInfo.put("FirstName",customer.getFirstName());
            customerInfo.put("Address",customer.getAddress());
            customerInfo.put("ZipCode",String.valueOf(customer.getZipCode()));
            customerInfo.put("Mail",customer.getMail());
            customerInfo.put("PhoneNumber",String.valueOf(customer.getPhoneNumber()));

        }
                return customerInfo;
    }


    

    /**
     * Set up the information in the technician column in the table view, based on a selected project.
     */
    private void setupTechniciansOnProject() {
        if (selectedProject != null) {
            int projectId = selectedProject.getId();
            try {
                techsOnProjectListView.setItems(userModel.getAllTechniciansOnProject(projectId));
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
    }

    /**
     * Save a new file from the users pc.
     * @param actionEvent
     */
    @FXML
    private void handleSaveNewFile(ActionEvent actionEvent) {
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
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
    }


    /*
    public void handleOpenCustomerDoc(ActionEvent actionEvent) throws FileNotFoundException, MalformedURLException, MalformedURLException, FileNotFoundException {

        ArrayList<String> imagePath=new ArrayList<>();

        for(ProjectFiles projectFiles : fileTable.getItems())
        {
            if(projectFiles.getUsedBox().isSelected())
                imagePath.add(projectFiles.getFilePath());
        }


        HashMap<String, String> customerMap=makeCustomerMap();
        CustomerPdf customerPdf=new CustomerPdf(imagePath,customerMap, selectedProject.getNote());
        customerPdf.makePdf();

        ShowFile showFile=new ShowFile();
        showFile.showFile("installations dokumentation.pdf");

    }

*/
    public void handleDeleteFile(ActionEvent actionEvent) {
    }


    public void handleOpenMainWindow(ActionEvent actionEvent) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/ProjectManager/NytVindue1.fxml"));
        AnchorPane pane = loader.load();
        pane.getStylesheets().add("/GUI/View/ProjectManager/managerView.css");
        mainViewAnchorPane.getChildren().setAll(pane);

        NyController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();
    }

    public void handleDraw(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/Paint/PaintView.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add("/GUI/View/Paint/Paint.css");

        PaintController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();

        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.show();
    }
}
