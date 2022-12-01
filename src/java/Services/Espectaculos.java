/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;
import DTOs.*;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import logica.Fabrica;
import logica.interfaz.*;
import org.json.simple.JSONObject;
import Clases.*;
import Interface.IControladorFuncion;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
/**
 *
 * @author Nico
 */
@Path("espectaculos")
public class Espectaculos {
    IControladorEspetaculo ICE = Fabrica.getInstance().getIControladorEspectaculo();
    IControladorFuncion ICF = Fabrica.getInstance().getIControladorFuncion();
    IControladorPaquetes ICP = Fabrica.getInstance().getIControladorPaquete();
    
    @GET
    @Path("/obtener")
    public Response obtenerEspectaculos(@QueryParam("estado") String estado)
    {
        
        try
        {
            String est = "";
            if(estado != null)
            {
               est = estado;
            }
            List<Clases.Espectaculo> espectaculos = (List<Clases.Espectaculo>) ICE.obtenerEspectaculosEstado(est);

            String json = "";

            for(int i = 0; i < espectaculos.size(); i ++)
            {
                if(i > 0)
                {
                    json = json +",";
                }
                json = json +"{"
                        + "\"nombre\":" + "\""+ espectaculos.get(i).getNombre() +"\","
                        + "\"artista\":" + "\""+ espectaculos.get(i).getArtistaOrganizador()+"\","
                        + "\"descripcion\":" + "\""+ espectaculos.get(i).getDescripcion().trim() +"\","
                        + "\"url\":" + "\""+ espectaculos.get(i).getURL() +"\","
                        + "\"duracion\":" + ""+ String.valueOf(espectaculos.get(i).getDuracion()) +","
                        + "\"minima\":" + ""+ String.valueOf(espectaculos.get(i).getCapacidadMinima()) +","
                        + "\"maxima\":" + ""+ String.valueOf(espectaculos.get(i).getCapacidadMaxima()) +","
                        + "\"costo\":" + ""+ String.valueOf(espectaculos.get(i).getCosto()) +","
                        + "\"creado\":" + "\""+ String.valueOf(espectaculos.get(i).getFechaCreado()) +"\","
                        + "\"plataforma\":" + "\""+ String.valueOf(espectaculos.get(i).getPLataforma()) +"\","
                        + "\"estado\":" + "\""+ espectaculos.get(i).getEstado()+"\","
                        + "\"imagen\":" + "\""+ espectaculos.get(i).getImagen()+"\""
                        + "}";
            }

            
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        } 
    }
    
