package org.univaq.swa.examples.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author SviluppoWebAvanzato
 */
public class SimpleClass {

    /*
     * con l'annotazione @JsonIgnore (messa, per sicurezza,
     * sul campo e sui suoi getter/setter) diciamo a Jackson
     * di non considerare questo campo nella serializzazione e
     * nella deserializzazione
     */
    @JsonIgnore
    private String s;
    private int i;
    private SimpleClass c;

    /* 
     * Attenzione: per poter essere deserializzato l'oggetto
     * deve essere dotato di un construttore di default 
     * (senza parametri)
     */
    public SimpleClass() {
        s = "";
        i = 0;
        c = null;
    }

    public SimpleClass(String s, int i, SimpleClass c) {
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

    public SimpleClass getC() {
        return c;
    }

    public void setC(SimpleClass c) {
        this.c = c;
    }

}
