/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.ws.*;
import javax.ws.rs.Path;
import DTOs.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.io.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.simple.JSONObject;
import logica.Fabrica;
import logica.interfaz.*;
import Interface.IControladorFuncion;
import java.text.SimpleDateFormat;
import org.json.simple.JSONArray;

/**
 *
 * @author Nico
 */
@Path("usuarios")
public class Usuarios {
    
    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
    
    
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)    
    public Response registro(@Valid RegistroDto usuario)
    {
        JSONObject json = new JSONObject();
        try{
            int continuo = 1;
            String esArtista = "N";
            if(usuario.getNickname().isEmpty())
            {
                continuo = 0;
                json.put("msg", "Usuario no fue ingresado.");
                json.put("code", "10000");
            }
            
            if(continuo == 1 && usuario.getMail().isEmpty())
            {
                continuo = 0;
                json.put("msg", "El correo electronico no fue ingresado.");
                json.put("code", "10000");
            }
            
            if(continuo == 1 && usuario.getNombre().isEmpty())
            {
                continuo = 0;
                json.put("msg", "El nombre no fue ingresado.");
                json.put("code", "10000");
            }
            
            if(continuo == 1 && usuario.getApellido().isEmpty())
            {
                continuo = 0;
                json.put("msg", "El apellido no fue ingresado.");
                json.put("code", "10000");
            }
            
            if(continuo == 1 && usuario.getTipo().isEmpty())
            {
                continuo = 0;
                json.put("msg", "Error a la hora de obetener que tipo de usuario.");
                json.put("code", "10000");
            }else{
                if(usuario.getTipo().equals("A"))//Es un artista
                {
                    esArtista = "S";
                }
            }
            
            
            if(esArtista.equals("S") && continuo == 1 && usuario.getDescripcion().isEmpty())
            {
                continuo = 0;
                json.put("msg", "La descripcion no fue ingresada.");
                json.put("code", "10000");
            }
            
             if(continuo == 1 && usuario.getFechaNacimiento().isEmpty())
            {
                continuo = 0;
                json.put("msg", "La fecha de nacimiento no fue ingresada.");
                json.put("code", "10000");
            }
            
            if(continuo == 1)
            {
               String resultado = ICU.crearUsuario(usuario.getNickname(),usuario.getNombre(),usuario.getApellido(),usuario.getFechaNacimiento(),usuario.getMail(),usuario.getClave(),esArtista,usuario.getDescripcion(),usuario.getBiografia(),usuario.getUrl(),usuario.getImagen()); 
               json.put("msg","Proceso con exito");
               json.put("data", resultado);
               json.put("code", "0");
            }
            
            return Response.ok("["+json.toJSONString()+"]", MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)    
    public Response login(@Valid UsuarioDto usuario)
    {
        JSONObject json = new JSONObject();
        try{
            String nicknameUsuario = "";
            String clave = "";
            int continuo = 1;
            
            if(!usuario.getNickname().isEmpty())
            {
                nicknameUsuario = usuario.getNickname();
            } else if(!usuario.getCorreo().isEmpty()){
                nicknameUsuario = usuario.getCorreo();
            }else{
                continuo = 0;
                json.put("msg", "Usuario o correo electronico no fueron ingresados.");
                json.put("code", "10000");
            }
            
            if(continuo == 1)
            {
                if(!usuario.getClave().isEmpty())
                {
                    clave = usuario.getClave();
                }else{
                    continuo = 0;
                    json.put("msg", "La contraseña no fue ingresada.");
                    json.put("code", "10001");
                }
            }
            
            if(continuo == 1)
            {
                if(nicknameUsuario.contains("@")){
                    nicknameUsuario = ICU.nickUsuario(nicknameUsuario, clave);
                }
                int login = ICU.login(nicknameUsuario, clave);
                if(login > 0 )//Es que se logueo
                {
                    json.put("msg", "Inicio existoso..");
                    json.put("dato",login);
                    json.put("code", "0");
                }else{
                    json.put("msg", "Las credenciales no son validas, intente de nuevo.");
                    json.put("code", "10002");   
                }
            }
            
            
            return Response.ok("["+json.toJSONString()+"]",MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/obtenerDatos")
    public Response obtenerPaquetes(@QueryParam("usuario") String usuario)
    {
        try{
            
            Clases.Usuarios dato = ICU.Consultar_un_Espectador_particular(usuario);
            
            Clases.Artista artista = null;
            if(ICU.Artista_o_Espectador(usuario) == 1)//Es artista
            {
                artista = ICU.Consultar_un_artista_particular(usuario);
            }
            JSONObject json = new JSONObject();
            JSONArray usu = new JSONArray();
            usu.add(dato);
            JSONArray art = new JSONArray();
            art.add(artista);
             
            StringWriter out = new StringWriter();
            usu.writeJSONString(out); 
            StringWriter out1 = new StringWriter();
            art.writeJSONString(out1); 
            json.put("usuario", out); 
            json.put("artista", out1);
            
            return Response.ok("["+json.toJSONString()+"]", MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage()).build();
        }
    }
    
    
    @POST
    @Path("/modificarUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarUsuario(@Valid ModificarUsuarioDto usuario) {
        try {

            String tipoUsuario = usuario.getTipoUsuario();
            String nick = usuario.getNickCorreo();
            String password = usuario.getPassword();

            String nombre = usuario.getNombre();
            String apellido = usuario.getApellido();
            String fechaString = usuario.getFecha();
            String json = "{"
                    + "\"code\":" + "0,"
                    + "\"msg\":" + "\"Se actualizo correctamente\"}";

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(fechaString);
            java.sql.Date fecha = new java.sql.Date(date.getTime());

           if (tipoUsuario.equals("1")) {

                if (ICU.EdiarEspectador(nick, nombre, apellido, password, fecha) == false) {
                    json = "{"
                            + "\"code\":" + "10,"
                            + "\"msg\":" + "\"Error a la hora de  actualizar.\"}";
                }
            } else {

                String biografia = usuario.getBiografia();
                String descripcion = usuario.getDescripcion();
                String URL = usuario.getUrl();

                if (ICU.editarArtistas(nick, nombre, apellido, password, fecha, descripcion, URL, biografia) == false) {
                    json = "{"
                            + "\"code\":" + "100,"
                            + "\"msg\":" + "\"Error a la hora de  actualizar.\"}";
                }
            }

            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }

    }
    
}
