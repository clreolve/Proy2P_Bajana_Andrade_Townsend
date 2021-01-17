/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.net;

import java.io.IOException;

/**
 *
 * @author neoterux
 */
interface Downloadable{
    
    /**
     * Este método contendrá el proceso de descarga
     * 
     * @throws IOException si ocurre un error durante la descarga.
     */
    void onDownload() throws IOException;
    
    /**
     * Este método se ejecutrá cuando ocurra una IOException en el método de descarga.
     * 
     * @param ex IOException que ocurrió durante el método {@link Downloadable#onDownload()}
     */
    void onError(IOException ex);
    
    /**
     * Este método se ejecutará si el método {@link Downloadable#onDownload() } ha culminado con éxito. 
     */
    void onCompleted();
    
}
