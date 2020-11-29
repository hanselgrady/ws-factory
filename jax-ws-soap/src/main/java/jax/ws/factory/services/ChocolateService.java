package jax.ws.factory.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface ChocolateService {
    @WebMethod
    public String addChocolate(String nama, String[] bahan, int[] jumlah);
    
    @WebMethod
    public String getChocolateRecipe(int chocoid);
}
