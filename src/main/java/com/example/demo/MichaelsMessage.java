package com.example.demo;

import java.io.Serializable;

/**
 * @author michael.hug@fiserv.com
 * Fiserv Internal Software
 */
public class MichaelsMessage implements Serializable {
    private String michaelsMessage;

    public MichaelsMessage() {
    }

    public MichaelsMessage(String michaelsMessage) {
        this.michaelsMessage = michaelsMessage;
    }

    @Override
    public String toString() {
        //return "MichaelsMessage{" + "michaelsMessage=" + michaelsMessage + '}';
        return michaelsMessage;
    }
}