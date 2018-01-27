/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static gestordeinventarios.Principal.con;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author JosephAWB
 */
public class Kardex 
{
    private String karCod;
    private String karAnio;
    private String karMes;
    private String karDia;
    private String proCod;
    private String marCod;
    private String karCan;
    private String karValUni;
    private String karValTot;
    private String karOpe;
    private String karObs;
    private String usrCod;
    
    
    public Kardex()
    {
        this("-1", "-1", "-1", "-1", "0", "0", "0", "0", "0", "0", "0", "NULL");
    }
    
    public Kardex(String karCod, String proCod, String marCod, String usrCod, String karAnio, String karMes, String karDia, String karCan, String karValUni, String karValTot, String karOpe, String karObs)
    {
        this.karCod = karCod;
        this.proCod = proCod;
        this.marCod = marCod;
        this.usrCod = usrCod;
        this.karAnio = karAnio;
        this.karMes = karMes;
        this.karDia = karDia;
        this.karCan = karCan;
        this.karValUni = karValUni;
        this.karValTot = karValTot;
        this.karOpe = karOpe;
        this.karObs = karObs;
        
    }

    /**
     * @return the karCod
     */
    public String getKarCod() {
        return karCod;
    }

    /**
     * @param karCod the karCod to set
     */
    public void setKarCod(String karCod) {
        this.karCod = karCod;
    }

    /**
     * @return the proCod
     */
    public String getProCod() {
        return proCod;
    }

    /**
     * @param proCod the proCod to set
     */
    public void setProCod(String proCod) {
        this.proCod = proCod;
    }

    /**
     * @return the karCan
     */
    public String getKarCan() {
        return karCan;
    }

    /**
     * @param karCan the karCan to set
     */
    public void setKarCan(String karCan) {
        this.karCan = karCan;
    }

    /**
     * @return the karValUni
     */
    public String getKarValUni() {
        return karValUni;
    }

    /**
     * @param karValUni the karValUni to set
     */
    public void setKarValUni(String karValUni) {
        this.karValUni = karValUni;
    }

    /**
     * @return the karValTot
     */
    public String getKarValTot() {
        return karValTot;
    }

    /**
     * @param karValTot the karValTot to set
     */
    public void setKarValTot(String karValTot) {
        this.karValTot = karValTot;
    }

    /**
     * @return the karOpe
     */
    public String getKarOpe() {
        return karOpe;
    }

    /**
     * @param karOpe the karOpe to set
     */
    public void setKarOpe(String karOpe) {
        this.karOpe = karOpe;
    }

    /**
     * @return the karObs
     */
    public String getKarObs() {
        return karObs;
    }

    /**
     * @param karObs the karObs to set
     */
    public void setKarObs(String karObs) {
        this.karObs = karObs;
    }
    
    /**
     * @return the usrCod
     */
    public String getUsrCod() {
        return usrCod;
    }

    /**
     * @param usrCod the usrCod to set
     */
    public void setUsrCod(String usrCod) {
        this.usrCod = usrCod;
    }

    /**
     * @return the karAnio
     */
    public String getKarAnio() {
        return karAnio;
    }

    /**
     * @param karAnio the karAnio to set
     */
    public void setKarAnio(String karAnio) {
        this.karAnio = karAnio;
    }

    /**
     * @return the karMes
     */
    public String getKarMes() {
        return karMes;
    }

    /**
     * @param karMes the karMes to set
     */
    public void setKarMes(String karMes) {
        this.karMes = karMes;
    }

    /**
     * @return the karDia
     */
    public String getKarDia() {
        return karDia;
    }

    /**
     * @param karDia the karDia to set
     */
    public void setKarDia(String karDia) {
        this.karDia = karDia;
    }
    
    /**
     * @return the marCod
     */
    public String getMarCod() {
        return marCod;
    }

    /**
     * @param marCod the marCod to set
     */
    public void setMarCod(String marCod) {
        this.marCod = marCod;
    }
    
