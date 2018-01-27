/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.consulta;


import beans.Consulta;
import beans.Producto;
import beans.Reporte;
import controlador.helper.CProductoHelp;
import controlador.principal.CMenu;
import frame.consulta.UISalida;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JosephAWB
 */
public class CSalida implements ISalida
{
    
    private ArrayList<ArrayList<String>> resultado;
    protected UISalida ventana;
    private boolean consultaRealizada;
    private ArrayList<ArrayList<String>> detalles;
    
    private ArrayList<String> anios;
    private int iAnio;

    public CSalida()
    {
        consultaRealizada = false;
        resultado = new ArrayList<ArrayList<String>>();
        detalles = new ArrayList<ArrayList<String>>();
        
        iAnio = -1;
        anios = null;
        
        ventana = new UISalida(this);
    }
    
    @Override
    public void verProducto(JTextField txtProCod, JTextField txtProDes, JComboBox cbxAnio)
    {
        new CProductoHelp(ventana, txtProCod, txtProDes);
        anios = Consulta.getAnioOp(txtProCod.getText(), "0");
        cbxAnio.removeAllItems();
        iAnio = -1;
        for(int i = 0; i < anios.size(); i++)
        {
            cbxAnio.insertItemAt(anios.get(i), i);
        }
    }

    @Override
    public void menu() 
    {
        new CMenu();
        ventana.dispose();
    }

    @Override
    public void consultar(JTable tblConsultas, JTextField txtProCod) 
    {
       if(txtProCod.getText().length() != 0)
        {
            boolean a = anios != null;
            boolean iA = iAnio != -1;
            
            if(a && iA)
            {
                resultado = Consulta.operaciones(txtProCod.getText(), anios.get(iAnio), "0");
                detalles = Consulta.getMarcaCantidad(txtProCod.getText(), anios.get(iAnio), "0");
                DefaultTableModel model = (DefaultTableModel) tblConsultas.getModel();
                model.setRowCount(0);
                
                for(int i = 0; i < resultado.size(); i++)
                {
                    model.addRow(new Object[]   {
                                                    resultado.get(i).get(0),
                                                    resultado.get(i).get(1),
                                                    resultado.get(i).get(2),
                                                    resultado.get(i).get(3)
                                                });
                }
                consultaRealizada = true;
            }
            else
                JOptionPane.showMessageDialog(null, "Seleccione un año", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione un producto", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void generarReporte(JTextField txtProCod, JComboBox cbxAnio) 
    {
        if(consultaRealizada)
        {
            Producto p = Producto.buscar(txtProCod.getText());
            Reporte.generarReporte2(p, (String) cbxAnio.getSelectedItem(), detalles, resultado, 0);
        }
        else
            JOptionPane.showMessageDialog(null, "Realize primero una consulta", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actualizarAnio(JComboBox cbxAnio) 
    {
        iAnio = cbxAnio.getSelectedIndex();
    }
    
}
