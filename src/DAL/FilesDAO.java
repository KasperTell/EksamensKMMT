package DAL;

import BE.ProjectFiles;
import DAL.PictureClasses.ImageViewKlient;
import DAL.PictureClasses.LilleJpeg;
import DAL.PictureClasses.LilleJpg;
import DAL.PictureClasses.LillePng;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.*;
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

                CheckBox checkBox = new CheckBox();


                if (usedInDoc == 0)
                    checkBox.setSelected(true);


                ProjectFiles files = new ProjectFiles(id, projectID, name, filePath, date, picture, checkBox);

                loadFilesFromAProject.add(files);
            }
            return loadFilesFromAProject;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to retrieve files from database", ex);
        }

    }

        public void updateUsedInDoc(Boolean usedInDoc, int id) throws Exception {

            byte used;
            String sql = "UPDATE ProjectFile SET usedInDoc = ? WHERE ID = ?";

            try (Connection conn = databaseConnector.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(sql);

                if (usedInDoc) {
                     used=0;
                }
                else {
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



    }





























