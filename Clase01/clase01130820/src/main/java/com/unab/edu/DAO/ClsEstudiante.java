/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unab.edu.DAO;

import com.unab.edu.Entidades.Estudiante;
import com.unab.edu.Entidades.Persona;

import com.unab.edu.conexionmysql.ConexionBd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Stanly
 */
public class ClsEstudiante {

    ConexionBd claseconectar = new ConexionBd();
    Connection conectar = claseconectar.retornarConexion();

    public boolean LoguinEstudiante(String usuario, String Pass) {
        ArrayList<Estudiante> ListaUsuariosPass = new ArrayList<>();

        try {
            CallableStatement Statement = conectar.prepareCall("call SP_S_LoguinEstudiante(?,?)");
            Statement.setString("pusuario", usuario);
            Statement.setString("ppass", Pass);

            ResultSet resultadoDeConsulta = Statement.executeQuery();

            while (resultadoDeConsulta.next()) {

                Estudiante es = new Estudiante();
                es.setUsu(resultadoDeConsulta.getString("USU"));
                es.setPass(resultadoDeConsulta.getString("PASS"));
                ListaUsuariosPass.add(es);

            }
            String usuariodebasededatos = null;
            String pasdebasededaros = null;
            for (var iterador : ListaUsuariosPass) {

                usuariodebasededatos = iterador.getUsu();
                pasdebasededaros = iterador.getPass();

            }
            if (usuariodebasededatos.equals(usuario) && pasdebasededaros.equals(Pass)) {
                return true;

            }
            conectar.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

        return false;
    }

    // Crud de estudiante ------------inicia aqui------------
    public ArrayList<Estudiante> MostrarEstudiante() {
        ArrayList<Estudiante> Estudiantes = new ArrayList<>();

        try {
            CallableStatement Statement = conectar.prepareCall("call SP_S_JOINMOSTRARESTUDIANTE()");
            ResultSet RS = Statement.executeQuery();
            while (RS.next()) {
                Estudiante est = new Estudiante();
                est.setId(RS.getInt("idestudiante"));
                est.setMatricula(RS.getInt("matricula"));
                est.setIdPersona(RS.getInt("idPersona"));
                est.setNombre(RS.getString("Nombre"));
                est.setUsu(RS.getString("USU"));
                est.setPass(RS.getString("PASS"));
                est.setNIE(RS.getString("NIE"));

                Estudiantes.add(est);
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return Estudiantes;

    }

    public void GuardarEstudiante(Estudiante Estu) {
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_I_ESTUDIANTE(?,?,?,?,?)");

            Statement.setInt("Pmatricula", Estu.getMatricula());
            Statement.setInt("PidPersona", Estu.getIdPersona());
            Statement.setString("PUSU", Estu.getUsu());
            Statement.setString("pPASS", Estu.getPass());
            Statement.setString("pNIE", Estu.getNIE());
            Statement.execute();
            JOptionPane.showMessageDialog(null, " Estud iante guardado con exito");
          
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void BorrarEstudiante(Estudiante Estu) {
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_D_ESTUDIANTE(?)");
            Statement.setInt("PIDestduaintes", Estu.getId());
            Statement.execute();
            JOptionPane.showMessageDialog(null, "Estudiante Eliminado");
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void ActualizarEstuiante(Estudiante Estu) {
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_U_ESTUDIANTE(?,?,?,?,?,?)");
            Statement.setInt("pIDestudiantes", Estu.getId());
            Statement.setInt("pMatricula", Estu.getMatricula());
            Statement.setInt("pIDpersonas", Estu.getIdPersona());
            Statement.setString("pUSUARIO", Estu.getUsu());
            Statement.setString("pPassword", Estu.getPass());
            Statement.setString("pNie", Estu.getNIE());
            Statement.execute();
           
            JOptionPane.showMessageDialog(null, " Estudiante Actualizado Correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

}
