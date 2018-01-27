/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.kardex;

import com.toedter.calendar.JDateChooser;

import beans.Existencia;
import beans.Producto;
import beans.Kardex;

import static gestordeinventarios.Principal.user;

import frame.kardex.UIKardexIns;

import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public class CKardexIns implements IKardexIns
{
    private UIKardexIns ventana;
    private String codigoProducto;
    private String codigoMarca;
    private String cantidad;
    
    public CKardexIns(String codigoProducto, String codigoMarca, String cantidad)
    {
        this.codigoProducto = codigoProducto;
        this.codigoMarca = codigoMarca;
        this.cantidad = cantidad;
        ventana = new UIKardexIns(this);
    }

    @Override
    public void cancelar() 
    {
       new CKardex();
       ventana.dispose();
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

    @Override
    public void cargar(JTextField txtKarCod, JTextField txtProCod, JTextField txtMarCod) 
    {
        txtKarCod.setText(Kardex.sgtCodigo());
        txtProCod.setText(codigoProducto);
        txtMarCod.setText(codigoMarca);
    }

    @Override
    public void aceptar(JTextField txtKarCod, JTextField txtProCod, JTextField txtMarCod, JDateChooser fecha, JComboBox cbxOpe, JTextField txtCan, JTextField txtPreUni, JTextField txtPreTot, JTextArea txtObs) 
    {
        Calendar c = fecha.getCalendar();
        try
        {          
            String cant = txtCan.getText();
            String canTotal = "0";
            if(cbxOpe.getSelectedIndex() == 0)
            {
                canTotal = String.valueOf(Integer.parseInt(cant) + Integer.parseInt(cantidad));
            }
            else
            {
                canTotal = Producto.validarCantidad(String.valueOf(Integer.parseInt(cantidad) - Integer.parseInt(cant)));
            }
            
            String preUni = txtPreUni.getText();
            String preTot = String.valueOf(Double.parseDouble(preUni)*Double.parseDouble(cant));
            
            String ope = "";
            if(cbxOpe.getSelectedIndex() == 0)
                ope = "1";
            else
                ope = "0";
            
            Kardex k = new Kardex(  txtKarCod.getText(),
                                    txtProCod.getText(),
                                    txtMarCod.getText(),
                                    user.getUsrDni(),
                                    String.valueOf(c.get(Calendar.YEAR)),
                                    String.valueOf(c.get(Calendar.MONTH) + 1),
                                    String.valueOf(c.get(Calendar.DATE)),                                  
                                    cant,
                                    preUni,
                                    preTot,
                                    ope,
                                    txtObs.getText()
                                );
            
            String err = k.insertar();
             if(err.equals(""))
            {
                Existencia p = Existencia.buscar(txtProCod.getText(), txtMarCod.getText());
                String err2 = p.modificar(canTotal);
                
                if(err2.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Se ha agregado el registro nuevo", "INSERCION", JOptionPane.INFORMATION_MESSAGE);
                    new CKardex();
                    ventana.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Cantidad o Precio Unitario inválido", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtCan.setText("");
            txtPreUni.setText("");
            txtPreTot.setText("");
        }
        catch(NullPointerException e)
        {
            JOptionPane.showMessageDialog(null, "     Fecha ingresada inválida", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
