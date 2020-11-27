package jax.ws.factory;

import javax.xml.ws.Endpoint;
import jax.ws.factory.services.SaldoServiceImpl;
import jax.ws.factory.services.ChocolateServiceImpl;

public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        Endpoint.publish("http://localhost:9999/ws/saldo", new SaldoServiceImpl());
        Endpoint.publish("http://localhost:9999/ws/chocolate", new ChocolateServiceImpl());
    }
}
