# -_proyecto_excel_mysql_- :.

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/3c9a4278-88d7-4ea1-8bdf-75e432a23aa5" />      

# ProyectoExcelMySQL - Parte 1:
## Estructura del proyecto

```text
ProyectoExcelMySQL
│
├── pom.xml
├── script.sql
│
└── src
    └── main
        ├── java
        │
        └── com
            └── ejemplo
                │
                ├── Main.java
                │
                ├── gui
                │      └── VentanaPrincipal.java
                │
                ├── excel
                │      └── ExcelService.java
                │
                ├── database
                │      ├── Conexion.java
                │      └── SumaDAO.java
                │
                ├── model
                │      └── Resultado.java
                │
                └── util
                       └── TablaModelo.java
```

---

# pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.ejemplo</groupId>
    <artifactId>ProyectoExcelMySQL</artifactId>
    <version>1.0</version>

    <properties>

        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

    </properties>

    <dependencies>

        <!-- Apache POI -->

        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.4.1</version>
        </dependency>

        <!-- JDBC MySQL -->

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.3.0</version>
        </dependency>

    </dependencies>

</project>
```

---

# script.sql

```sql
CREATE DATABASE proyecto_excel;

USE proyecto_excel;

CREATE TABLE suma_filas(

    id INT AUTO_INCREMENT PRIMARY KEY,

    fila INT,

    suma DOUBLE

);
```

---

# Main.java

Ruta:

```
src/main/java/com/ejemplo/Main.java
```

```java
package com.ejemplo;

import com.ejemplo.gui.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            new VentanaPrincipal();

        });

    }

}
```

---

# ¿Qué hace esta primera parte?

* Crea la estructura base del proyecto.
* Configura Maven para Java 21.
* Agrega las dependencias de Apache POI y MySQL.
* Crea la base de datos `proyecto_excel`.
* Define la tabla `suma_filas`.
* Implementa el punto de entrada (`Main.java`), que inicia la interfaz gráfica.

---

# ProyectoExcelMySQL - Parte 2

En esta parte se desarrollan las siguientes clases:

* `Conexion.java`
* `Resultado.java`
* `SumaDAO.java`

---

# Conexion.java

Ruta:

```text
src/main/java/com/ejemplo/database/Conexion.java
```

```java
package com.ejemplo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/proyecto_excel";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    public static Connection obtenerConexion() {

        try {

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {

            System.out.println("Error de conexión");

            e.printStackTrace();

            return null;

        }

    }

}
```

---

# Resultado.java

Ruta

```text
src/main/java/com/ejemplo/model/Resultado.java
```

```java
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

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

}
```

---

# SumaDAO.java

Ruta

```text
src/main/java/com/ejemplo/database/SumaDAO.java
```

```java
package com.ejemplo.database;

