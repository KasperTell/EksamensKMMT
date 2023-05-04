package DAL;

import BE.Project;

import java.io.IOException;
import java.sql.*;
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
                String note = rs.getString("note");


                boolean open1;

                if (openClose==0)
                    open1=true;
                            else
                    open1=false;


                Project project = new Project(id, title, customerID, date, open1, note);

                loadProjectofAType.add(project);
            }
            return loadProjectofAType;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to retrieve all Projects from database", ex);
        }
    }

    public Project createNewProject(Project project) throws SQLException {
        String sql = "INSERT INTO Project (Title, customerID, Date, OpenClose) VALUES (?,?,?,?)";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query.
            stmt.setString(1, project.getTitle());
            stmt.setInt(2, project.getCustomernumber());
            System.out.println(project.getCustomernumber());
            stmt.setDate(3, Date.valueOf(project.getDate()));
            stmt.setInt(4, 0);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                project.setId(rs.getInt(1));
            }
        }
        return project;
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
        throw new SQLException("Could not edit project status");
    }
    }

    public ArrayList<Project> searchByQuery(String query) throws Exception {

        ArrayList<Project> projects = new ArrayList<>();
        //SQL Query.
        String sql = "SELECT * FROM Project INNER JOIN Customers ON Project.customerID = Customers.ID WHERE Customers.Address LIKE ?";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Setting the parameters and executing the statement.
            stmt.setString(1, "%" + query + "%");


            ResultSet rs = stmt.executeQuery();



            // Loop through rows from the database result set
            while (rs.next()) {
                //Map DB row to user object
                int id = rs.getInt("ID");
                String title = rs.getString("title");
                int customerID = rs.getInt("customerID");
                LocalDate date = rs.getDate("date").toLocalDate();
                boolean openClose = rs.getBoolean("OpenClose");
                String note = rs.getString("note");

                Project project = new Project(id, title, customerID, date, openClose, note);

                projects.add(project);

            }
            System.out.println(projects.size() + "Saerch DAL");
            return projects;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not get EventKoordinator from database", ex);
        }

    }


    /*
    public Project saveNote(String note, int projectID) throws Exception {
        String sql = "INSERT INTO Project_Note WHERE Project_ID = ?";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, note);
            stmt.setInt(2, projectID);
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Could not edit project status");
        }
        return saveNote(note, projectID);
    }
    */
    public void saveNote(String note, int id, int customerID) throws Exception {
        String sql = "INSERT INTO Project (Note) SELECT ? FROM Project WHERE ID = ? AND customerID = ?";
        try (Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, note);
            stmt.setInt(2, id);
            stmt.setInt(3, customerID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error saving note: " + e.getMessage());
        }
    }

}

