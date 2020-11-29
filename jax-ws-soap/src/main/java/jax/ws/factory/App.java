package jax.ws.factory;

import javax.xml.ws.Endpoint;
import jax.ws.factory.services.SaldoServiceImpl;
import jax.ws.factory.services.ChocolateServiceImpl;
import jax.ws.factory.services.RequestServiceImpl;
import jax.ws.factory.services.IngredientsServiceImpl;

public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        Endpoint.publish("http://localhost:9999/ws/saldo", new SaldoServiceImpl());
        Endpoint.publish("http://localhost:9999/ws/chocolate", new ChocolateServiceImpl());
        Endpoint.publish("http://localhost:9999/ws/request", new RequestServiceImpl());

        Endpoint.publish("http://localhost:9999/ws/ingredients", new IngredientsServiceImpl());
    }
}
