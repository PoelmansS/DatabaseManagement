package be.kuleuven.pccustomizer.controller.Objects;

public class PSU {
    private String name;
    private int price;
    private int wattage;
    private int aantal;

    public PSU(String name, int price, int wattage, int aantal) {
        this.name = name;
        this.price = price;
        this.wattage = wattage;
        this.aantal = aantal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
