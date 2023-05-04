package org.univaq.swa.examples.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Calendar;

/**
 *
 * @author SviluppoWebAvanzato
 */
public class AdvancedClass {

    /*
     * con l'annotazione @JsonIgnore (messa, per sicurezza,
     * sul campo e sui suoi getter/setter) diciamo a Jackson
     * di non considerare questo campo nella serializzazione e
     * nella deserializzazione
     */
    @JsonIgnore
    private String s;
    private Calendar t;
    private int i;

    private AdvancedClass c;

    /* 
     * Attenzione: per poter essere deserializzato l'oggetto
     * deve essere dotato di un construttore di default 
     * (senza parametri)
     */
    public AdvancedClass() {
        this.s = "";
        this.i = 0;
        this.c = null;
        this.t = Calendar.getInstance();
    }

    public AdvancedClass(String s, int i, AdvancedClass c) {
        this.s = s;
        this.i = i;
        this.c = c;
        this.t = Calendar.getInstance();
    }

    public AdvancedClass(String s, int i, AdvancedClass c, Calendar t) {
        this.s = s;
        this.i = i;
        this.c = c;
        this.t = t;
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

    public Calendar getT() {
        return t;
    }

    public void setT(Calendar t) {
        this.t = t;
    }

}
