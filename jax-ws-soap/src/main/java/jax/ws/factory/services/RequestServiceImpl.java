package jax.ws.factory.services;

import javax.jws.WebService;
import java.sql.*;

@WebService(endpointInterface = "jax.ws.factory.services.RequestService")
public class RequestServiceImpl implements RequestService {
    @Override
    public String addStock(int chocoID, int amount, String status) {
        // Asumsi :
        // <bahan[i]> yang dibutuhkan untuk membuat <nama> adalah <jumlah[i]>
        // bahan.length sama dengan jumlah.length
        int count = 0;
        int requestid;
        String sql;
        ResultSet rs;
        DatabaseConnector dbcon = new DatabaseConnector();
        sql = "SELECT COUNT(*) FROM request";
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            rs.next();
            count = rs.getInt("count(*)") + 1;
        }
        catch (SQLException err) {
            err.printStackTrace();
            return "FAILED";
        }
        sql = "SELECT COUNT(*) FROM request";
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            rs.next();
            count = rs.getInt("count(*)");
        }
        catch (SQLException err) {
            err.printStackTrace();
            return "FAILED";
        }

        if (count == 0) {
            requestid = 1;
        }
        else {
            sql = "SELECT MAX(requestid) FROM request";
            dbcon.extractData(sql);
            rs = dbcon.getResult();
            try {
                rs.next();
                requestid = rs.getInt("max(requestid)") + 1;
            }
            catch (SQLException err) {
                err.printStackTrace();
                return "FAILED";
            }
        }

        sql = "INSERT INTO request (requestid, chocoid, amount) VALUES (" + requestid + ", " + chocoID + ", 'PENDING');";
        dbcon.updateDatabase(sql);

        
        System.out.println("SUCCESSFULLY ADDING NEW REQUEST TO DATABASE");
        return "PENDING";
    }
}