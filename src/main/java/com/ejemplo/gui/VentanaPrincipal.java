package com.ejemplo.gui;

import com.ejemplo.excel.ExcelService;
import com.ejemplo.util.TablaModelo;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    // Componentes

    private JTable tabla;

    private TablaModelo modelo;

    private JButton btnAbrir;

    private JTextArea txtResultados;

    private JScrollPane scrollTabla;

    private JScrollPane scrollTexto;

    private ExcelService excelService;

    public VentanaPrincipal() {

        excelService = new ExcelService();

        inicializarComponentes();

    }

    private void inicializarComponentes() {

        setTitle("Proyecto Excel MySQL");

        setSize(900,600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        //------------------------------------

        btnAbrir = new JButton("Abrir Excel");

        JPanel panelSuperior = new JPanel();

        panelSuperior.add(btnAbrir);

        add(panelSuperior, BorderLayout.NORTH);

        //------------------------------------

        modelo = new TablaModelo();

        tabla = new JTable(modelo);

        scrollTabla = new JScrollPane(tabla);

        add(scrollTabla, BorderLayout.CENTER);

        //------------------------------------

        txtResultados = new JTextArea();

        txtResultados.setEditable(false);

        scrollTexto = new JScrollPane(txtResultados);

        scrollTexto.setPreferredSize(new Dimension(900,150));

        add(scrollTexto, BorderLayout.SOUTH);

        //------------------------------------

        registrarEventos();

        //------------------------------------

        setVisible(true);

    }

    private void registrarEventos() {

        btnAbrir.addActionListener(e -> abrirArchivo());

    }

    private void abrirArchivo() {

        // Se implementará en la Parte 5.2

        JFileChooser selector = new JFileChooser();
        int opcion = selector.showOpenDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();

            cargarExcel(archivo);
        }   
    }

    private void cargarExcel(File archivo) {

        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        modelo.addColumn("Columna 1");
        modelo.addColumn("Columna 2");
        modelo.addColumn("Columna 3");
        modelo.addColumn("Columna 4");
        modelo.addColumn("Suma"); // columna para la suma .

        List<Object[]> datos = excelService.leerExcel(archivo);
        txtResultados.setText("");

        int fila = 1;

        for (Object[] registro : datos) {
            modelo.addRow(registro);
            double suma = (Double) registro[registro.length - 1];

            txtResultados.append(
                    "Fila " + fila +
                            " = " + suma +
                            System.lineSeparator()
            );

            fila++;
        }

        JOptionPane.showMessageDialog(
                this,
                "Archivo procesado correctamente! "
        );
    }
}