/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

/**
 *
 * @author Joaquin
 */
public class Style {
    public static void temaClar(JFrame frame){
        try{
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
             SwingUtilities.updateComponentTreeUI(frame);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void temaFosc(JFrame frame){
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());
             SwingUtilities.updateComponentTreeUI(frame);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
