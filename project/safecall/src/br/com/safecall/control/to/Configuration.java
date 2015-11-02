/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.control.to;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Configuration {
    private Long seconds;
    private String message;
    private ArrayList<String> telephones = new ArrayList<String>();

    /**
     * @return the seconds
     */
    public Long getSeconds() {
        return seconds;
    }

    /**
     * @param seconds the seconds to set
     */
    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the telephones
     */
    public ArrayList<String> getTelephones() {
        return telephones;
    }
}
