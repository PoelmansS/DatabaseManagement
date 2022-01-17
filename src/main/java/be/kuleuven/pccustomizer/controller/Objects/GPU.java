package be.kuleuven.pccustomizer.controller.Objects;

public class GPU {
    private String name;
    private int price;
    private int VRAM;
    private int powerUsage;
    private int NRofSlots;
    private int aantal;


    public GPU(String name, int price, int VRAM, int powerUsage, int NRofSlots, int aantal) {
        this.name = name;
        this.price = price;
        this.VRAM = VRAM;
        this.powerUsage = powerUsage;
        this.NRofSlots = NRofSlots;
        this.aantal = aantal;
    }


    public int getNRofSlots() {
        return NRofSlots;
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
