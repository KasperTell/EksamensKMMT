package DAL;

import BE.Project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectDAO implements IProjectDataAccess{

    private DatabaseConnector databaseConnector;

    public ProjectDAO() throws IOException {databaseConnector = DatabaseConnector.getInstance();}



    public ArrayList<Project> loadProjectOfAType(boolean open) throws Exception {

        ArrayList<Project> loadProjectofAType = new ArrayList<>();


        String sql = "SELECT * FROM Project WHERE OpenClose =?";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);


            byte openClose;

            if (open)
                openClose=0;
            else
                openClose=1;



            stmt.setInt(1, openClose);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Map DB row to user object
                int id = rs.getInt("ID");
                String title = rs.getString("title");
                int customerID = rs.getInt("customerID");
                LocalDate date = rs.getDate("date").toLocalDate();
                openClose = rs.getByte("OpenClose");


                boolean open1;

                if (openClose==0)
                    open1=true;
                            else
                    open1=false;


                Project project = new Project(id, title, customerID, date, open1);

                loadProjectofAType.add(project);
            }
            return loadProjectofAType;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to retrieve all Projects from database", ex);
        }
    }

    public void changeProjectStatus(int projectStatus, int id) throws Exception {
    String sql = "UPDATE Project SET OpenClose = ? WHERE ID = ?";
    try(Connection conn = databaseConnector.getConnection()){
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, projectStatus);
        stmt.setInt(2, id);
        stmt.execute();
    } catch(SQLException ex){
        ex.printStackTrace();
        throw new Exception("Could not edit project status");
    }
    }

}
