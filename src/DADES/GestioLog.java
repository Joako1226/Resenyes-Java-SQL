/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DADES;

import CONTROLLER.Main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Rger Trulls
 */
public class GestioLog {
    public static void CrearLog() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String diaactual = LocalDate.now().format(format);
        String directori = "src/LOG/";
        String rutaCompleta = directori + diaactual + ".log";
        Main.rutaFitxerLog = rutaCompleta;
        Crear(rutaCompleta);
    }

    public static void Crear(String ruta) {
        try {
            File obj = new File(ruta);
            if (obj.createNewFile()) {
                System.out.println("Fitxer creat: " + obj.getName());
            }
        } catch (IOException e) {
            System.out.println("Error de creacio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void Escriure(String ruta, String s) {
        try (FileWriter wr = new FileWriter(ruta, true)) {
            wr.write(s + "\n");
        } catch (IOException e) {
            System.out.println("Error escrivint fitxer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void EscriureLog(String s) {
        Escriure(Main.rutaFitxerLog, s);
    }


}
