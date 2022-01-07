package be.kuleuven.pccustomizer.controller.Objects;

public class Cooling {
    String Name;
    String Type;
    int Price;
    int Wattage;

    public Cooling(String name, String type, int price, int wattage) {
        Name = name;
        Type = type;
        Price = price;
        Wattage = wattage;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getWattage() {
        return Wattage;
    }

    public void setWattage(int wattage) {
        Wattage = wattage;
    }
}
