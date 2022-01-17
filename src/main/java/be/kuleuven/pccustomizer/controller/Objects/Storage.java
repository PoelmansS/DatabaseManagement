package be.kuleuven.pccustomizer.controller.Objects;

public class Storage {
    private String name;
    private String type;
    private int price;
    private int size;
    private int readSpeed;
    private int writeSpeed;
    private int aantal;

    public Storage(String name, String type, int price, int size, int readSpeed, int writeSpeed) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.size = size;
        this.readSpeed = readSpeed;
        this.writeSpeed = writeSpeed;
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

    public int getReadSpeed() {
        return readSpeed;
    }

    public void setReadSpeed(int readSpeed) {
        this.readSpeed = readSpeed;
    }

    public int getWriteSpeed() {
        return writeSpeed;
    }

    public void setWriteSpeed(int writeSpeed) {
        this.writeSpeed = writeSpeed;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
