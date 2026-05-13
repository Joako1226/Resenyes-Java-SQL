/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author Rger Trulls
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Diccionari {

    private static List<String> diccionari = new ArrayList<>();
    private static final String RUTA_FITXER = "src/DADES/diccionari.txt"; 

    public static void carregarDiccionari() throws IOException {
        List<String> linies = Files.readAllLines(Paths.get(RUTA_FITXER));
        diccionari.clear();
        for (String s : linies) {
            if (!s.trim().isEmpty()) {
                diccionari.add(s.trim().toLowerCase());
            }
        }
        System.out.println("Diccionari carregat: " + diccionari.size() + " paraules.");
    }

    /**
     * Revisa si el comentari conté paraules prohibides.
     */
    public static boolean esInadequat(String comentari) throws IOException {
        if (comentari == null || comentari.isEmpty()) {
            return false;
        }

        if (diccionari.isEmpty()) {
            carregarDiccionari();
        }

        String textAAnalitzar = comentari.toLowerCase();

        for (String paraula : diccionari) {
            if (textAAnalitzar.contains(paraula)) {
                return true;
            }
        }
        return false;
    }
}
