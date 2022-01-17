package be.kuleuven.pccustomizer.controller.Objects;

public class Case {
    private String name;
    private String type;
    private int price;
    private String size;
    private int aantal;


    public Case(String name, String type, int price, String size, int aantal) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.size = size;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
