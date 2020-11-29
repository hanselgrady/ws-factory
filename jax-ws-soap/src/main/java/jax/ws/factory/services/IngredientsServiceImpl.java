package jax.ws.factory.services;

import javax.jws.WebService;
import java.sql.*;
import org.json.simple.*;
import org.json.simple.parser.*;

@WebService(endpointInterface = "jax.ws.factory.services.IngredientsService")
public class IngredientsServiceImpl implements IngredientsService {
    @Override
    public void addNewIngredient(String name, int amount, String expire) {
        DatabaseConnector dbcon = new DatabaseConnector();
        String sql;
        ResultSet rs;
        int count = 0;
        int ingredientsid = 0;
        sql = "SELECT COUNT(*) FROM ingredients WHERE name = " + name + " AND expiredate = '" + expire +"'";
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            rs.next();
            count = rs.getInt("count(*)");
        }
        catch (SQLException err) {
            err.printStackTrace();
        }
        sql = "SELECT MAX(chocoid) FROM ingredients";
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            rs.next();
            ingredientsid = rs.getInt("max(ingredientsid)") + 1;
        }
        catch (SQLException err) {
            err.printStackTrace();
        }

        // same ingredients may have different ingredients date, so save to new tuples
        if (count == 0) {
            sql = "INSERT INTO request (ingredientsid, ingredientsname, ingredientsamount, expiredate) VALUES (" + ingredientsid + ", " + name + ", " + amount + ", '" + expire +"')";
            dbcon.updateDatabase(sql);
        }
        else {
            sql = "SELECT ingredientsamount FROM ingredients WHERE name = " + name + " AND expiredate = '" + expire +"'";
            int ingredientsamount = 0;
            dbcon.extractData(sql);
            rs = dbcon.getResult();
            try {
                rs.next();
                ingredientsamount = rs.getInt("ingredientsamount") + amount;
            }
            catch (SQLException err) {
                err.printStackTrace();
            }
            sql = "UPDATE request SET ingredientsamount = " + ingredientsamount + " WHERE name = " + name + " AND expiredate = '" + expire +"'";
        }
    }

    @Override
    public String listIngredients() {
        
        DatabaseConnector dbcon = new DatabaseConnector();
        String sql;
        ResultSet rs;

        JSONObject res = new JSONObject();
        res.put("status", "success");
        JSONArray items = new JSONArray();
        
        sql = "select * from ingredients;";
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            while(rs.next()) {
                JSONObject item = new JSONObject();
                item.put("id", rs.getInt("ingredientsid"));
                item.put("ingredient", rs.getString("ingredientsname"));
                item.put("amount", rs.getInt("ingredientsamount"));
                items.add(item);
            }
            res.put("ingredient", items);
        }
        catch (SQLException err) {
            err.printStackTrace();
            return "{\"status\": \"FAILED\"}";
        }
        return res.toJSONString();
        
    }
    @Override
    public void requestStock(int id, int amount) {
        
    }

    @Override
    public int getStock(int id) {
        
        DatabaseConnector dbcon = new DatabaseConnector();
        String sql;
        ResultSet rs;

        JSONObject res = new JSONObject();
        res.put("status", "success");
        JSONArray items = new JSONArray();
        
        sql = "select ingredientsamount from ingredients where ingredientsid = " + id + ";";;
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            rs.next(); 
            return rs.getInt("ingredientsamount"); 
        }
        catch (SQLException err) {
            err.printStackTrace();
            return -999;
        }
    }
}