import com.ejemplo.model.Resultado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SumaDAO {

    public void guardar(Resultado resultado) {

        String sql =
                "INSERT INTO suma_filas(fila,suma) VALUES(?,?)";

        try (

                Connection conexion = Conexion.obtenerConexion();

                PreparedStatement ps =
                        conexion.prepareStatement(sql)

        ) {

            ps.setInt(1, resultado.getFila());

            ps.setDouble(2, resultado.getSuma());

            ps.executeUpdate();

            System.out.println("Registro guardado.");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void listar() {

        String sql =
                "SELECT * FROM suma_filas";

        try (

                Connection conexion = Conexion.obtenerConexion();

                Statement st = conexion.createStatement();

                ResultSet rs = st.executeQuery(sql)

        ) {

            System.out.println();

            System.out.println("===== DATOS EN MYSQL =====");

            while (rs.next()) {

                System.out.println(

                        "ID: " + rs.getInt("id")
                                + "  Fila: " + rs.getInt("fila")
                                + "  Suma: " + rs.getDouble("suma")

                );

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
```

---

# Funcionamiento

Cuando el programa calcule la suma de una fila, se creará un objeto:

```java
Resultado resultado = new Resultado(1, 95.0);
```

Luego se guardará mediante:

```java
SumaDAO dao = new SumaDAO();

dao.guardar(resultado);
```

Finalmente podrá consultar todos los registros almacenados:

```java
dao.listar();
```

---

# Resultado esperado en MySQL

Tabla `suma_filas`

| id | fila | suma |
| -- | ---: | ---: |
| 1  |    1 |  100 |
| 2  |    2 |   85 |
| 3  |    3 |   74 |
| 4  |    4 |   93 |
| 5  |    5 |   66 |
| 6  |    6 |   58 |
| 7  |    7 |   79 |
| 8  |    8 |   81 |
| 9  |    9 |   72 |
| 10 |   10 |   90 |

---

Con esta segunda parte ya está implementada la capa de acceso a datos mediante JDBC, lista para ser utilizada desde la interfaz gráfica y el lector de Excel.

--- 

# ProyectoExcelMySQL - Parte 3

## ExcelService.java

Esta clase tiene como funciones:

* Leer un archivo **Excel (.xlsx)**.
* Obtener el contenido de las primeras **10 filas**.
* Calcular la suma de cada fila.
* Guardar cada suma en MySQL mediante `SumaDAO`.
* Devolver los datos para mostrarlos posteriormente en un `JTable`.

---

## Ubicación

```text
src/main/java/com/ejemplo/excel/ExcelService.java
```

---

## Código

```java
package com.ejemplo.excel;

import com.ejemplo.database.SumaDAO;
import com.ejemplo.model.Resultado;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

public class ExcelService {

    /**
     * Lee el archivo Excel y devuelve una lista con todas las filas.
     */
    public List<Object[]> leerExcel(File archivo) {

        List<Object[]> datos = new ArrayList<>();

        try {

            FileInputStream fis = new FileInputStream(archivo);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet hoja = workbook.getSheetAt(0);

            SumaDAO dao = new SumaDAO();

            int numeroFila = 1;

            for (Row fila : hoja) {

                if (numeroFila > 10)
                    break;

                List<Object> columnas = new ArrayList<>();

                double suma = 0;

                for (Cell celda : fila) {

                    switch (celda.getCellType()) {

                        case NUMERIC:

                            double valor = celda.getNumericCellValue();

                            columnas.add(valor);

                            suma += valor;

                            break;

                        case STRING:

                            columnas.add(celda.getStringCellValue());

                            break;

                        default:

                            columnas.add("");

                    }

                }

                columnas.add(suma);

                datos.add(columnas.toArray());

                Resultado resultado =
                        new Resultado(numeroFila, suma);

                dao.guardar(resultado);

                numeroFila++;

            }

            workbook.close();

            fis.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return datos;

    }

}
```

---

# ¿Cómo funciona?

Supongamos un archivo Excel:

|  A |  B |  C |  D |
| -: | -: | -: | -: |
| 10 | 20 | 30 | 40 |
|  5 |  8 |  6 |  1 |
| 15 | 12 | 18 | 10 |

La clase calcula:

| Fila | Suma |
| ---- | ---: |
| 1    |  100 |
| 2    |   20 |
| 3    |   55 |

Cada suma se guarda automáticamente en MySQL.

---

# Flujo interno

```text
Usuario

↓

Selecciona Excel

↓

ExcelService

↓

Lee Sheet 1

↓

Fila 1

↓

Calcula suma

↓

Guarda en MySQL

↓

Fila 2

↓

Calcula suma

↓

Guarda en MySQL

↓

...

↓

Fila 10

↓

Fin
```

---

# Valor devuelto

El método devuelve una lista de filas:

```java
List<Object[]> datos =
        excelService.leerExcel(archivo);
```

Cada elemento representa una fila del Excel.

Ejemplo:

```text
Fila 1

10

20

30

40

100
```

El último valor corresponde a la **suma de la fila**, que podrá mostrarse como una columna adicional en la interfaz gráfica.

---

# Resultado esperado en consola

```text
Registro guardado.

Registro guardado.

Registro guardado.

...

Registro guardado.
```

Al finalizar, la tabla `suma_filas` contendrá las sumas de las primeras 10 filas del archivo Excel.

---

# ProyectoExcelMySQL - Parte 4

# TablaModelo.java

Esta clase crea el modelo que utilizará el JTable para mostrar la información leída del archivo Excel.

---

## Ubicación

```text
src/main/java/com/ejemplo/util/TablaModelo.java
```

---

## Código completo

```java
package com.ejemplo.util;

import javax.swing.table.DefaultTableModel;

public class TablaModelo extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int fila, int columna) {

        return false;

    }

}
```

---

# ¿Por qué crear esta clase?

Aunque podríamos utilizar directamente un `DefaultTableModel`, crear una clase propia permite:

* Mantener el código organizado.
* Evitar que el usuario edite las celdas.
* Facilitar futuras mejoras sin modificar la interfaz gráfica.

---

# Funcionamiento

Cuando `ExcelService` termine de leer el archivo Excel, devolverá una lista de filas.

Por ejemplo:

```text
10   20   30   40   100
5     8    6    1    20
7     9   12   15    43
```

La última columna corresponde a la **suma** calculada de cada fila.

---

# Crear el modelo

```java
TablaModelo modelo = new TablaModelo();
```

---

# Agregar columnas

```java
modelo.addColumn("Columna 1");
modelo.addColumn("Columna 2");
modelo.addColumn("Columna 3");
modelo.addColumn("Columna 4");
modelo.addColumn("Suma");
```

---

# Agregar filas

```java
modelo.addRow(datos);
```

donde `datos` es un arreglo de objetos:

```java
Object[] datos = {

    10,

    20,

    30,

    40,

    100

};
```

---

# Asignar el modelo al JTable

```java
tabla.setModel(modelo);
```

---

# Resultado esperado

```
+----------------------------------------------+

Columna1  Columna2  Columna3  Columna4   Suma

-----------------------------------------------

10        20        30        40         100

5         8         6         1          20

7         9         12        15         43

-----------------------------------------------
```

---

# Ventajas

* Muy fácil de utilizar.
* Compatible con cualquier JTable.
* No requiere código adicional.
* Las celdas quedan protegidas contra edición.
* Ideal para proyectos educativos y ejemplos básicos.

---

# Proyecto completado hasta el momento

```
✔ Main.java

✔ Conexion.java

✔ Resultado.java

✔ SumaDAO.java

✔ ExcelService.java

✔ TablaModelo.java
```

En este punto ya están listas la conexión a MySQL, la lectura del archivo Excel, el cálculo de las sumas y el modelo para mostrar la información en la tabla.

---

# ProyectoExcelMySQL - Parte 5.1

# VentanaPrincipal.java

Ubicación:

```text
src/main/java/com/ejemplo/gui/VentanaPrincipal.java
```

## Código

```java
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
```

---

# ¿Qué hace esta parte?

Se crea la ventana principal de la aplicación con los siguientes componentes:

* Un botón **Abrir Excel**.
* Un `JTable` para mostrar el contenido del archivo.
* Un `JTextArea` para mostrar las sumas calculadas.
* Un diseño `BorderLayout` para organizar los elementos.

---

# Aspecto aproximado de la interfaz

```text
+-----------------------------------------------------------+

              PROYECTO EXCEL MYSQL

-------------------------------------------------------------

                [ Abrir Excel ]

-------------------------------------------------------------

+---------------------------------------------------------+

|                                                     |

|                 JTable                               |

|                                                     |

|                                                     |

|                                                     |

+---------------------------------------------------------+

-------------------------------------------------------------

Resultados

Fila 1 =

Fila 2 =

Fila 3 =

...

-------------------------------------------------------------
```

---

# Estado del proyecto

Hasta este punto ya están implementadas las siguientes clases:

* Main.java
* Conexion.java
* Resultado.java
* SumaDAO.java
* ExcelService.java
* TablaModelo.java
* VentanaPrincipal.java (estructura e interfaz)

En la siguiente parte se implementará el método `abrirArchivo()`, que permitirá seleccionar un archivo `.xlsx`, leerlo con `ExcelService` y mostrar su contenido en el `JTable`.

---

# ProyectoExcelMySQL - Parte 5.2

## Implementar el método `abrirArchivo()`

Reemplaza el método vacío por el siguiente código:

```java
private void abrirArchivo() {

    JFileChooser selector = new JFileChooser();

    int opcion = selector.showOpenDialog(this);

    if (opcion == JFileChooser.APPROVE_OPTION) {

        File archivo = selector.getSelectedFile();

        cargarExcel(archivo);

    }

}
```

---

## Agregar los imports

Al inicio de `VentanaPrincipal.java`, agrega:

```java
import java.io.File;
import java.util.List;
```

---

## Agregar el método `cargarExcel(File archivo)`

Este método puede colocarse debajo de `abrirArchivo()`.

```java
private void cargarExcel(File archivo) {

    modelo.setRowCount(0);

    modelo.setColumnCount(0);

    modelo.addColumn("Columna 1");
    modelo.addColumn("Columna 2");
    modelo.addColumn("Columna 3");
    modelo.addColumn("Columna 4");
    modelo.addColumn("Suma");

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
            "Archivo procesado correctamente."
    );

}
```

---

# ¿Qué realiza este código?

Cuando el usuario hace clic en **Abrir Excel**:

1. Se abre un `JFileChooser`.
2. El usuario selecciona un archivo `.xlsx`.
3. `ExcelService` lee las primeras 10 filas.
4. Calcula la suma de cada fila.
5. Guarda automáticamente cada suma en MySQL.
6. Devuelve los datos.
7. Los datos se muestran en el `JTable`.
8. Las sumas aparecen en el área de resultados.

---

# Resultado esperado

Si el archivo contiene:

|  A |  B |  C |  D |
| -: | -: | -: | -: |
| 10 | 20 | 30 | 40 |
|  5 |  8 |  6 |  1 |
|  9 |  9 |  9 |  9 |

El `JTable` mostrará:

```text
+-------------------------------------------+

Columna1  Columna2  Columna3  Columna4  Suma

---------------------------------------------

10        20        30        40        100

5         8         6         1         20

9         9         9         9         36

+-------------------------------------------+
```

Y el área de resultados mostrará:

```text
Fila 1 = 100.0

Fila 2 = 20.0

Fila 3 = 36.0
```

---

# Flujo de ejecución

```text
Usuario

↓

Abrir Excel

↓

JFileChooser

↓

ExcelService

↓

Leer archivo .xlsx

↓

Calcular suma

↓

Guardar en MySQL

↓

Devolver datos

↓

Actualizar JTable

↓

Mostrar sumas
```

---

# ProyectoExcelMySQL - Parte 5.3

## Paso 1. Modificar `SumaDAO.java`

Agrega el siguiente método:

```java
public List<Resultado> obtenerTodos() {

    List<Resultado> lista = new ArrayList<>();

    String sql = "SELECT fila, suma FROM suma_filas ORDER BY fila";

    try (
        Connection conexion = Conexion.obtenerConexion();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(sql)
    ) {

        while (rs.next()) {

            Resultado resultado = new Resultado();

            resultado.setFila(rs.getInt("fila"));
            resultado.setSuma(rs.getDouble("suma"));

            lista.add(resultado);

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

    return lista;

}
```

No olvides agregar estos imports:

```java
import java.util.ArrayList;
import java.util.List;
```

---

## Paso 2. Agregar componentes en `VentanaPrincipal.java`

Importa:

```java
import com.ejemplo.database.SumaDAO;
import com.ejemplo.model.Resultado;
import java.util.List;
```

Declara los atributos:

```java
private JButton btnConsultar;

private SumaDAO sumaDAO;
```

En el constructor:

```java
excelService = new ExcelService();

sumaDAO = new SumaDAO();
```

---

## Paso 3. Crear el botón

Después del botón **Abrir Excel**:

```java
btnConsultar = new JButton("Consultar MySQL");

panelSuperior.add(btnConsultar);
```

---

## Paso 4. Registrar el evento

Dentro de `registrarEventos()`:

```java
btnConsultar.addActionListener(e -> consultarMySQL());
```

---

## Paso 5. Crear el método

```java
private void consultarMySQL() {

    List<Resultado> resultados = sumaDAO.obtenerTodos();

    txtResultados.setText("");

    txtResultados.append("===== DATOS GUARDADOS =====\n\n");

    for (Resultado r : resultados) {

        txtResultados.append(

                "Fila "
                + r.getFila()
                + " = "
                + r.getSuma()
                + "\n"

        );

    }

}
```

---

# Resultado final de la interfaz

```text
+--------------------------------------------------------------------+

                    PROYECTO EXCEL MYSQL

---------------------------------------------------------------------

[ Abrir Excel ]     [ Consultar MySQL ]

---------------------------------------------------------------------

+---------------------------------------------------------------+

|                     JTable                                   |

|                                                             |

|                                                             |

+---------------------------------------------------------------+

Fila 1 = 100

Fila 2 = 20

Fila 3 = 36

...

---------------------------------------------------------------------
```

---

# Flujo completo del programa

```text
Inicio

↓

VentanaPrincipal

↓

Abrir Excel

↓

Seleccionar archivo

↓

ExcelService

↓

Leer primeras 10 filas

↓

Calcular suma de cada fila

↓

Guardar automáticamente en MySQL

↓

Mostrar datos en JTable

↓

Mostrar sumas

↓

Consultar MySQL

↓

Mostrar registros almacenados
```

---

# Proyecto terminado

La aplicación ya permite:

* Leer un archivo Excel (`.xlsx`).
* Mostrar su contenido en un `JTable`.
* Calcular la suma de las primeras 10 filas.
* Guardar automáticamente las sumas en MySQL mediante JDBC.
* Consultar los registros almacenados.
* Mostrar los resultados en una interfaz gráfica desarrollada con Swing.

La estructura final del proyecto es:

```text
ProyectoExcelMySQL
│
├── pom.xml
├── script.sql
│
└── src
    └── main
        └── java
            └── com
                └── ejemplo
                    ├── Main.java
                    ├── gui
                    │     └── VentanaPrincipal.java
                    ├── excel
                    │     └── ExcelService.java
                    ├── database
                    │     ├── Conexion.java
                    │     └── SumaDAO.java
                    ├── model
                    │     └── Resultado.java
                    └── util
                          └── TablaModelo.java
```

:. . / .
