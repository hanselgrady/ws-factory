package jax.ws.factory.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface SaldoService {
    @WebMethod
    public String getSaldo();

    @WebMethod
    public String addSaldo(int amount);
}