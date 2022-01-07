package be.kuleuven.pccustomizer.controller.Objects;

public class Component {
    String name;

    public Component() {}
    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
