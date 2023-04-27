package BE;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int role;


    public User(int id, String firstName, String lastName, String username, String password, int role){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Getters and setters for the entire class.
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

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String user_name) {
        this.username = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole(){return role;}

    /**
     *The customized toString method.
     */
    @Override
    public String toString() {
        return firstName + " " + lastName ;
    }








}
