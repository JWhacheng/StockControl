/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.helper;

import com.mxrck.autocompleter.TextAutoCompleter;
import beans.Producto;
import frame.helper.UIProductoHelp;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JosephAWB
 */
public class CProductoHelp implements IProductoHelp
{
    private UIProductoHelp ventana;
    private ArrayList<Producto> productos;
    private JTextField txtProCod;
    private JTextField txtProDes;

    
    public CProductoHelp(JFrame owner, JTextField txtProCod, JTextField txtProDes)
    {
        productos = Producto.getLista();
        this.txtProCod = txtProCod;
        this.txtProDes = txtProDes;
        ventana = new UIProductoHelp(owner, true, this);
    }

    @Override
    public void cargar(JTable tblRegistros) 
    {        
        DefaultTableModel model = (DefaultTableModel) tblRegistros.getModel();
        model.setRowCount(0);
		
        int productosSize = productos.size();
        for(int i = 0; i < productosSize; i++)
        {
            model.addRow(new Object[]{  productos.get(i).getProCod(),
                                        productos.get(i).getProDes()
                                     });
        }
    }

    @Override
    public boolean aceptar(JTable tblRegistros) 
    {
        boolean seleccionado = false;
        int i = tblRegistros.getSelectedRow();
        if(i != -1)
        {          
            Producto p = productos.get(i);
            txtProCod.setText(p.getProCod());
            txtProDes.setText(p.getProDes());
            seleccionado = true;
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione un producto", "ERROR", JOptionPane.ERROR_MESSAGE);
        return seleccionado;
    }

    @Override
    public void seleccionarFila(JTextField buscar, JTable tblRegistros) 
    {
        TableModel tableModel = tblRegistros.getModel();
        String dato = buscar.getText();
        String filtro = "Descripción";
        int col;
		int column = tableModel.getColumnCount();
        for(col = 0; col < column; col++)
            if(filtro.compareTo(tableModel.getColumnName(col)) == 0)
                break;
        int row;
        try
        {
			int rowC = tableModel.getRowCount();
            for(row = 0; row < rowC; row++)
                if(dato.compareTo((String) tableModel.getValueAt(row, col)) == 0)
                    break;

            if(row == 0)
                tblRegistros.changeSelection(0,0,false,true);
            else
                tblRegistros.getSelectionModel().setSelectionInterval(row - 1, row);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "No se encontraron los datos buscados", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void buscarProducto(JTextField buscar, JTable tblRegistros) 
    {
        TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( buscar );
        textAutoAcompleter.setMode(-1); // prefijo
        textAutoAcompleter.setCaseSensitive(false); //No sensible a mayúsculas
        TableModel tableModel = tblRegistros.getModel();
        String filtro = "Descripción";
        
        int i;
        int column = tableModel.getColumnCount();
        
	for(i = 0; i < column; i++)
        {
            if(filtro.compareTo(tableModel.getColumnName(i)) == 0)
                break;
        }
        int row = tableModel.getRowCount();
        for(int k = 0; k < row; k++)
        {
            textAutoAcompleter.addItem(tableModel.getValueAt(k, i));
        }
    }
}
