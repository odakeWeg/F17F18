package com.edson.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TestTag {
    //private final SimpleBooleanProperty selected;
    private final SimpleStringProperty command;
    private final SimpleStringProperty attribute1;
    private final SimpleStringProperty attribute2;
    private final SimpleStringProperty attribute3;
    private final SimpleStringProperty attribute4;
    private final SimpleStringProperty attribute5;
    private final SimpleStringProperty attribute6;
    private final SimpleIntegerProperty tagStep;

    public TestTag(String command, String attribute1, String attribute2, String attribute3, String attribute4, String attribute5, String attribute6, Integer tagStep) {
        this.command = new SimpleStringProperty(command);
        this.attribute1 = new SimpleStringProperty(attribute1);
        this.attribute2 = new SimpleStringProperty(attribute2);
        this.attribute3 = new SimpleStringProperty(attribute3);
        this.attribute4 = new SimpleStringProperty(attribute4);
        this.attribute5 = new SimpleStringProperty(attribute5);
        this.attribute6 = new SimpleStringProperty(attribute6);
        this.tagStep = new SimpleIntegerProperty(tagStep);
    }


    public String getCommand() {
        return command.get();
    }

    public SimpleStringProperty commandProperty() {
        return command;
    }

    public void setCommand(String command) {
        this.command.set(command);
    }



    public String getAttribute1() {
        return attribute1.get();
    }

    public SimpleStringProperty attribute1Property() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1.set(attribute1);
    }

    // ----------------------------------------------------------

    public String getAttribute2() {
        return attribute2.get();
    }

    public SimpleStringProperty attribute2Property() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2.set(attribute2);
    }

    // ----------------------------------------------------------

    public String getAttribute3() {
        return attribute3.get();
    }

    public SimpleStringProperty attribute3Property() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3.set(attribute3);
    }

    // ----------------------------------------------------------

    public String getAttribute4() {
        return attribute4.get();
    }

    public SimpleStringProperty attribute4Property() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4.set(attribute4);
    }

    // ----------------------------------------------------------

    public String getAttribute5() {
        return attribute5.get();
    }

    public SimpleStringProperty attribute5Property() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5.set(attribute5);
    }

    // ----------------------------------------------------------

    public String getAttribute6() {
        return attribute6.get();
    }

    public SimpleStringProperty attribute6Property() {
        return attribute6;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6.set(attribute6);
    }

    // ----------------------------------------------------------



    public int getTagStep() {
        return tagStep.get();
    }

    public SimpleIntegerProperty tagStepProperty() {
        return tagStep;
    }

    public void setTagStep(int tagStep) {
        this.tagStep.set(tagStep);
    }

    @Override
    public String toString() {
        return "{" +
            "command=" + getCommand() +
            ", attribute1=" + getAttribute1() +
            ", attribute2=" + getAttribute2() +
            ", attribute3=" + getAttribute3() +
            ", attribute4=" + getAttribute4() +
            ", attribute5=" + getAttribute5() +
            ", attribute6=" + getAttribute6() +
            ", tagStep=" + getTagStep() +
            "}";
    }

}
