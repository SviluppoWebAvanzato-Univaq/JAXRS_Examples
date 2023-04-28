package org.univaq.swa.examples.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author SviluppoWebAvanzato
 */
public class AdvancedClass {

    private String s;
    private int i;
    private AdvancedClass c;

    /* 
     * Attenzione: per poter essere deserializzato l'oggetto
     * deve essere dotato di un construttore di default 
     * (senza parametri)
     */
    public AdvancedClass() {
        s = "";
        i = 0;
        c = null;
    }

    public AdvancedClass(String s, int i, AdvancedClass c) {
        this.s = s;
        this.i = i;
        this.c = c;
    }

    @JsonIgnore
    public String getS() {
        return s;
    }

    @JsonIgnore
    public void setS(String s) {
        this.s = s;
    }

    /*
     * Attenzione: perchè Jackson funzioni bene, è utile che i campi da
     * (de)serializzare, siano dotati tutti di corrispondenti getter e setter
     */
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public AdvancedClass getC() {
        return c;
    }

    public void setC(AdvancedClass c) {
        this.c = c;
    }

}
