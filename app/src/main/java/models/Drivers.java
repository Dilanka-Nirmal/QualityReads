package models;

public class Drivers {
    String id;
    String name;
    String email;
    String vehicleno;
    String contact;
    String address;
    String image;

    public Drivers() {
    }

    public Drivers(String id, String name, String email, String vehicleno, String contact, String address, String image) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.vehicleno = vehicleno;
        this.contact = contact;
        this.address = address;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
