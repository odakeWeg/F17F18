package com.edson.routine.handler;

public class SapTestDataHandler {
    private long serial;
    private String family;
    private float current;

    public SapTestDataHandler(long serial, String family, float current) {
        this.serial = serial;
        this.family = family;
        this.current = current;
    }

    public long getSerial() {
        return serial;
    }
    public void setSerial(long serial) {
        this.serial = serial;
    }
    public String getFamily() {
        return family;
    }
    public void setFamily(String family) {
        this.family = family;
    }
    public float getCurrent() {
        return current;
    }
    public void setCurrent(float current) {
        this.current = current;
    }


    @Override
    public String toString() {
        return "{" +
            "serial=" + getSerial() +
            ", family=" + getFamily() +
            ", current=" + getCurrent() +
            "}";
    }


}
