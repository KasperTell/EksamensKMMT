package DAL;

import BE.ProjectFiles;

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


        //SQL Query.
        //String sql = "SELECT * FROM Users WHERE Role ="+roleType+"AND Is_Deleted IS NULL";
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



                ProjectFiles files = new ProjectFiles(id,projectID,name,filePath,date,usedInDoc ) ;

                loadFilesFromAProject.add(files);
            }
            return loadFilesFromAProject;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to retrieve files from database", ex);
        }
    }




























}
