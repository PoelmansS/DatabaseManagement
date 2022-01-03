package be.kuleuven.pccustomizer.controller.Objects;

public class Klant {
    int ID;
    String lastName;
    String firstName;
    int postalCode;
    String street;
    String number;
    String phone;
    String mail;

    public Klant(int ID, String lastName, String firstName, int postalCode, String street, String number, String phone, String mail) {
        this.ID = ID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.postalCode = postalCode;
        this.street = street;
        this.number = number;
        this.phone = phone;
        this.mail = mail;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
