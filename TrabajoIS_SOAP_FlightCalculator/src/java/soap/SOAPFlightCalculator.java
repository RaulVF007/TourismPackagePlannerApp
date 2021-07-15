/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Lab-DIS
 */
@WebService(serviceName = "SOAPFlightCalculator")
public class SOAPFlightCalculator {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "calculateFlightPrice")
    public double calculateFlightPrice(@WebParam(name = "childrenNumber") int childrenNumber, @WebParam(name = "adultNumber") int adultNumber, @WebParam(name = "flightPrice") double flightPrice) {
        //TODO write your implementation code here:
        return (adultNumber * flightPrice) + (childrenNumber * 0.5 * flightPrice);
    }
}
