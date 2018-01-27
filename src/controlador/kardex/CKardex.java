/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.kardex;

import com.mxrck.autocompleter.TextAutoCompleter;
import beans.Existencia;
import beans.Kardex;
import beans.Marca;
import beans.Producto;
import beans.Usuario;
import controlador.principal.CMenu;
import frame.kardex.UIKardex;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JosephAWB
 */
public class CKardex implements IKardex
{
    private UIKardex ventana;
    private ArrayList<Existencia> existencias;
    private ArrayList<ArrayList<Kardex>> operaciones;
    private String codigoProducto;
    private String codigoMarca;
    
    public CKardex()
    {
        operaciones = new ArrayList<>();
        existencias = Existencia.getLista();
        

        for(int i = 0; i < existencias.size(); i++)
        {
            operaciones.add(Kardex.getLista(existencias.get(i).getCodPro(), existencias.get(i).getCodMar()));
        }
        
        ventana = new UIKardex(this);
        
    }

    @Override
    public void cargar(JTable tblProductos) 
    {
        
        DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
        model.setRowCount(0);
        
              
        for(int i = 0; i < existencias.size(); i++)
        {
            model.addRow(new Object[]{  Producto.buscar(existencias.get(i).getCodPro()).getProDes(),
                                        Marca.buscar(existencias.get(i).getCodMar()).getMarNom(),
                                        existencias.get(i).getCantidad(),
                                    });
        }
        
    }

    @Override
    public void menu() 
    {
        new CMenu();
        ventana.dispose();
    }

    @Override
    public void modificar(JTable tblProductos) 
    {
        int i = tblProductos.getSelectedRow();
        if(i != -1)
        {
            try
            {
                Existencia p = existencias.get(i);            

                Kardex d = operaciones.get(i).get(operaciones.get(i).size() - 1);
                new CKardexMod(d.getKarCod(), d.getProCod(), d.getMarCod(), p.getCantidad());
                ventana.dispose();
                
            }catch(ArrayIndexOutOfBoundsException e)
            {
                JOptionPane.showMessageDialog(null, "Nada por modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
            }            
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione un producto", "ERROR", JOptionPane.ERROR_MESSAGE);          
    }

    @Override
    public void insertar(JTable tblProductos) 
    {
        int i = tblProductos.getSelectedRow();
        if(i != -1)
        {         
            Existencia p = existencias.get(i);
            new CKardexIns(codigoProducto, codigoMarca, p.getCantidad());
            ventana.dispose();
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione un producto", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void eliminar(JTable tblProductos) 
    {
        int i = tblProductos.getSelectedRow();
        if(i != -1)
        {
            Existencia p = existencias.get(i);
            String cantidad = p.getCantidad();
            
            try
            {                
                Kardex d = operaciones.get(i).get(operaciones.get(i).size() - 1);
                cantidad = Producto.eliminarCantidad(cantidad, d.getKarCan(), d.getKarOpe());
    
                if(JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                {
                    p.modificar(cantidad);
                    d.eliminar(d.getKarCod(), d.getProCod());
                    new CKardex();
                    ventana.dispose();
                }
            }catch(ArrayIndexOutOfBoundsException ex)
            {
                JOptionPane.showMessageDialog(null, "Nada por eliminar", "ERROR", JOptionPane.ERROR_MESSAGE);
            }            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Seleccione un producto", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizarOpe(JTable tblRegistros, JTable tblProductos, JTextField txtUsuario, JTextArea txtObs) 
    {
        int i = tblProductos.getSelectedRow();
        int j = tblRegistros.getSelectedRow();
        
        if(i != -1 && j != -1)
        {
           txtUsuario.setText(Usuario.buscar(operaciones.get(i).get(j).getUsrCod()).getUsrNom() + " " + Usuario.buscar(operaciones.get(i).get(j).getUsrCod()).getUsrApe());
           txtObs.setText(operaciones.get(i).get(j).getKarObs());
           txtObs.setLineWrap(true);
        }
        else
        {
            txtUsuario.setText("");
            txtObs.setText("");
        }
    }

    @Override
    public void actualizar(JTable tblRegistros, JTable tblProductos) 
    {
        codigoProducto = existencias.get(tblProductos.getSelectedRow()).getCodPro();
        codigoMarca = existencias.get(tblProductos.getSelectedRow()).getCodMar();
        
        int i = tblProductos.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblRegistros.getModel();
        model.setRowCount(0);
        ArrayList<Kardex> det = operaciones.get(i);
        
        int detSize = det.size();
        String tipo = "";
        
        for(i = 0; i < detSize; i++)
        {
            if(det.get(i).getKarOpe().equals("1"))
                tipo = "Entrada";
            else
                tipo = "Salida";
            
            model.addRow(new Object[] { new StringBuffer( det.get(i).getKarDia().length() + 
                                                          det.get(i).getKarMes().length() +
                                                          det.get(i).getKarAnio().length() + 2
                                                        ).append(det.get(i).getKarDia())
                                                        .append('/')
                                                        .append(det.get(i).getKarMes())
                                                        .append('/')
                                                        .append(det.get(i).getKarAnio()),
                                        tipo,
                                        det.get(i).getKarCan(),
                                        det.get(i).getKarValUni(),
                                        det.get(i).getKarValTot()
                                        });
        }
    }

    @Override
    public void insertarProducto() 
    {
        new CProductoMarca();
        ventana.dispose();
    }

    @Override
    public void eliminarProducto(JTable tblProductos) 
    {
        int i = tblProductos.getSelectedRow();
        if(i != -1)
        {
            Existencia p = existencias.get(i);
            if(JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                Kardex.eliminar(p.getCodPro());
                p.eliminar();
                new CKardex();
                ventana.dispose();
            }
        }
        else
             JOptionPane.showMessageDialog(null, "Seleccione un registro a eliminar", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void buscarProducto(JTextField buscar, JTable tblProductos) 
    {
        TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( buscar );
        textAutoAcompleter.setMode(-1); // prefijo
        textAutoAcompleter.setCaseSensitive(false); //No sensible a mayúsculas
        TableModel tableModel = tblProductos.getModel();
        String filtro = "Producto";
        
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

    @Override
    public void seleccionarFila(JTextField buscar, JTable tblProductos) 
    {
        TableModel tableModel = tblProductos.getModel();
        String dato = buscar.getText();
        String filtro = "Producto";
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
                tblProductos.changeSelection(0,0,false,true);
            else
                tblProductos.getSelectionModel().setSelectionInterval(row - 1, row);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "No se encontraron los datos buscados", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