    public String insertar()
    {
        String msg = "";
        try
        {
            con.ejecutar("INSERT INTO KARDEX VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
                            new String[] {
                            marCod,
                            proCod,                            
                            usrCod,
                            karAnio,
                            karMes,
                            karDia,
                            karCan, 
                            karValUni, 
                            karValTot,
                            karOpe,
                            karObs
                            }, 
                            false
                        );
        }
        catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String eliminar(String codigo1, String codigo2)
    {
        String msg = "";
        try
        {
            con.ejecutar("DELETE FROM KARDEX WHERE (idKardex = ? AND idProducto = ?)", new String[] {codigo1, codigo2} , false);
        }
        catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public static String eliminar(String codPro)
    {
       String msg = "";
        try
        {
            con.ejecutar("DELETE FROM KARDEX WHERE idProducto = ?", new String[] {codPro} , false);
        }
        catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg; 
    }
    
    public String modificar()
    {
        String msg = "";
        try
        {
            con.ejecutar("UPDATE KARDEX SET año = ?, "
                                            + "mes = ?, "
                                            + "dia = ?, "
                                            + "idUsuario = ?, "
                                            + "cantidad = ?, "
                                            + "precioUni = ?, "
                                            + "precioTot = ?, "
                                            + "tipoOpe = ?, "
                                            + "observacion = ? "
                                            + "WHERE (idKardex = ? AND idProducto = ? AND idMarca = ?)", 
                        new String[] {
                                            karAnio,
                                            karMes,
                                            karDia,
                                            usrCod,
                                            karCan,
                                            karValUni,
                                            karValTot,
                                            karOpe,
                                            karObs,
                                            karCod,
                                            proCod,
                                            marCod
                        }, 
                        false);
        }
        catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public static Kardex buscar(String codigo1, String codigo2, String codigo3)
    {
        Kardex detalle = null;
        try
        {
            ResultSet rs = con.ejecutar("SELECT * FROM KARDEX WHERE idKardex = ? AND idProducto = ? AND idMarca = ?", new String[] {codigo1, codigo2, codigo3}, true);
            rs.next();
            
            detalle = new Kardex();
            detalle.setKarCod(rs.getString("idKardex"));
            detalle.setProCod(rs.getString("idProducto"));
            detalle.setMarCod(rs.getString("idMarca"));
            detalle.setUsrCod(rs.getString("idUsuario"));
            detalle.setKarAnio(rs.getString("año"));
            detalle.setKarMes(rs.getString("mes"));
            detalle.setKarDia(rs.getString("dia"));
            detalle.setKarCan(rs.getString("cantidad"));
            detalle.setKarValUni(rs.getString("precioUni"));
            detalle.setKarValTot(rs.getString("precioTot"));
            detalle.setKarOpe(rs.getString("tipoOpe"));
            detalle.setKarObs(rs.getString("observacion")); 
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return detalle;
    }
    
    public static String sgtCodigo()
    {
        String codigo = "000000";
        try
        {
            ResultSet rs = con.ejecutar("SELECT LPAD((SELECT COUNT(*) + 1 FROM KARDEX), 6, '0') AS nextCod", null, true);
            rs.next();
            codigo = rs.getString("nextCod");
        }
        catch (SQLException ex)
        {
             JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return codigo;
    }
    
    public static ArrayList<Kardex> getLista(String codPro, String codMar)
    {
        ArrayList<Kardex> operaciones = new ArrayList<>();
        try
        {
            ResultSet rs = con.ejecutar("SELECT * FROM KARDEX WHERE idProducto = ? AND idMarca = ?", new String [] {codPro, codMar}, true);
            while(rs.next())
            {
                Kardex k = new Kardex();
                k.setKarCod(rs.getString("idKardex"));
                k.setProCod(codPro);
                k.setMarCod(codMar);
                k.setKarAnio(rs.getString("año"));
                k.setKarMes(rs.getString("mes"));
                k.setKarDia(rs.getString("dia"));
                k.setUsrCod(rs.getString("idUsuario"));
                k.setKarCan(rs.getString("cantidad"));
                k.setKarValUni(rs.getString("precioUni"));
                k.setKarValTot(rs.getString("precioTot"));
                k.setKarOpe(rs.getString("tipoOpe"));
                k.setKarObs(rs.getString("observacion"));
                
                operaciones.add(k);
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return operaciones;
    }  
    
}
