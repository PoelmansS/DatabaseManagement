package be.kuleuven.pccustomizer.controller.Objects;

public class GPU {
    String name;
    int price;
    int VRAM;
    int powerUsage;

    public GPU(String name, int price, int VRAM, int powerUsage) {
        this.name = name;
        this.price = price;
        this.VRAM = VRAM;
        this.powerUsage = powerUsage;
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

    public int getVRAM() {
        return VRAM;
    }

    public void setVRAM(int VRAM) {
        this.VRAM = VRAM;
    }

    public int getPowerUsage() {
        return powerUsage;
    }

    public void setPowerUsage(int powerUsage) {
        this.powerUsage = powerUsage;
    }
}
