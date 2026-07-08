package com.ejemplo.util;

import javax.swing.table.DefaultTableModel;

public class TablaModelo extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int fila , int columna) {
        return false;
    }
}   
