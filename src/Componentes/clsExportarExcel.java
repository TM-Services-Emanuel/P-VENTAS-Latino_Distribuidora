/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componentes;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author AdminOmarGuevara
 */
public class clsExportarExcel {

    public void exportarExcel(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Hoja de cálculo", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                try (FileOutputStream archivo = new FileOutputStream(archivoXLS)) {
                    Sheet hoja = libro.createSheet("LISTADO");
                    hoja.setDisplayGridlines(true);
                    for (int f = 0; f < t.getRowCount(); f++) {
                        Row fila = hoja.createRow(f);
                        for (int c = 0; c < t.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (f == 0) {
                                celda.setCellValue(t.getColumnName(c));
                            }
                        }
                    }
                    int filaInicio = 1;
                    for (int f = 0; f < t.getRowCount(); f++) {
                        Row fila = hoja.createRow(filaInicio);
                        filaInicio++;
                        for (int c = 0; c < t.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (t.getValueAt(f, c) instanceof Double) {
                                celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                            } else if (t.getValueAt(f, c) instanceof Float) {
                                celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                            } else {
                                celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                            }
                        }
                    }
                    libro.write(archivo);
                }
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }

    public void exportarCSV(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV UTF-8 (delimitado por comas)", "csv");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".csv");
            try {
                File archivoCSV = new File(ruta);
                if (archivoCSV.exists()) {
                    archivoCSV.delete();
                }
                archivoCSV.createNewFile();
                Workbook libro = new HSSFWorkbook();
                try (FileOutputStream archivo = new FileOutputStream(archivoCSV)) {
                    Sheet hoja = libro.createSheet("VENTA");
                    hoja.setDisplayGridlines(true);
                    try {

                        FileWriter fw = new FileWriter(ruta);
                        BufferedWriter bw = new BufferedWriter(fw);
                        for (int i = 0; i < t.getRowCount(); i++) {

                            for (int j = 0; j < t.getColumnCount(); j++) {
                                //write
                                bw.write(t.getValueAt(i, j).toString() + ";");
                            }
                            bw.newLine();//record per line 
                        }
                        JOptionPane.showMessageDialog(null, "EL ARCHIVO FUE CREADO EXITOSAMENTE", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
                        bw.close();
                        fw.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
                    }
                    libro.write(archivo);
                }
                Desktop.getDesktop().open(archivoCSV);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }

}
