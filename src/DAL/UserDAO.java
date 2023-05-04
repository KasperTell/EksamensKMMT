package DAL;

import BE.ProjectTechnician;
import BE.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDataAccess {

    private DatabaseConnector databaseConnector;

    /**
     * Constructor for the class "UserDAO"
     * @throws IOException
     */
    public UserDAO() throws IOException {
        databaseConnector = DatabaseConnector.getInstance();
    }

    /**
     * Getting a list of users/employees
     * @param roleType
     * @return
     * @throws SQLException
     */
    public List<User> loadUserOfAType(int roleType) throws SQLException {

        ArrayList<User> allUserOfAType = new ArrayList<>();


        //SQL Query.
        String sql = "SELECT * FROM Users WHERE Role =? AND Is_Deleted IS NULL";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, roleType);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Getting the info from the database.
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                int role = rs.getInt("Role");

                User technician = new User(id, firstName, lastName, username, password, role);

                allUserOfAType.add(technician);
            }
            return allUserOfAType;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("Failed to retrieve Technicians from database", ex);
        }
    }

    /**
     * Getting a specific user from the database based on a username.
     * @param name
     * @return
     * @throws SQLException
     */
    public User loadUser(String name) throws SQLException {
        User user = null;
        //SQL Query.
        String sql = "SELECT * FROM Users WHERE Username = ?";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Setting the parameters and executing the statement.
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                // Loop through rows from the database result set and getting the information.
                int id = rs.getInt("Id");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                int role = rs.getInt("Role");

                user = new User(id, firstName, lastName, username, password, role);
            }
            return user;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Could not get the user from the database", ex);
        }
    }

    /**
     * Checking if the database has a entry matching the username from the application.
     * @param username
     * @return
     * @throws SQLException
     */
    public boolean validateUsername(String username) throws SQLException {
        //SQL Query.
        String sql = "SELECT * FROM Users WHERE Username = ?";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameters and executing the query.
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to validate", e);
        }
        return false;
    }

    /**
     * Creating a new user/employee in the database.
     * @param user
     * @return
     * @throws Exception
     */
    public User createNewUser(User user) throws SQLException {
        //SQL Query.
        String sql = "INSERT INTO Users (First_Name, Last_Name, Username, Password, Role) VALUES(?,?,?,?,?)";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query.
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUserName());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getRole());
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Could not create User", ex);
        }
    }


    /**
     * Soft deleting a user/employee from the database.
     * @param selectedUser
     * @throws Exception
     */
    public void deleteUser(User selectedUser) throws SQLException {
        LocalDate localDate = LocalDate.now();
        //SQL query.
        String sql = "UPDATE Users SET Is_Deleted = ? WHERE ID = ?";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameter and executing the query.
            stmt.setDate(1, Date.valueOf(localDate));
            stmt.setInt(2, selectedUser.getId());
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Could not delete this user", ex);
        }
    }

    /**
     * Remove a user/employee from a project.
     * @param selectedTechnician
     * @param projectID
     * @throws Exception
     */
    public void removeTechnicianFromProject(User selectedTechnician, int projectID) throws SQLException{
        //SQL Query
        String sql = "DELETE FROM ProjectTechnician WHERE ProjectID = ? AND UserID = ?";
        try (Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query.
            stmt.setInt(1, projectID);
            stmt.setInt(2, selectedTechnician.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Something went wrong while removing a technician from a project.", ex);
        }
    }

    /**
     * Get a list of all users/employees working on a specific project.
     * @param projectID
     * @return
     * @throws SQLException
     */
    public List<User> filterTechnicianById(int projectID) throws SQLException {
        ArrayList<User> allUsers = new ArrayList<>();
        //SQL query and database connection.
        String sql = "SELECT * FROM ProjectTechnician INNER JOIN Users ON ProjectTechnician.UserID = Users.ID WHERE ProjectID = ?;";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, projectID);
            ResultSet rs = stmt.executeQuery();
            //Getting the information from the database.
            while (rs.next()) {
                int id = rs.getInt("UserID");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                int role = rs.getInt("Role");
                User user = new User(id, firstName, lastName, username, password, role);
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Could not move Technician", ex);
        }
    }

    /**
     * Assign a user/employee to a project through the database.
     * @param technicianID
     * @param projectID
     * @return
     * @throws Exception
     */
    public ProjectTechnician moveTechnician(int technicianID, int projectID) throws SQLException {
        //SQL Query
        String sql = "INSERT INTO ProjectTechnician (ProjectID, UserID) VALUES (?,?)";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query.
            stmt.setInt(1, projectID);
            stmt.setInt(2, technicianID);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            ProjectTechnician projectTechnician = new ProjectTechnician(id, technicianID, projectID);
            return projectTechnician;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Could not move Technician", ex);
        }
    }

}

