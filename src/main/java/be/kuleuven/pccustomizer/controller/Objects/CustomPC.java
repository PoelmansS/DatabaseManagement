package be.kuleuven.pccustomizer.controller.Objects;

import be.kuleuven.pccustomizer.controller.Objects.*;

public class CustomPC {
    String name;
    String type;
    int price;
    MotherBoard motherBoard;
    CPU cpu;
    GPU gpu;
    RAM ram;
    Case cases;
    PSU psu;
    Storage storage;
    Cooling cooling;

    public CustomPC(String name, String type, int price, MotherBoard motherBoard, CPU cpu, GPU gpu, RAM ram, Case cases, PSU psu, Storage storage, Cooling cooling) {
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

    public void setPrice() {
        this.price = motherBoard.getPrice() +
                cpu.getPrice() +
                gpu.getPrice() +
                ram.getPrice() +
                cases.getPrice() +
                psu.getPrice() +
                storage.getPrice() +
                cooling.getPrice();
    }

    public MotherBoard getMotherBoard() {
        return motherBoard;
    }

    public void setMotherBoard(MotherBoard motherBoard) {
        this.motherBoard = motherBoard;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public GPU getGpu() {
        return gpu;
    }

    public void setGpu(GPU gpu) {
        this.gpu = gpu;
    }

    public RAM getRam() {
        return ram;
    }

    public void setRam(RAM ram) {
        this.ram = ram;
    }

    public Case getCases() {
        return cases;
    }

    public void setCases(Case cases) {
        this.cases = cases;
    }

    public PSU getPsu() {
        return psu;
    }

    public void setPsu(PSU psu) {
        this.psu = psu;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Cooling getCooling() {
        return cooling;
    }

    public void setCooling(Cooling cooling) {
        this.cooling = cooling;
    }
}
