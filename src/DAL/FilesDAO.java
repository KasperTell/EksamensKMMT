package DAL;

import BE.ProjectFiles;
import PersonsTypes.ImageViewKlient;
import PersonsTypes.LillePng;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilesDAO {

    private DatabaseConnector databaseConnector;

    public FilesDAO() throws IOException {databaseConnector = DatabaseConnector.getInstance();}

    public List<ProjectFiles> loadFilesFromAProject(int projectID) throws Exception {

        ArrayList<ProjectFiles> loadFilesFromAProject = new ArrayList<>();
        ImageView picture;
        ImageViewKlient pictureFrame = null;



        String sql = "SELECT * FROM ProjectFile WHERE ProjectID =?";


        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, projectID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Map DB row to user object
                int id = rs.getInt("ID");
                int projectID1 = rs.getInt("ProjectID");
                String name = rs.getString("Name");
                String filePath = rs.getString("FilePath");
                LocalDate date = rs.getDate("Date").toLocalDate();
                byte usedInDoc = rs.getByte("usedInDoc");



                String filetype=filePath.substring(filePath.length()-4,filePath.length());
                System.out.println(filetype);

                switch (filetype)
                {
                    case ".jpg":
                        pictureFrame = new ImageViewKlient(new LillePng());
                        break;

                    case "":
                        break;
                }

                picture=pictureFrame.getImageView();

                CheckBox checkBox=new CheckBox();



                ProjectFiles files = new ProjectFiles(id, projectID,name, filePath,date, usedInDoc, picture,checkBox) ;

                loadFilesFromAProject.add(files);
            }
            return loadFilesFromAProject;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to retrieve files from database", ex);
        }
    }




























}
