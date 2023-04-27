package DAL;

import BE.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDataAccess {

    private DatabaseConnector databaseConnector;

    public UserDAO() throws IOException {databaseConnector = DatabaseConnector.getInstance();}

    @Override
    public List<User> loadTechnicians() throws Exception {

        ArrayList<User> allTechnicians = new ArrayList<>();
        //SQL Query.
        String sql = "SELECT * FROM Users WHERE Role = 1 AND Is_Deleted IS NULL";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Map DB row to user object
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                int role = rs.getInt("Role");

                User technician = new User(id, firstName, lastName, username, password, role);

                allTechnicians.add(technician);
            }
            return allTechnicians;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to retrieve Technicians from database", ex);
        }
    }

    @Override
    public List<User> loadUser(String name) throws Exception {
        ArrayList<User> allUsers = new ArrayList<>();
        //SQL Query.
        String sql = "SELECT * FROM Users WHERE Username = ?";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Setting the parameters and executing the statement.
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            // Loop through rows from the database result set
            while (rs.next()) {
                //Map DB row to user object
                int id = rs.getInt("Id");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                int role = rs.getInt("Role");

                User user = new User(id, firstName, lastName, username, password, role);

                allUsers.add(user);
            }
            return allUsers;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not get EventKoordinator from database", ex);
        }
    }

    @Override
    public boolean validateUsername(String username) throws Exception {
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
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Failed to validate", e);
        }
        return false;
    }


    @Override
    public User createNewUser(String firstName, String lastName, String username, String password, int role) throws Exception {
        //SQL Query.
        String sql = "INSERT INTO Users (First_Name, Last_Name, Username, Password, Role) VALUES(?,?,?,?,?)";
        //Getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query.
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, username);
            stmt.setString(4, password);
            stmt.setInt(5, role);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if(rs.next()){
                id = rs.getInt(1);
            }
            User user = new User(id, firstName, lastName, username, password, role);
            return user;
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not create User", ex);
        }
    }



    @Override
    public void deleteUser(User selectedUser) throws Exception {

        LocalDate localDate = LocalDate.now();
        //SQL query.
        String sql = "UPDATE Users SET Is_Deleted = ? WHERE ID = ?";
        //Getting the connection to the database.
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameter and executing the query.
            stmt.setDate(1, Date.valueOf(localDate));
            stmt.setInt(2, selectedUser.getId());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not delete this user", ex);
        }
    }
    }

