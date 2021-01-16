package de.rnd7.owservermqttgw.messages;

public class SensorMessage extends Message {

    private double value;

    public SensorMessage(final String topic, final double value) {
        super(topic);
        this.value = value;
    }

    public String getValueString() {
        return "" + value;
    }

    public double getValueDouble() {
        return value;
    }

}
