package DAL;

import BE.Project;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ProjectDAO implements IProjectDataAccess {

    private DatabaseConnector databaseConnector;
    private Project project;

    /**
     * Constructor for the class "ProjectDAO".
     * @throws IOException
     */
    public ProjectDAO() throws IOException {
        databaseConnector = DatabaseConnector.getInstance();
    }

    /**
     * Gets all projects marked as open from the database.
     * @param open
     * @return
     * @throws SQLException
     */
    public List<Project> loadProjectOfAType(boolean open) throws SQLException {
        ArrayList<Project> loadProjectofAType = new ArrayList<>();
        //SQL query.
        String sql = "SELECT  Project.ID, Project.title, Project.customerID,Project.[Date], Project.OpenClose, Project.Note, " +
                "Customers.Company_Name, Customers.First_Name, Customers.Last_Name from Project " +
                "inner JOIN Customers " +
                "on Project.customerID=Customers.ID " +
                "WHERE OpenClose =?";

        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            byte openClose;
            if (open)
                openClose = 0;
            else
                openClose = 1;

            //Setting the parameter and execute the statement.
            stmt.setInt(1, openClose);
            ResultSet rs = stmt.executeQuery();
            //Getting the information from the database.
            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("title");
                int customerID = rs.getInt("customerID");
                LocalDate date = rs.getDate("date").toLocalDate();
                openClose = rs.getByte("OpenClose");
                String note = rs.getString("note");
                String companyName = rs.getString("Company_Name");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");

                boolean open1;
                if (openClose == 0)
                    open1 = true;
                else
                    open1 = false;

                int days = (int) DAYS.between(date, LocalDate.now());

                if (companyName == null) {
                    companyName = firstName + " " + lastName;
                }

                if (days < 365 * 4) {
                    project = new Project(id, title, customerID, date, open1, note, companyName);
                }
                loadProjectofAType.add(project);
            }
            return loadProjectofAType;
        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve all Projects from database", ex);
        }
    }

    /**
     * Create a new project in the database.
     * @param project
     * @return
     * @throws SQLException
     */
    public Project createNewProject(Project project) throws SQLException {
        //SQL query
        String sql = "INSERT INTO Project (Title, customerID, Date, OpenClose) VALUES (?,?,?,?)";
        //Getting connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query.
            stmt.setString(1, project.getTitle());
            stmt.setInt(2, project.getCustomerID());
            stmt.setDate(3, Date.valueOf(project.getDate()));
            stmt.setInt(4, 0);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                project.setId(rs.getInt(1));
            }
            return project;
        } catch (SQLException ex) {
            throw new SQLException("Could not create new project in the database", ex);
        }
    }

    /**
     * Changing the project status in the database.
     * @param projectStatus
     * @param id
     * @throws SQLException
     */
    public void changeProjectStatus(int projectStatus, int id) throws SQLException {
        //SQL Query and getting database connection.
        String sql = "UPDATE Project SET OpenClose = ? WHERE ID = ?";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameters and executing the statement.
            stmt.setInt(1, projectStatus);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException ex) {
            throw new SQLException("Could not edit project status", ex);
        }
    }

    /**
     * Changing the project note in the database.
     * @param note
     * @param id
     * @throws SQLException
     */
    public void changeNote(String note, int id) throws SQLException {
        //SQL Query and getting database connection.
        String sql = "UPDATE Project SET Note = ? WHERE ID = ?";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameters and executing the statement.
            stmt.setString(1, note);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException ex) {
            throw new SQLException("Could not update the note", ex);
        }
    }

    /**
     * Gets a list of all projects assigned to a specific user.
     *
     * @param technicianID
     * @return
     * @throws SQLException
     */
    public List<Project> allProjectsForTechnician(int technicianID) throws SQLException {
        ArrayList<Project> projectsForTechnician = new ArrayList<>();
        //SQL Query
        String sql = "SELECT * FROM Project INNER JOIN ProjectTechnician ON Project.ID = ProjectTechnician.ProjectID" +
                " INNER JOIN Customers ON Project.customerID = Customers.ID WHERE UserID = ? AND OpenClose = 0 AND Deleted IS NULL";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameter
            stmt.setInt(1, technicianID);
            //Getting the result from the database
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("title");
                int customerID = rs.getInt("customerID");
                LocalDate date = rs.getDate("date").toLocalDate();
                boolean openClose = rs.getBoolean("OpenClose");
                String note = rs.getString("note");
                String companyName = rs.getString("Company_Name");

                Project project = new Project(id, title, customerID, date, openClose, note, companyName);
                projectsForTechnician.add(project);
            }
            return projectsForTechnician;
        } catch (SQLException ex) {
            throw new SQLException("Could not read all the projects for the logged in technician", ex);
        }
    }

    /**
     * Getting all the projects from the database based on a query.
     *
     * @param query
     * @return
     * @throws SQLException
     */
    public List<Project> searchByQuery(String query) throws SQLException {
        ArrayList<Project> projects = new ArrayList<>();
        //SQL Query.
        String sql = "SELECT * FROM Project INNER JOIN Customers ON Project.customerID = Customers.ID WHERE Customers.First_Name LIKE ? OR Customers.Last_Name LIKE ? OR Customers.Company_Name LIKE ? OR Customers.Address LIKE ? OR Customers.Zip_Code LIKE ? OR Customers.Mail LIKE ? OR Customers.Phone_Number LIKE ?";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Setting the parameters and executing the statement.
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            stmt.setString(3, "%" + query + "%");
            stmt.setString(4, "%" + query + "%");
            stmt.setString(5, "%" + query + "%");
            stmt.setString(6, "%" + query + "%");
            stmt.setString(7, "%" + query + "%");

            ResultSet rs = stmt.executeQuery();
            // Loop through rows from the database result set
            while (rs.next()) {
                //Getting the data.
                int id = rs.getInt("ID");
                String title = rs.getString("title");
                int customerID = rs.getInt("customerID");
                LocalDate date = rs.getDate("date").toLocalDate();
                boolean openClose = rs.getBoolean("OpenClose");
                String note = rs.getString("note");
                String companyName = rs.getString("Company_Name");

                Project project = new Project(id, title, customerID, date, openClose, note, companyName);
                projects.add(project);
            }
            return projects;
        } catch (SQLException ex) {
            throw new SQLException("Failed to return the list of projects based on search query", ex);
        }
    }
}