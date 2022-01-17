package be.kuleuven.pccustomizer.controller.Objects;

public class RAM {
    String name;
    String type;
    int price;
    int size;
    int NRofSlots;
    int aantal;

    public RAM(String name, String type, int price, int size, int NRofSlots) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.size = size;
        this.NRofSlots = NRofSlots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNRofSlots() {
        return NRofSlots;
    }

    public void setNRofSlots(int NRofSlots) {
        this.NRofSlots = NRofSlots;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
