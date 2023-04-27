package BE;

public class Customers {
        private int id;
        private String address;
        private int phonenumber;
        private String companyname;
        private int zipcode;
        private String mail;
        private String firstName;
        private String lastName;
        private int room;

    public Customers(int id, String address, int phonenumber, String companyname, int zipcode, String mail, String firstName, String lastName, int room) {
        this.id = id;
        this.address = address;
        this.phonenumber = phonenumber;
        this.companyname = companyname;
        this.zipcode = zipcode;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.room = room;
    }

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

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
