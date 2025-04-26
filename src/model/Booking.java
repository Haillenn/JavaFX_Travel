package model;

import javafx.beans.property.*;

public class Booking {
    private final IntegerProperty bookingID;
    private final StringProperty userEmail;
    private final StringProperty tourName;
    private final StringProperty bookingDate;
    private final IntegerProperty people;
    private final StringProperty note;
    private final StringProperty status;

    public Booking(int bookingID, String userEmail, String tourName, String bookingDate, int people, String note, String status) {
        this.bookingID = new SimpleIntegerProperty(bookingID);
        this.userEmail = new SimpleStringProperty(userEmail);
        this.tourName = new SimpleStringProperty(tourName);
        this.bookingDate = new SimpleStringProperty(bookingDate);
        this.people = new SimpleIntegerProperty(people);
        this.note = new SimpleStringProperty(note);
        this.status = new SimpleStringProperty(status);
    }

    public int getBookingID() { return bookingID.get(); }
    public IntegerProperty bookingIDProperty() { return bookingID; }

    public String getUserEmail() { return userEmail.get(); }
    public StringProperty userEmailProperty() { return userEmail; }

    public String getTourName() { return tourName.get(); }
    public StringProperty tourNameProperty() { return tourName; }

    public String getBookingDate() { return bookingDate.get(); }
    public StringProperty bookingDateProperty() { return bookingDate; }

    public int getPeople() { return people.get(); }
    public IntegerProperty peopleProperty() { return people; }

    public String getNote() { return note.get(); }
    public StringProperty noteProperty() { return note; }

    public String getStatus() { return status.get(); }
    public StringProperty statusProperty() { return status; }
}
