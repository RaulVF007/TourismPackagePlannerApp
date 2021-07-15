/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

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
@Path("generic")
public class ConversorMoneda {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ConversorMoneda
     */
    public ConversorMoneda() {
    }

    /**
     * PUT method for updating or creating an instance of ConversorMoneda
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    /*Metodo de conversion*/
    @Path("conversorMoneda")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String conversionCoin(@QueryParam("M") double M,@QueryParam("c") String c) {
        //Money = amount = M
        //C = coin = dollar, pound or euro
        //Will come in dollars
        return Double.toString(MconversionCoin(M, c));
    }
    
    double MconversionCoin(double M, String c) {
        switch (c) {
            case "Euros":
                return (double)(M*0.83);
            case "Pounds":
                return (double) (M*0.72);
            default:
                return M;
        }
    }
}
