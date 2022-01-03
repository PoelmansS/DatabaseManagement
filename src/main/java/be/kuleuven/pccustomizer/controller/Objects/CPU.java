package be.kuleuven.pccustomizer.controller.Objects;

public class CPU {
    String name;
    int price;
    int threads;
    int cores;
    int clockSpeed;
    int powerUsage;

    public CPU(String name, int price, int threads, int cores, int clockSpeed, int powerUsage) {
        this.name = name;
        this.price = price;
        this.threads = threads;
        this.cores = cores;
        this.clockSpeed = clockSpeed;
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

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public int getClockSpeed() {
        return clockSpeed;
    }

    public void setClockSpeed(int clockSpeed) {
        this.clockSpeed = clockSpeed;
    }

    public int getPowerUsage() {
        return powerUsage;
    }

    public void setPowerUsage(int powerUsage) {
        this.powerUsage = powerUsage;
    }
}
