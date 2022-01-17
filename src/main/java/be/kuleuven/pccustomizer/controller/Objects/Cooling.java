package be.kuleuven.pccustomizer.controller.Objects;

public class Cooling {
    private String Name;
    private String Type;
    private int Price;
    private int Wattage;
    private int aantal;

    public Cooling(String name, String type, int price, int wattage, int aantal) {
        this.Name = name;
        this.Type = type;
        this.Price = price;
        this.Wattage = wattage;
        this.aantal = aantal;
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

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
