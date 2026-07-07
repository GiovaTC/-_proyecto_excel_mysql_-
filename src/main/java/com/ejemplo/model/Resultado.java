package com.ejemplo.model;

public class Resultado {

    private int fila;
    private double suma;

    public Resultado() {

    }

    public Resultado(int fila, double suma) {

        this.fila = fila;
        this.suma = suma;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }
}
