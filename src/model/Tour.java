package model;

import javafx.beans.property.*;

public class Tour {
    private final IntegerProperty tourID;
    private final StringProperty name;
    private final StringProperty time;
    private final StringProperty description;
    private final StringProperty price;
    private final StringProperty image;

    public Tour(int tourID, String name, String time, String description, String price, String image) {
        this.tourID = new SimpleIntegerProperty(tourID);
        this.name = new SimpleStringProperty(name);
        this.time = new SimpleStringProperty(time);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleStringProperty(price);
        this.image = new SimpleStringProperty(image);
    }

    public int getTourID() { return tourID.get(); }
    public IntegerProperty tourIDProperty() { return tourID; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public String getTime() { return time.get(); }
    public StringProperty timeProperty() { return time; }

    public String getDescription() { return description.get(); }
    public StringProperty descriptionProperty() { return description; }

    public String getPrice() { return price.get(); }
    public StringProperty priceProperty() { return price; }

    public String getImage() { return image.get(); }
    public StringProperty imageProperty() { return image; }
}
