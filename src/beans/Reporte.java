/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import piePagina.FooterPiePaginaiText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import piePagina.HeaderPiePaginaiText;


/**
 *
 * @author JosephAWB
 */
public class Reporte 
{
    
    public static String generarReporte1(Producto myProducto, ArrayList<ArrayList<String>> resultados, JTextField cantidadTotal)
    {        
        String path = null;
        
        JFileChooser save = new JFileChooser()
        {
            @Override
            public void approveSelection(){
                File f = getSelectedFile();
                String path = "" + f.getAbsoluteFile() + "";
                if(!path.substring(path.length() - 3, path.length()).equals("pdf"))
                    path = path +".pdf";
                
                if (new File(path).exists()) {
                    int result = JOptionPane.showConfirmDialog(this, "El archivo ya existe. ¿Deseas reemplazarlo?", "Confirmar Guardar Cómo", JOptionPane.YES_NO_OPTION);

                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            JOptionPane.showMessageDialog(this, "El archivo se ha guardado con éxito");
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                JOptionPane.showMessageDialog(this, "El archivo se ha guardado con éxito");
                super.approveSelection();
            }
        };
        
        save.setDialogTitle("Guardar Reporte PDF");
        FileFilter filter = new FileNameExtensionFilter("Archivo PDF","pdf");
        save.setAcceptAllFileFilterUsed(false);
        save.setFileFilter(filter);
        
        int option = save.showSaveDialog(null);

        if(option == JFileChooser.APPROVE_OPTION)
        {
            try
            {                
                ArrayList <String> detPro = new ArrayList <>(); //detalle del producto--> descripcion

                detPro.add(myProducto.getProCod());
                detPro.add(myProducto.getProDes());
                detPro.add(cantidadTotal.getText());

                Date fechaActual = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("dd'/'MM'/'yyyy");
                String fecha = format1.format(fechaActual);                
                SimpleDateFormat format2 = new SimpleDateFormat("H:mm");
                String hora = format2.format(fechaActual);

                /*-------------------------------------------------------------------------------------------------------------------------*/

                Document doc = new Document();

                path = "" + save.getSelectedFile().getAbsoluteFile() + "";
                if(!path.substring(path.length() - 3, path.length()).equals("pdf"))
                    path = path +".pdf";

                PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path));
                FooterPiePaginaiText eventPie = new FooterPiePaginaiText();
                HeaderPiePaginaiText eventMem = new HeaderPiePaginaiText();

                writer.setPageEvent(eventMem);
                writer.setPageEvent(eventPie);

                doc.open();

                //PRIMERA LINEA
                Paragraph prgReporte;

                prgReporte = new Paragraph("REPORTE DE PRODUCTO", FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_CENTER);

                doc.add(prgReporte);

                //CREACION DE TABLA Y CELDAS
                PdfPTable tbaReporte;
                PdfPCell cellReporte;

                // FECHA
                prgReporte = new Paragraph("Fecha: " + fecha, FontFactory.getFont(FontFactory.TIMES_ROMAN,10,BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_RIGHT);
                doc.add(prgReporte);
                //HORA 
                prgReporte = new Paragraph("Hora: " + hora,FontFactory.getFont(FontFactory.TIMES_ROMAN,10,BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_RIGHT);
                doc.add(prgReporte);

                // DATOS DEL PRODUCTO
                prgReporte = new Paragraph("DATOS DEL PRODUCTO",FontFactory.getFont(FontFactory.TIMES_ROMAN,13,java.awt.Font.BOLD,BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_LEFT);
                doc.add(prgReporte);

                // INFO DEL PRODUCTO
                prgReporte = new Paragraph("\n    Código:             " + detPro.get(0), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK));
                doc.add(prgReporte);
                prgReporte = new Paragraph("    Descripción:     " + detPro.get(1).toUpperCase(), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK));
                doc.add(prgReporte);
                prgReporte = new Paragraph("    Cantidad total:  " + detPro.get(2), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK));
                doc.add(prgReporte);

                prgReporte = new Paragraph("\nDETALLE POR MARCA\n\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,13,java.awt.Font.BOLD,BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_LEFT);
                doc.add(prgReporte);

                 ///detalle de las marcas

                ArrayList<String> cabMar =new ArrayList<>();
                cabMar.add("Código");
                cabMar.add("Nombre");
                cabMar.add("Cantidad");


                tbaReporte = new PdfPTable(3);

                // agregamos a la tabla
                for(int i = 0;i < cabMar.size();i++)
                {
                    cellReporte = new PdfPCell(new Paragraph(cabMar.get(i), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,java.awt.Font.BOLD,BaseColor.BLACK)));
                    cellReporte.setFixedHeight(30);
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBackgroundColor(BaseColor.WHITE);
                    cellReporte.setBorderColorTop(BaseColor.BLACK);
                    cellReporte.setBorderColorBottom(BaseColor.BLACK);
                    cellReporte.setBorderWidthTop(1);
                    cellReporte.setBorderWidthBottom(1);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);
                }
                doc.add(tbaReporte);

                prgReporte = new Paragraph("\n", FontFactory.getFont(FontFactory.TIMES_ROMAN,5));
                doc.add(prgReporte);

                tbaReporte = new PdfPTable(3);
                // agregamos las marcas
                for(int i = 0;i < resultados.size();i++)
                {
                    cellReporte = new PdfPCell(new Paragraph(resultados.get(i).get(0), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);

                    cellReporte = new PdfPCell(new Paragraph(resultados.get(i).get(1), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);

                    cellReporte = new PdfPCell(new Paragraph(resultados.get(i).get(2), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);
                }
                doc.add(tbaReporte);
                doc.close();
                
            }catch (DocumentException | IOException ex) {
                
            }
        }
        return path;
    }
    
     public static String generarReporte2(Producto myProducto, String anio, ArrayList<ArrayList<String>> detallesPorMarca, ArrayList<ArrayList<String>> resultados, int tipoOpe)
    {
        String path = null;
        JFileChooser save = new JFileChooser()
        {
            @Override
            public void approveSelection(){
                File f = getSelectedFile();
                String path = "" + f.getAbsoluteFile() + "";
                if(!path.substring(path.length() - 3, path.length()).equals("pdf"))
                    path = path +".pdf";
                
                if (new File(path).exists()) {
                    int result = JOptionPane.showConfirmDialog(this, "El archivo ya existe. ¿Deseas reemplazarlo?", "Confirmar Guardar Cómo", JOptionPane.YES_NO_OPTION);

                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            JOptionPane.showMessageDialog(this, "El archivo se ha guardado con éxito");
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                super.approveSelection();
                JOptionPane.showMessageDialog(this, "El archivo se ha guardado con éxito");
            }
        };

        save.setDialogTitle("Guardar Reporte PDF");
        FileFilter filter = new FileNameExtensionFilter("Archivo PDF","pdf");
        save.setAcceptAllFileFilterUsed(false);
        save.setFileFilter(filter);
        
        int option = save.showSaveDialog(null);

        if(option == JFileChooser.APPROVE_OPTION)
        {
            try
            {                
                ArrayList <String> detPro = new ArrayList <>(); //detalle del producto--> descripcion

                detPro.add(myProducto.getProCod());
                detPro.add(myProducto.getProDes());

                Date fechaActual = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("dd'/'MM'/'yyyy");
                String fecha = format1.format(fechaActual);                
                SimpleDateFormat format2 = new SimpleDateFormat("H:mm");
                String hora = format2.format(fechaActual);

                /*-------------------------------------------------------------------------------------------------------------------------*/

                Document doc = new Document();

                path = "" + save.getSelectedFile().getAbsoluteFile() + "";
                if(!path.substring(path.length() - 3, path.length()).equals("pdf"))
                    path = path +".pdf";

                PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path));
                FooterPiePaginaiText eventPie = new FooterPiePaginaiText();
                HeaderPiePaginaiText eventMem = new HeaderPiePaginaiText();

                writer.setPageEvent(eventMem);
                writer.setPageEvent(eventPie);

                doc.open();

                //PRIMERA LINEA
                Paragraph prgReporte;
                String titulo = "";
                if(tipoOpe == 1)
                    titulo = "REPORTE DE ENTRADAS DEL " + anio;
                else
                    titulo = "REPORTE DE SALIDAS DEL " + anio;
                
                prgReporte = new Paragraph(titulo, FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_CENTER);

                doc.add(prgReporte);
                
                 //CREACION DE TABLA Y CELDAS
                PdfPTable tbaReporte;
                PdfPCell cellReporte;

                // FECHA
                prgReporte = new Paragraph("Fecha: " + fecha, FontFactory.getFont(FontFactory.TIMES_ROMAN,10,BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_RIGHT);
                doc.add(prgReporte);
                //HORA 
                prgReporte = new Paragraph("Hora: " + hora,FontFactory.getFont(FontFactory.TIMES_ROMAN,10,BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_RIGHT);
                doc.add(prgReporte);

                // DATOS DEL PRODUCTO
                prgReporte = new Paragraph("DATOS DEL PRODUCTO",FontFactory.getFont(FontFactory.TIMES_ROMAN,13,java.awt.Font.BOLD,BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_LEFT);
                doc.add(prgReporte);

                // INFO DEL PRODUCTO
                prgReporte = new Paragraph("\n    Código:             " + detPro.get(0), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK));
                doc.add(prgReporte);
                prgReporte = new Paragraph("    Descripción:     " + detPro.get(1).toUpperCase(), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK));
                doc.add(prgReporte);
                
                // RESUMEN DE ENTRADAS POR MARCA
                prgReporte = new Paragraph("\nRESUMEN POR MARCA\n\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,13,java.awt.Font.BOLD,BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_LEFT);
                doc.add(prgReporte);
                
                ArrayList<String> cabMar =new ArrayList<>();
                cabMar.add("Código");
                cabMar.add("Nombre");
                cabMar.add("Cantidad");


                tbaReporte = new PdfPTable(3);

                // agregamos a la tabla
                for(int i = 0; i < cabMar.size(); i++)
                {
                    cellReporte = new PdfPCell(new Paragraph(cabMar.get(i), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,java.awt.Font.BOLD,BaseColor.BLACK)));
                    cellReporte.setFixedHeight(30);
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBackgroundColor(BaseColor.WHITE);
                    cellReporte.setBorderColorTop(BaseColor.BLACK);
                    cellReporte.setBorderColorBottom(BaseColor.BLACK);
                    cellReporte.setBorderWidthTop(1);
                    cellReporte.setBorderWidthBottom(1);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);
                }
                doc.add(tbaReporte);
                
                prgReporte = new Paragraph("\n", FontFactory.getFont(FontFactory.TIMES_ROMAN,5));
                doc.add(prgReporte);

                tbaReporte = new PdfPTable(3);
                for(int i = 0; i < detallesPorMarca.size(); i++)
                {
                    cellReporte = new PdfPCell(new Paragraph(detallesPorMarca.get(i).get(0), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);

                    cellReporte = new PdfPCell(new Paragraph(detallesPorMarca.get(i).get(1), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);

                    cellReporte = new PdfPCell(new Paragraph(detallesPorMarca.get(i).get(2), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);
                }
                doc.add(tbaReporte);
                
                // RESUMEN DE ENTRADAS/SALIDAS POR MARCA
                prgReporte = new Paragraph("\nDETALLE POR FECHA Y MARCA\n\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,13,java.awt.Font.BOLD,BaseColor.BLACK));
                prgReporte.setAlignment(Element.ALIGN_LEFT);
                doc.add(prgReporte);
                
                ArrayList<String> tblCab = new ArrayList<>();
                tblCab.add("Fecha");
                tblCab.add("Código");
                tblCab.add("Nombre");
                tblCab.add("Cantidad");
                
                tbaReporte = new PdfPTable(4);
                
                //agreamos a la tabla
                for(int i = 0; i < tblCab.size(); i++)
                {
                    cellReporte = new PdfPCell(new Paragraph(tblCab.get(i), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,java.awt.Font.BOLD,BaseColor.BLACK)));
                    cellReporte.setFixedHeight(30);
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBackgroundColor(BaseColor.WHITE);
                    cellReporte.setBorderColorTop(BaseColor.BLACK);
                    cellReporte.setBorderColorBottom(BaseColor.BLACK);
                    cellReporte.setBorderWidthTop(1);
                    cellReporte.setBorderWidthBottom(1);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);
                }
                doc.add(tbaReporte);
                
                prgReporte = new Paragraph("\n", FontFactory.getFont(FontFactory.TIMES_ROMAN,5));
                doc.add(prgReporte);
                
                tbaReporte = new PdfPTable(4);
                for(int i = 0; i < resultados.size(); i++)
                {
                    cellReporte = new PdfPCell(new Paragraph(resultados.get(i).get(0), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);

                    cellReporte = new PdfPCell(new Paragraph(resultados.get(i).get(1), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);

                    cellReporte = new PdfPCell(new Paragraph(resultados.get(i).get(2), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);
                    
                    cellReporte = new PdfPCell(new Paragraph(resultados.get(i).get(3), FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK)));
                    cellReporte.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellReporte.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellReporte.setBorderColor(BaseColor.WHITE);
                    tbaReporte.addCell(cellReporte);
                }
                doc.add(tbaReporte);
                doc.close();
                
            }catch(DocumentException | IOException ex){
            }
        }
        return path;
    }
     
}