    @GET
    @Path("/verificarNombre")
    public Response verificarNombre(@QueryParam("nombre") String nombre)
    {
        try{
            boolean existe = ICE.checkEspectaculo(nombre);
            String existeS = "N";
            if(existe == true)
            {
                existeS = "S";
            }
            return Response.ok(existeS,MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/verificarNombreFuncion")
    public Response verificarNombreFuncion(@QueryParam("nombre") String nombre)
    {
        try{
            String existe = ICF.existeNombreFuncion(nombre);
        
            return Response.ok(existe,MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/crearFuncion")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearFuncion(@Valid FuncionDto funcion)
    {
        try{
            
            String espectaculo = funcion.getEspectaculo();
            String nombre = funcion.getNombre();
            String fechaInicio[] = funcion.getFechaInicio().split("-");
            String horaInicio[] = funcion.getHoraInicio().split(":");
            String artistas[] = funcion.getArtistas().split(",");
            String url = funcion.getUrl();

            List<String> artistaList = new ArrayList<String>();
        
            for(int i = 0;i < artistas.length;i++)
            {
                artistaList.add(artistas[i]);
            }


            int resultado =  ICF.AltaFuncion(espectaculo, nombre, String.valueOf(fechaInicio[2]), String.valueOf(fechaInicio[1]), String.valueOf(fechaInicio[0]), horaInicio[0], horaInicio[1], artistaList, null,url );
            String creado = "S";
            if(resultado != 8 && resultado != 9)
            {
                creado = "N";
            }
            return Response.ok(creado, MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/crear")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearEspectaculo(@Valid EspectaculoDto espectaculo)
    {
        try{
            String nombre = espectaculo.getNombre();
            String descripcion = espectaculo.getDescripcion();
            String artista = espectaculo.getArtista();
            String duracion = espectaculo.getDuracion();
            String espectadoresMinimos = espectaculo.getEspectadoresMinimo();
            String espectadoresMaximos = espectaculo.getEspectadoresMaximo();
            String URL = espectaculo.getURL(); 
            String costo = espectaculo.getCosto();
            String plataforma = espectaculo.getPlataforma();
            String categorias = espectaculo.getCategorias();
            String imagen = espectaculo.getImagen();
            
            String[] partes = categorias.split("-");

            ArrayList<String> partesFinal = new ArrayList<>();

            partesFinal.addAll(Arrays.asList(partes));

            String json = ICE.nuevoEspectaculo1(nombre, plataforma, artista, descripcion, duracion, espectadoresMaximos, espectadoresMinimos, URL, costo, partesFinal, imagen);
            return Response.ok(json,MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/obtenerFuncionesEspectaculos")
    public Response obtenerFuncionesEspectaculo(@QueryParam("espectaculo") String espectaculo)
    {
        try{
            List<Funciones> fun = (List<Funciones>) ICF.ListarFuncionesEspectaculo(espectaculo);
        

            String json = "";

            for(int i = 0; i < fun.size(); i ++)
            {
                if(i > 0)
                {
                    json = json +",";
                }
                List<Artista> artistas = (List<Artista>) ICF.obtenerArtistasFuncion(fun.get(i).getNombre());
                String jsonArtistas = "";
                for(int j = 0; j < artistas.size(); j++)
                {
                    if(j > 0)
                    {
                        jsonArtistas = jsonArtistas +",";
                    }        
                    jsonArtistas =  jsonArtistas+ "{"
                                                + "\"nombre\":" + "\""+artistas.get(j).getNombre()+" "+artistas.get(j).getApellido()+"\""
                                                + "}";
                }
                json = json +"{"
                        + "\"nombre\":" + "\""+ fun.get(i).getNombre() +"\","
                        + "\"imagen\":" + "\""+ fun.get(i).getImagen() +"\","
                        + "\"fecha\":" + "\""+ fun.get(i).getHoraDeInicio().toString()+"\","
                        + "\"artistas\": [" + jsonArtistas +" ]"
                        + "}";
            }
            json = "[" + json + "]";
            
            return Response.ok(json,MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/funcion/preRegistro")
    public Response preRegistroFuncion(@Valid RegistroFuncionDto registro)
    {
        try{
            String json = "";
            
            String nombreEspectaculo = registro.getEspectaculo();
            String nombreFuncion = registro.getFuncion();

            String nickname = registro.getCorreo();
            //Primero me fijo si esta lleno es espectaculo
            if(ICF.EspectaculoLleno(nombreEspectaculo, nombreFuncion) == 1)//Es que esta lleno
            {
                json = "{"
                        + "\"code\":" + "100,"
                        + "\"msg\": \"El espectaculo esta lleno, intente con otro.\""
                        + "}";
            }else{
                if(ICF.EspectadorEnFuncion(nickname, nombreFuncion) == 1)
                {
                    json = "{"
                        + "\"code\":" + "30,"
                        + "\"msg\": \"Ya esta registrado a la funcion intente otro.\""
                        + "}";
                }else{
                    //Me fijo si tiene para canjear
                    if(ICF.tieneCanjeDisponible(nickname).equals("S")) //Si tiene canje entonces retorno para canjear
                    {
                        String jsonRegistro = ICF.obtenerRegistroCanjera(nickname);
                        json = "{"
                            + "\"code\":" + "10,"
                            + "\"msg\": \"Tienes canje para realizar, seleccione dos registros para poder realizar el canje.\","
                            + "\"registrosCanje\":" + "["+jsonRegistro+"]"
                            + "}";
                    }else{ //Si no hago el registro
                        ICF.RegistrarEspectadorFuncion(nickname, nombreEspectaculo, nombreFuncion);
                        json = "{"
                            + "\"code\":" + "0,"
                            + "\"msg\": \"Se registro correctamente a la funcion.\""
                            + "}";
                    }

                }
            }
            
            return Response.ok("["+json+"]", MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/funcion/registro")
    public Response registrarFuncion(@Valid RegistroFuncionDto registro)
    {
        try{
            String json = "";
            String esCanje = registro.getEsCanje();
            String nombreEspectaculo = registro.getEspectaculo();
            String nombreFuncion = registro.getFuncion();
            String nickname = registro.getCorreo();

            
            //Primero chequeo si 

            if(esCanje.equals("S"))//Si es canje entonces sigo con lo que tengo que hacer
            {
                String registrosCanjear =  registro.getRegistros();
                String[] registros = registrosCanjear.split(",");
                List registrosPrevios = new ArrayList<>();
                for(int i = 0;i < registros.length; i++)
                {
                    registrosPrevios.add(registros[i]);
                }
                ICF.CanjearRegistroEspectadorFuncion(nickname, nombreEspectaculo, nombreFuncion, registrosPrevios);
                json = "{"
                        + "\"code\":" + "0,"
                        + "\"msg\": \"Se registro correctamente a la funcion y se realizo el canje correctamente.\""
                        + "}";
            }else{
                ICF.RegistrarEspectadorFuncion(nickname, nombreEspectaculo, nombreFuncion);
                json = "{"
                        + "\"code\":" + "0,"
                        + "\"msg\": \"Se registro correctamente a la funcion.\""
                        + "}";
            }
            return Response.ok("["+json+"]").build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/paquete/comprar")
    public Response comprarPaquete(@Valid ComprarPaqueteDto paquetes)
    {
        try{
            String json = "";
            String paquete = paquetes.getPaquete();
            String nickname = paquetes.getNickname();

            String compra = ICP.comprarPaquete(paquete, nickname);

            if(compra.equals("S"))///Todo ok
            {
                json = "{"
                        + "\"code\":" + "0,"
                        + "\"msg\": \"Se compro correctamente el paquete.\""
                        + "}";
            }else if(compra.equals("Y")){
                json = "{"
                        + "\"code\":" + "10,"
                        + "\"msg\": \"Ya se compro el paquete..\""
                        + "}";
            } else{
                json = "{"
                        + "\"code\":" + "101,"
                        + "\"msg\": \"Error a la hora de comprar el paquete.\""
                        + "}";
            }
            return Response.ok("["+json+"]",MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage(),MediaType.APPLICATION_JSON).build();
        }
    }
    
    
    @GET
    @Path("/actualizarFavorito")
    public Response actualizarEspectaculoFavorito(@QueryParam("nickUsuario") String nickUsuario, @QueryParam("nombreEspectaculo") String nombreEspectaculo) {
        try {

            ICE.ActualizarFavorito(nickUsuario, nombreEspectaculo);

            String json = "{"
                    + "\"code\":" + "0,"
                    + "\"msg\":" + "\"Se actualizo correctamente\"}";

            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
    
}
