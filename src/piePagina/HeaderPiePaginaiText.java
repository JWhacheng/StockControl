/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piePagina;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 *
 * @author JosephAWB
 */
public class HeaderPiePaginaiText extends PdfPageEventHelper
{
    private Image imagen;
  
    public HeaderPiePaginaiText()
    {
        try
        {
            URL url = HeaderPiePaginaiText.class.getResource("/recursos/membrete.png");
            imagen = Image.getInstance(url);
            imagen.scaleAbsolute(150, 43);
        } catch (BadElementException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al leer la imagen");
        }
        
    }
    @Override
    public void onStartPage(PdfWriter writer, Document document) 
    {
        try
        {
            document.add(imagen);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
}
