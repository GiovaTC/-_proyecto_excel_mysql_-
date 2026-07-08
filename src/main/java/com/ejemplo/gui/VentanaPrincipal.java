package com.ejemplo.gui;

import com.ejemplo.excel.ExcelService;
import com.ejemplo.util.TablaModelo;

import javax.swing.*;
import java.awt.*;  

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

    }
}