/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Lab-DIS
 */
@Stateless
@Path("conversorTemp")
public class ConversorTemp {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ConversorTemp
     */
    public ConversorTemp() {
    }

    /**
     * PUT method for updating or creating an instance of ConversorTemp
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    /*Metodo de conversion*/
    @Path("conversionTemp")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String conversionTemp(@QueryParam("T") double T,@QueryParam("u") String u) {
        return Double.toString(TconversionTemp(T, u));
    }
    
    double TconversionTemp(double T, String u) {
        switch (u) {
            case "C":
                return (double)(T - 273.15);
            case "F":
                return (double) ((T - 273.15) * 9/5 + 32);
            default:
                return T;
        }
    }

}