package com.ejemplo;

import com.ejemplo.gui.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            new VentanaPrincipal();
        });
    }
}