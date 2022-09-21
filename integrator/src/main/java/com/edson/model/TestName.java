package com.edson.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TestName {
    //private final SimpleBooleanProperty selected;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty step;

    public TestName(String name, Integer step) {
        this.name = new SimpleStringProperty(name);
        this.step = new SimpleIntegerProperty(step);
    }


    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setNome(String name) {
        this.name.set(name);
    }

    public int getStep() {
        return step.get();
    }

    public SimpleIntegerProperty stepProperty() {
        return step;
    }

    public void setStep(int step) {
        this.step.set(step);
    }

}
