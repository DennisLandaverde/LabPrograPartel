/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unab.edu.DAO;

import com.unab.edu.Entidades.Persona;
import com.unab.edu.conexionmysql.ConexionBd;
import java.sql.*;

import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Stanly
 */
public class ClsPersona {

    ConexionBd claseconectar = new ConexionBd();
    Connection conectar = claseconectar.retornarConexion();

    public ArrayList<Persona> MostrarPersona() {
        ArrayList<Persona> Personas = new ArrayList<>();

        try {
            CallableStatement Statement = conectar.prepareCall("call SP_s_Persona()");
            ResultSet resultadoDeConsulta = Statement.executeQuery();

            while (resultadoDeConsulta.next()) {

                Persona persona = new Persona();
                persona.setIdPersona(resultadoDeConsulta.getInt("idPersona"));
                persona.setNombre(resultadoDeConsulta.getString("Nombre"));
                persona.setApellido(resultadoDeConsulta.getString("Apellido"));
                persona.setEdad(resultadoDeConsulta.getInt("Edad"));
                persona.setSexo(resultadoDeConsulta.getString("sexo"));

                Personas.add(persona);

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

        return Personas;
    }

    public void AgregarPersona(Persona Per) {
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_I_Persona(?,?,?,?)");

            Statement.setString("PNombre", Per.getNombre());
            Statement.setString("PApellido", Per.getApellido());
            Statement.setInt("PEdad", Per.getEdad());
            Statement.setString("PSexo", Per.getSexo());
            Statement.execute();
            JOptionPane.showMessageDialog(null, " Persona guardada");
         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void BorrarPersona(Persona Per) {
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_D_Persona(?)");
            Statement.setInt("PIdPersonas", Per.getIdPersona());
            Statement.execute();
            JOptionPane.showMessageDialog(null, "Persona Eliminada");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void ActualizarPersona(Persona Persona) {
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_U_Persona(?,?,?,?,?)");
            Statement.setInt("PIdPersonas", Persona.getIdPersona());
            Statement.setString("PNombre", Persona.getNombre());
            Statement.setString("PApellido", Persona.getApellido());
            Statement.setInt("PEdad", Persona.getEdad());
            Statement.setString("PSexo", Persona.getSexo());
            Statement.execute();
             
            JOptionPane.showMessageDialog(null, " Persona Actualizada Correctamente");
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

}
