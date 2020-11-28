package jax.ws.factory.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface IngredientsService {
    @WebMethod
    public void addNewIngredient(String name, int amount);

    @WebMethod
    public void addStock(int id, int amount);
    
    @WebMethod
    public int getStock(int id);
    
}
