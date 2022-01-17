package be.kuleuven.pccustomizer.controller.Objects;

public class Extra {
    private String name;
    private String type;
    private int price;
    private int aantal;

    public Extra(){}

    public Extra(String name, String type, int price, int aantal) {
        this.name = name;
        this.type = type;
        this.price = price;
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

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
