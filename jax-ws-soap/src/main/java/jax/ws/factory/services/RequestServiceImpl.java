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

    @Override public int checkRequest(int chocoID) {
        int result = 0;
        String sql;
        ResultSet rs;
        DatabaseConnector dbcon = new DatabaseConnector();

        // Get amount of total chocolate accepted request 
        sql = "SELECT SUM(amount) FROM (SELECT amount FROM request WHERE chocoid = " + chocoID + " AND status = 'DELIVERED') AS delivered_item";
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            rs.next();
            result = rs.getInt("sum(amount)");
        }
        catch (SQLException err) {
            err.printStackTrace();
            return result;
        }

        // Remove accepted request after processed
        sql = "DELETE FROM request WHERE chocoid = " + chocoID + " AND status = 'DELIVERED'";    
        dbcon.updateDatabase(sql);
        return result;
    }

    @Override public void acceptRequest(int requestID) {
        String sql;
        ResultSet rs;
        DatabaseConnector dbcon = new DatabaseConnector();
        // Make chocolate
        // ...
        
        // Get amount of total chocolate accepted request 
        sql = "UPDATE request SET status = 'DELIVERED'  WHERE requestid = " + requestID +" AND status = 'PENDING'";
        dbcon.extractData(sql);
    }

    @Override
    public String listRequest() {
        
        DatabaseConnector dbcon = new DatabaseConnector();
        String sql;
        ResultSet rs;

        JSONObject res = new JSONObject();
        res.put("status", "success");
        JSONArray items = new JSONArray();
        
        sql = "select * from request;";
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            while(rs.next()) {
                JSONObject item = new JSONObject();
                item.put("requestid", rs.getInt("requestid"));
                item.put("chocoid", rs.getString("chocoid"));
                item.put("amount", rs.getInt("amount"));
                item.put("status", rs.getInt("status"));
                items.add(item);
            }
            res.put("request", items);
        }
        catch (SQLException err) {
            err.printStackTrace();
            return "{\"status\": \"FAILED\"}";
        }
        return res.toJSONString();
        
    }
}