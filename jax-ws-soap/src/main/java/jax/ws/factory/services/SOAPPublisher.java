package jax.ws.factory.services;

import javax.xml.ws.Endpoint;

public class SOAPPublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/ws/saldo", new SaldoServiceImpl());
        Endpoint.publish("http://localhost:8888/ws/chocolate", new ChocolateServiceImpl());
        Endpoint.publish("http://localhost:8888/ws/request", new RequestServiceImpl());

    }
}