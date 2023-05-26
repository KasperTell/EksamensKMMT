package DAL;

import BE.ProjectFiles;
import DAL.PictureClasses.ImageViewKlient;
import DAL.PictureClasses.LilleJpeg;
import DAL.PictureClasses.LilleJpg;
import DAL.PictureClasses.LillePng;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
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
    public FilesDAO() throws IOException { databaseConnector = DatabaseConnector.getInstance();}

    /**
     * Getting a list of files from the database based on the project ID.
     * @param projectID
     * @return
     * @throws Exception
     */
    public List<ProjectFiles> loadFilesFromAProject(int projectID) throws FileNotFoundException, SQLException {
        ArrayList<ProjectFiles> loadFilesFromAProject = new ArrayList<>();
        ImageView picture;
        ImageViewKlient pictureFrame = null;
        CheckBox checkBox = null;

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
                checkBox = new CheckBox();
                if (usedInDoc == 0) {
                    checkBox.setSelected(true);
                }
                ProjectFiles files = new ProjectFiles(id, projectID1, name, filePath, date, picture, checkBox);
                loadFilesFromAProject.add(files);
            }
        }
            return loadFilesFromAProject;

    }

    /**
     * Update the usability status of the files.
     * @param usedInDoc
     * @param id
     * @throws SQLException
     */
    public void updateUsedInDoc(Boolean usedInDoc, int id) throws SQLException {
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
            throw new SQLException("Could not update fileList", ex);
        }
    }

    /**
     * Creates a new entry in the database containing information about a specific file.
     * @param file
     * @return
     * @throws SQLException
     */
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

            if(rs.next()) {
                file.setId(rs.getInt(1));
            }
            return file;
        } catch (SQLException ex){
            throw new SQLException("Could not add file to database", ex);
        }
    }

    /**
     * Delete a specific entry from the database based on a selected file.
     * @param file
     * @throws SQLException
     */
    public void deleteFile(ProjectFiles file) throws SQLException {
        //SQL query
        String sql = "DELETE FROM ProjectFile WHERE ID = ?";
        //Getting connection to the database.
        try(Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameter and executing the query.
            stmt.setInt(1, file.getId());
            stmt.execute();
        } catch (SQLException ex) {
            throw new SQLException("Could not delete the file from the database", ex);
        }
    }


    /**
     * Checking if the database has an entry matching the FilePath from the project.
     * @param filepath
     * @return
     * @throws SQLException
     */
    public boolean doesFileExist(String filepath) throws SQLException {
        //SQL Query.
        String sql = "SELECT * FROM ProjectFile WHERE Filepath = ?";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameters and executing the query.
            stmt.setString(1, filepath);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            throw new SQLException("Failed to check", ex);
        }
        return false;
    }
}