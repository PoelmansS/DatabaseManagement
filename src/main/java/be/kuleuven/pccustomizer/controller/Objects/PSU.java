package be.kuleuven.pccustomizer.controller.Objects;

public class PSU {
    String name;
    int price;
    int wattage;

    public PSU(String name, int price, int wattage) {
        this.name = name;
        this.price = price;
        this.wattage = wattage;
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
}
