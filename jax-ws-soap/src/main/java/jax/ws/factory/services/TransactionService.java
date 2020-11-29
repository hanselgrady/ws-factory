package jax.ws.factory.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface TransactionService {
    @WebMethod
    public void addChocolate(String nama, String[] bahan, int[] jumlah);
}
