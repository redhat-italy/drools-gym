package it.ugolandini.drools.model;

import org.drools.core.util.bitmask.BitMask;
import org.drools.core.util.bitmask.OpenBitSet;

public class BitmaskExample {

    public BitmaskExample(int id) {
        this.id = id;
        bitMask = new OpenBitSet(16);
    }

    @Override
    public String toString() {
        return "BitmaskExample{" +
                "id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void eventFired(Event event) {
        bitMask.set(event.ordinal());
    }

    public boolean isEventFired(Event event) {
        return bitMask.isSet(event.ordinal());
    }

    public BitMask getBitMask() {
        return bitMask;
    }

    private int id;
    private BitMask bitMask;

}
