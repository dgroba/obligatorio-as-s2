/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.persistencia;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import uy.com.gameon.dominio.Consola;
import uy.com.gameon.dominio.Genero;
import uy.com.gameon.dominio.Usuario;

/**
 *
 * @author diegogroba
 */
@Singleton
public class BaseDeDatosSingletonSB implements BaseDeDatosSingletonSBLocal {
    public Map<String, Usuario> usuarios;
    public Long contadorIdUsuarios;
    public Map<String, Genero> generos;
    public Map<String, Consola> consolas;
    public Map<String, String> usuariosAutenticados;

    public BaseDeDatosSingletonSB() {
        this.contadorIdUsuarios = new Long(0);
        this.usuarios = new HashMap<>();
        this.generos = new HashMap<>();
        this.consolas = new HashMap<>();
        this.usuariosAutenticados = new HashMap<>();
    }
    
    @Override
    public void agregarConsola(Consola consola) {
        this.consolas.put(consola.getCodigo(), consola);
    }
    
    @Override
    public void agregarGenero(Genero genero) {
        this.generos.put(genero.getCodGenero(), genero);
    }
    
    @Override
    public Long agregarUsuario(Usuario usuario){
        usuario.setId(++this.contadorIdUsuarios);
        this.usuarios.put(usuario.getEmail(), usuario);
        return this.contadorIdUsuarios;
    }
    
    @Override
    public Map<String, Consola> obtenerConsolas() {
        return this.consolas;
    }
    
    @Override
    public Map<String, Genero> obtenerGeneros() {
        return this.generos;
    }
    
    @Override
    public Map<String, Usuario> obtenerUsuarios() {
        return this.usuarios;
    }
    
    @Override
    public Usuario obtenerUsuarioPorEmail(String emailUsuario) {
        return this.usuarios.get(emailUsuario);
    }
    
    @Override
    public void agregarUsuarioAutenticado(String emailUsuario, String authKey) {
        this.usuariosAutenticados.put(emailUsuario, authKey);
    }
    
    @Override
    public void quitarUsuarioAutenticado(String emailUsuario) {
        if (usuariosAutenticados.containsKey(emailUsuario)) {
            this.usuariosAutenticados.remove(emailUsuario);
        }
    }
    
    @Override
    public Boolean usuarioAutenticado(String emailUsuairo) {
        return this.usuariosAutenticados.containsKey(emailUsuairo);
    }
    
    @PostConstruct
    private void init(){
        this.agregarUsuario(new Usuario("Nombre 1", "Apellido 1", "email1@icloud.com", "pass1"));
        this.agregarUsuario(new Usuario("Nombre 2", "Apellido 2", "email2@icloud.com", "pass2"));
        this.agregarUsuario(new Usuario("Nombre 3", "Apellido 3", "email3@icloud.com", "pass3"));
        
        this.agregarGenero(new Genero("act", "Action"));
        this.agregarGenero(new Genero("shoot", "Shooter"));
        this.agregarGenero(new Genero("adv", "Adventure"));
        this.agregarGenero(new Genero("sim", "Simulation"));
        this.agregarGenero(new Genero("str", "Strategy"));
        this.agregarGenero(new Genero("sp", "Sports"));
        this.agregarGenero(new Genero("rol", "Role Playing"));
        
        this.agregarConsola(new Consola("xbx1", "Xbox One", "Microsoft"));
        this.agregarConsola(new Consola("xbx360", "Xbox 360", "Microsoft"));
        this.agregarConsola(new Consola("ps4", "PlayStation 4", "Sony"));
        this.agregarConsola(new Consola("ps3", "PlayStation 3", "Sony"));
        this.agregarConsola(new Consola("wii", "Wii", "Nintendo"));
        this.agregarConsola(new Consola("wiiu", "Wii U", "Nintendo"));
    }

    @Override
    public Boolean usuarioExistente(String emailUsuario) {
        return this.usuarios.containsKey(emailUsuario);
    }

    /**
     * 
     * @param emailUsuario Usuario existente
     * @param password
     * @return 
     */
    @Override
    public Boolean passwordCorrecto(String emailUsuario, String password) {
        return this.usuarios.get(emailUsuario).getPassword().equals(password);
    }

    @Override
    public Boolean authTokenValido(String authToken, String emailUsuario) {
        if (this.usuariosAutenticados.containsKey(emailUsuario)) {
            return this.usuariosAutenticados.get(emailUsuario).equals(authToken);
        } else {
            return false;
        }
    }
    
}
