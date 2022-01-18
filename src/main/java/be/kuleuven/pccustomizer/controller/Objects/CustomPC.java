package be.kuleuven.pccustomizer.controller.Objects;

import be.kuleuven.pccustomizer.controller.Objects.*;

public class CustomPC {
    private String name;
    private String type;
    private int price;
    private String motherBoard;
    private String cpu;
    private String gpu;
    private String ram;
    private String cases;
    private String psu;
    private String storage;
    private String cooling;
    private String extra;

    public CustomPC(){}

    public CustomPC(String name, String type, Integer price, String motherBoard, String cpu, String gpu, String ram, String cases, String psu, String storage, String cooling, String extra) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.motherBoard = motherBoard;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.cases = cases;
        this.psu = psu;
        this.storage = storage;
        this.cooling = cooling;
        this.extra = extra;
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


    public String getMotherBoard() {
        return motherBoard;
    }

    public void setMotherBoard(String motherBoard) {
        this.motherBoard = motherBoard;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getPsu() {
        return psu;
    }

    public void setPsu(String psu) {
        this.psu = psu;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getCooling() {
        return cooling;
    }

    public void setCooling(String cooling) {
        this.cooling = cooling;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
