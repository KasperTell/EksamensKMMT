package BE;

public class Customer {
    private int id, phoneNumber, zipCode;
    private String firstName, lastName, companyName, address, mail,town;

    /**
     * Constructor for the class "Customer"
     * @param id
     * @param firstName
     * @param lastName
     * @param companyName
     * @param address
     * @param mail
     * @param phoneNumber
     * @param zipCode
     * @param town
     */
    public Customer(int id, String firstName, String lastName, String companyName, String address, String mail, int phoneNumber, int zipCode, String town) {
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.zipCode = zipCode;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.town=town;
    }


    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    /**
     * Getters and setters for the entire class.
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Overriding the default toString method, so it now returns the customers first name, last name and the company name.
     * @return
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " " + companyName;
    }
}
