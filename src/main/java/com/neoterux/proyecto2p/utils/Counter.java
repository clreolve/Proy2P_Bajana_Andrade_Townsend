/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.utils;

/**
 *
 * @author neoterux
 */
public class Counter {
    
    private int initValue;
    private int step;
    
    
    public Counter(int initialValue, int step){
        this.initValue = initialValue;
        this.step = step;
    }
    
    public Counter(int initialValue){
        this(initialValue, 1);
    }
    
    
    
    public int getCurrentValue() { return this.initValue; }
    
    public void step(){
        this.initValue += this.step;
    }
    
    
    @Override
    public String toString() {
        return this.initValue + "";
    }
}
