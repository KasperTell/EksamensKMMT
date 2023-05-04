package BE;

import java.time.LocalDate;
import java.util.Date;

public class Project {
    private int id;
    private String title;
    private int customernumber;
    private LocalDate date;
    private boolean open;
    private String note;

    public Project(int id, String title, int customernumber, LocalDate date, boolean open,  String note) {
        this.id = id;
        this.title = title;
        this.customernumber = customernumber;
        this.date = date;
        this.open = open;
        this.note = note;
    }


    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(int customernumber) {
        this.customernumber = customernumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open_close) {
        this.open = open_close;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", customernumber=" + customernumber +
                ", date=" + date +
                ", open=" + open +
                ", note='" + note + '\'' +
                '}';
    }
}
