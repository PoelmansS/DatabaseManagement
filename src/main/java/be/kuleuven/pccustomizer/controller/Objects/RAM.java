package be.kuleuven.pccustomizer.controller.Objects;

public class RAM {
    private String name;
    private String type;
    private int price;
    private int size;
    private int NRofSticks;
    private int aantal;

    public RAM(String name, String type, int price, int size, int NRofSticks, int aantal) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.size = size;
        this.NRofSticks = NRofSticks;
        this.aantal = aantal;
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

    public int getNRofSticks() {
        return NRofSticks;
    }

    public void setNRofSticks(int NRofSticks) {
        this.NRofSticks = NRofSticks;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
