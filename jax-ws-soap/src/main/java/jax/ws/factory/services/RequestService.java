package jax.ws.factory.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface RequestService {
    /**
     * 
     * @param chocoID
     * @param amount
     * @param status
     * @return current STATUS = PENDING
     */
    @WebMethod
    public String addStock(int chocoID, int amount, String status);
    /**
     * 
     * @param chocoID
     * @return amount of chocolate which can be added to WWWeb database
     * Accessed from WWWeb
     */
    @WebMethod
    public int checkRequest(int chocoID);

    /**
     * 
     * @param requestID requestid that granted by WWFactory
     * @return 
     */
    @WebMethod
    public String acceptRequest(int requestID); // Accessed from WWFactory for granting the request delivery

}