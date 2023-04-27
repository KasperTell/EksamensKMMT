package BE;

public class Room {

    private int id;
    private int roomnumber;
    private int messurements;
    private String note;
    private String ssid;

    public Room(int id, int roomnumber, int messurements, String note, String ssid) {
        this.id = id;
        this.roomnumber = roomnumber;
        this.messurements = messurements;
        this.note = note;
        this.ssid = ssid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(int roomnumber) {
        this.roomnumber = roomnumber;
    }

    public int getMessurements() {
        return messurements;
    }

    public void setMessurements(int messurements) {
        this.messurements = messurements;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
