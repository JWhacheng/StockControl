/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.kardex;

import com.toedter.calendar.JDateChooser;

import beans.Kardex;
import frame.kardex.UIKardexMod;
import static gestordeinventarios.Principal.user;
import beans.Existencia;
import beans.Producto;

import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public class CKardexMod implements IKardexMod
{
    private UIKardexMod ventana;
    private Kardex kd;
    private String cantidad;
    
    public CKardexMod(String codigo, String codigoProducto, String codigoMarca, String cantidad)
    {
        this.cantidad = cantidad;
        kd = Kardex.buscar(codigo, codigoProducto, codigoMarca);
        ventana = new UIKardexMod(this);
    }

    @Override
    public void cancelar() 
    {
       new CKardex();
       ventana.dispose();
    }

    @Override
    public void cargar(JTextField txtKarCod, JTextField txtProCod, JTextField txtMarCod, JDateChooser fecha, JComboBox cbxOpe, JTextField txtCan, JTextField txtPreUni, JTextField txtPreTot, JTextArea txtObs) 
    {
        txtKarCod.setText(kd.getKarCod());
        txtProCod.setText(kd.getProCod());
        txtMarCod.setText(kd.getMarCod());
        
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(kd.getKarAnio()), Integer.parseInt(kd.getKarMes()) - 1, Integer.parseInt(kd.getKarDia()));
        
        fecha.setCalendar(c);
        int ope = 1;
        if(kd.getKarOpe().equals("1"))
            ope = 0;
        cbxOpe.setSelectedIndex(ope);
        txtCan.setText(kd.getKarCan());
        txtPreUni.setText(kd.getKarValUni());
        txtPreTot.setText(kd.getKarValTot());
        
        txtObs.setText(kd.getKarObs());
    }

    @Override
    public void aceptar(JDateChooser fecha, JComboBox cbxOpe, JTextField txtCan, JTextField txtPreUni, JTextField txtPreTot, JTextArea txtObs) 
    {
        Calendar c = fecha.getCalendar();
        try
        {
            String auxCan = kd.getKarCan();
            String auxOpe = kd.getKarOpe();
            String cant = txtCan.getText();
            String canTotal = "0";
            
            if(cbxOpe.getSelectedIndex() == 0)
            {
                if(auxOpe.equals("1"))
                {
                    canTotal = String.valueOf(Integer.parseInt(cantidad) - Integer.parseInt(auxCan) + Integer.parseInt(cant));
                }
                else                
                    canTotal = String.valueOf(Integer.parseInt(cantidad) + Integer.parseInt(auxCan) + Integer.parseInt(cant));                    
            }
            else
            {
                if(auxOpe.equals("1"))
                    canTotal = Producto.validarCanMod(String.valueOf(Integer.parseInt(cantidad) - Integer.parseInt(auxCan) - Integer.parseInt(cant)), Existencia.getOperaciones(kd.getProCod(), kd.getMarCod()));
                else                
                    canTotal = Producto.validarCantidad(String.valueOf(Integer.parseInt(cantidad) + Integer.parseInt(auxCan) - Integer.parseInt(cant)));                  
            }
            
            String preUni = txtPreUni.getText();
            String preTot = String.valueOf(Double.parseDouble(preUni)*Double.parseDouble(cant));
            
            String ope = "";
            if(cbxOpe.getSelectedIndex() == 0)
                ope = "1";
            else
                ope = "0";
            
            kd.setKarAnio(String.valueOf(c.get(Calendar.YEAR)));
            kd.setKarMes(String.valueOf(c.get(Calendar.MONTH) + 1));
            kd.setKarDia(String.valueOf(c.get(Calendar.DATE)));
            kd.setUsrCod(user.getUsrDni());
            kd.setKarOpe(ope);
            kd.setKarCan(cant);
            kd.setKarValUni(preUni);
            kd.setKarValTot(preTot);
            kd.setKarObs(txtObs.getText());
            
            String err = kd.modificar();
            
            if(err.equals(""))
            {
                Existencia p = Existencia.buscar(kd.getProCod(), kd.getMarCod());
                String err2 = p.modificar(canTotal);
                
                if(err2.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Se ha modificado el registro", "MODIFICACIÓN", JOptionPane.INFORMATION_MESSAGE);
                    new CKardex();
                    ventana.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
                JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void calcular(JTextField txtCan, JTextField txtPreUni, JTextField txtPreTot) 
    {
       boolean canB    = !(txtCan.getText().length() == 0);
        boolean valUniB = !(txtPreUni.getText().length() == 0);
        
        int can  = 0;
        double vUni = 0;
        double vTot = 0;
        
        try
        {
            can = Integer.parseInt(txtCan.getText());
        }
        catch(NumberFormatException e)
        {
            canB = false;
        }
        
        try
        {
            vUni = Double.parseDouble(txtPreUni.getText());
        }
        catch(NumberFormatException e)
        {
            valUniB = false;
        }
        
        if(canB && valUniB)
        {
            vTot = can * vUni;
            txtPreTot.setText(String.valueOf(String.format("%.2f", vTot)));
        }
    }
}
