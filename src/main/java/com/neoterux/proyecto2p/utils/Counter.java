/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.utils;

/**
 * <h1>Counter</h1>
 * <p>Esta clase representa un contador, que se puede utilizar dentro de lambdas</p>
 *
 * @author neoterux
 */
public class Counter {

    /**
     * Valor inicial del contador.
     */
    private final int INITIAL_VALUE;
    /**
     * Valor actual del contador.
     */
    private int value;
    /**
     * Escalar que se suma/resta al valor inicial del contador.
     */
    private final int step;

    /**
     * Crea un nuevo contador con su valor inicial y un step personalizado.
     *
     * @param initialValue valor inicial del contador
     * @param step step personalizado.
     */
    public Counter(int initialValue, int step){
        this.INITIAL_VALUE = initialValue;
        this.value = initialValue;
        this.step = step;
    }

    /**
     * Crea un nuevo contado con valor inical, step por defecto = 1.
     *
     * @param initialValue valor inicial del contador.
     */
    public Counter(int initialValue){
        this(initialValue, 1);
    }


    /**
     * Obtiene el valor actual del contador.
     *
     * @return el valor actual.
     */
    public int getCurrentValue() { return this.value; }

    /**
     * Realiza la suma/resta del {@link #step} que se ingresó inicialmente.
     */
    public void step(){
        this.value += this.step;
    }

    /**
     * Hace que el valor actual del contador, se resetee a su valor incial.
     */
    public void reset() { this.value = this.INITIAL_VALUE; }

    /**
     * Muestra el valor actual del contador.
     * @return el valor actual en String.
     */
    @Override
    public String toString() {
        return this.value + "";
    }
}
