/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.net;

import java.io.IOException;

/**
 * <h1>DownloadTask</h1>
 * <p>
 * clase que permite manejar los procesos de descarga, puede ser utilizada para
 * manejar la descarga en el fondo.</p>
 *
 * @author neoterux
 */
public abstract class DownloadTask implements Runnable, Downloadable {
    

    @Override
    public void run() {
//        try {
//            onDownload();
//            onCompleted();
//        } catch (IOException ex) {
//            onError(ex);
//        }
    }
    
}
