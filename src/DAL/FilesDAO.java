package DAL;

import BE.ProjectFiles;
import DAL.PictureClasses.ImageViewKlient;
import DAL.PictureClasses.LilleJpeg;
import DAL.PictureClasses.LilleJpg;
import DAL.PictureClasses.LillePng;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilesDAO implements iFileDataAccess {

    private DatabaseConnector databaseConnector;

    /**
     * Constructor for the class "FilesDAO".
     * @throws IOException
     */
    public FilesDAO() throws IOException {databaseConnector = DatabaseConnector.getInstance();}

    /**
     * Getting a list of files from the database based on the project ID.
     * @param projectID
     * @return
     * @throws Exception
     */
    public List<ProjectFiles> loadFilesFromAProject(int projectID) throws Exception {

        ArrayList<ProjectFiles> loadFilesFromAProject = new ArrayList<>();
        ImageView picture;
        ImageViewKlient pictureFrame = null;
        //SQL query
        String sql = "SELECT * FROM ProjectFile WHERE ProjectID =?";


        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, projectID);

            ResultSet rs = stmt.executeQuery();
            //Getting the information from the database.
            while (rs.next()) {
                int id = rs.getInt("ID");
                int projectID1 = rs.getInt("ProjectID");
                String name = rs.getString("Name");
                String filePath = rs.getString("FilePath");
                LocalDate date = rs.getDate("Date").toLocalDate();
                byte usedInDoc = rs.getByte("usedInDoc");


                String filetype = filePath.substring(filePath.length() - 4, filePath.length());

                switch (filetype) {
                    case ".jpg":
                        pictureFrame = new ImageViewKlient(new LilleJpg());
                        break;

                    case ".png":
                        pictureFrame = new ImageViewKlient(new LillePng());
                        break;

                    case "jpeg":
                        pictureFrame = new ImageViewKlient(new LilleJpeg());
                        break;

                }

                picture = pictureFrame.getImageView();

                //Creating the checkbox and set it as empty.
                CheckBox checkBox = new CheckBox();


                if (usedInDoc == 0)
                    checkBox.setSelected(true);


                ProjectFiles files = new ProjectFiles(id, projectID1, name, filePath, date, picture, checkBox);

                loadFilesFromAProject.add(files);
            }
            return loadFilesFromAProject;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to retrieve files from database", ex);
        }

    }

    /**
     * Update the usability status of the files.
     * @param usedInDoc
     * @param id
     * @throws Exception
     */
    public void updateUsedInDoc(Boolean usedInDoc, int id) throws Exception {

        byte used;
        //SQL query and getting connection to the database.
        String sql = "UPDATE ProjectFile SET usedInDoc = ? WHERE ID = ?";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            if (usedInDoc) {
                used=0;
            } else {
                used = 1;
            }

            // Bind parameters

            stmt.setByte(1, used);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not update fileList", ex);
        }
    }

    public ProjectFiles createNewFile(ProjectFiles file) throws SQLException {
        //SQL Query
        String sql = "INSERT INTO ProjectFile(ProjectID, Name, FilePath, Date, UsedInDoc) VALUES (?,?,?,?,?)";
        //Getting connection to the database.
        try(Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query.
            stmt.setInt(1, file.getProjectID());
            stmt.setString(2, file.getName());
            stmt.setString(3, file.getFilePath());
            stmt.setDate(4, Date.valueOf(file.getDate()));
            stmt.setInt(5, 0);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                file.setId(rs.getInt(1));
            }
            return file;
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SQLException("Could not add file to database", ex);
        }
    }
    public void deleteFile(ProjectFiles file) throws SQLException {
        //SQL query
        String sql = "DELETE FROM ProjectFile WHERE ID = ?";
        //Getting connection to the database.
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameter and executing the query.
            stmt.setInt(1, file.getId());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SQLException("Could not delete the file from the database", ex);
        }
    }
    public void updateFileOrder(int fileId, int newOrder) throws SQLException {
            String sql = "UPDATE ProjectFiles SET FileOrder = ? WHERE ID = ?";
        try (Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newOrder);
            stmt.setInt(2, fileId);
            stmt.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SQLException("Could not delete the file from the database", ex);
        }
    }

}





























