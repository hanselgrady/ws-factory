package jax.ws.factory.services;

import javax.jws.WebService;
import java.sql.*;

@WebService(endpointInterface = "jax.ws.factory.services.SaldoService")
public class SaldoServiceImpl implements SaldoService {
    @Override
    public int getSaldo() {
        DatabaseConnector dbcon = new DatabaseConnector();
        String sql = "SELECT saldoamount FROM saldo where saldoid = 1";
        dbcon.extractData(sql);
        ResultSet rs = dbcon.getResult();
        try {
            rs.next();
            return rs.getInt("saldoamount");
        }
        catch (SQLException err) {
            err.printStackTrace();
            return -999;
        }
    }

    @Override
    public String addSaldo(int amount) {
        DatabaseConnector dbcon = new DatabaseConnector();
        String sql = "UPDATE saldo SET saldoamount = saldoamount + " + amount + " WHERE saldoid = 1";
        dbcon.updateDatabase(sql);
        return "SUCCESSFULLY UPDATED THE DATABASE";
    }
}