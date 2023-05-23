package BE;

public class User {

    private int id, role;
    private String firstName, lastName, username, password;

    /**
     * Constructor for the class "User".
     * @param id
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param role
     */
    public User(int id, String firstName, String lastName, String username, String password, int role){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Getters and setter for the entire class.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() { return role;}

    /**
     * Overriding the default toString method, so it returns the user/employees firstname and lastname.
     * @return
     */
    @Override
    public String toString() {
        return firstName + " " + lastName ;
    }
}
