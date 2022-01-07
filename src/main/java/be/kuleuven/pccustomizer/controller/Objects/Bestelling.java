package be.kuleuven.pccustomizer.controller.Objects;

public class Bestelling {
    int ID;
    int Klant;
    String Computer;
    int Price;

    public Bestelling(int ID, int klant, String computer, int price) {
        this.ID = ID;
        Klant = klant;
        Computer = computer;
        Price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getKlant() {
        return Klant;
    }

    public void setKlant(int klant) {
        Klant = klant;
    }

    public String getComputer() {
        return Computer;
    }

    public void setComputer(String computer) {
        Computer = computer;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
