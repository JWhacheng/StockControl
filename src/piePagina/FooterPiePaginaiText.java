/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piePagina;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author JosephAWB
 */
public class FooterPiePaginaiText extends PdfPageEventHelper
{ 
    Font ffont =  FontFactory.getFont(FontFactory.TIMES_ROMAN,8,BaseColor.BLACK);
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) 
    {
        PdfContentByte cb = writer.getDirectContent();
        Date fechaActual = new Date();
        
        SimpleDateFormat format1 = new SimpleDateFormat("dd'/'MM'/'yyyy");
        String fecha = format1.format(fechaActual); 
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm:ss a");
        String hora = format2.format(fechaActual);
        
        String time = fecha + " " + hora;
        
        Phrase footer1 = new Phrase(time, ffont);
        Phrase footer2 = new Phrase("Página.-" + document.getPageNumber(), ffont);
        
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer1, 40, 20, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer2, (document.right() - document.left()) / 2 + document.leftMargin(), 20, 0);
    }
    
}
