package jax.ws.factory.services;

import javax.jws.WebService;
import java.sql.*;
import org.json.simple.*;
import org.json.simple.parser.*;

@WebService(endpointInterface = "jax.ws.factory.services.IngredientsService")
public class IngredientsServiceImpl implements IngredientsService {
    @Override
    public void addNewIngredient(String name, int amount) {
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
