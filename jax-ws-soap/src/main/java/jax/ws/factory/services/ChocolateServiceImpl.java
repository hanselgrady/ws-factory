package jax.ws.factory.services;

import javax.jws.WebService;
import java.sql.*;
import org.json.simple.*;
import org.json.simple.parser.*;

@WebService(endpointInterface = "jax.ws.factory.services.ChocolateService")
public class ChocolateServiceImpl implements ChocolateService {
    @Override
    public String addChocolate(String nama, String[] bahan, int[] jumlah) {
        // Asumsi :
        // <bahan[i]> yang dibutuhkan untuk membuat <nama> adalah <jumlah[i]>
        // bahan.length sama dengan jumlah.length
        int count = -1;
        int ingredientsid = 0;
        int chocoid = 0;
        String sql;
        ResultSet rs;
        DatabaseConnector dbcon = new DatabaseConnector();

        sql = "SELECT COUNT(*) FROM recipe";
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

        if (count == 0) {
            chocoid = 1;
        }
        else {
            sql = "SELECT MAX(chocoid) FROM recipe";
            dbcon.extractData(sql);
            rs = dbcon.getResult();
            try {
                rs.next();
                chocoid = rs.getInt("max(chocoid)") + 1;
            }
            catch (SQLException err) {
                err.printStackTrace();
                return "FAILED";
            }
        }

        sql = "INSERT INTO choco_stock (chocoid, choconame, amount) VALUES (" + chocoid + ", '" + nama + "', 0);";
        dbcon.updateDatabase(sql);

        for (int i = 0; i < bahan.length; i++) {
            // Cek bahan sudah ada atau belum
            sql = "SELECT COUNT(*) FROM (SELECT * FROM ingredients WHERE ingredientsname = '" + bahan[i] + "') AS data;";
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

            // atur data dalam basis data sesuai dengan keberadaan bahan
            if (count == 0) {
                sql = "SELECT COUNT(*) FROM ingredients";
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

                if (count == 0) {
                    ingredientsid = 1;
                }
                else {
                    sql = "SELECT MAX(ingredientsid) FROM ingredients";
                    dbcon.extractData(sql);
                    rs = dbcon.getResult();
                    try {
                        rs.next();
                        ingredientsid = rs.getInt("max(ingredientsid)") + 1;
                    }
                    catch (SQLException err) {
                        err.printStackTrace();
                        return "FAILED";
                    }
                }

                sql = "INSERT INTO ingredients (ingredientsid, ingredientsname, ingredientsamount) VALUES (" + ingredientsid + ", '" + bahan[i] + "', 0);";
                dbcon.updateDatabase(sql);    
            }
            else {
                sql = "SELECT ingredientsid FROM ingredients WHERE ingredientsname = '" + bahan[i] + "';";
                dbcon.extractData(sql);
                rs = dbcon.getResult();
                try {
                    rs.next();
                    ingredientsid = rs.getInt("ingredientsid");
                }
                catch (SQLException err) {
                    err.printStackTrace();
                    return "FAILED";
                }
            }
            
            // sql = "SELECT MAX(chocoid) FROM ingredients";
            // dbcon.extractData(sql);
            // try {
            //     rs.next();
            //     chocoid = rs.getInt("chocoid") + 1;
            // }
            // catch (SQLException err) {
            //     err.printStackTrace();
            //     System.exit(1);
            // }

            sql = "INSERT INTO recipe (chocoid, ingredientsid, ingredientsamount) VALUES (" + chocoid + ", " + ingredientsid + ", " + jumlah[i] + ");";
            dbcon.updateDatabase(sql);
        }
        System.out.println("SUCCESSFULLY ADDING NEW CHOCOLATE RECIPE TO DATABASE");
        return "SUCCESS";
    }

    @Override
    public String getChocolateRecipe(int chocoid) {
        
        DatabaseConnector dbcon = new DatabaseConnector();
        String sql;
        ResultSet rs;

        JSONObject res = new JSONObject();
        res.put("status", "success");
        JSONArray items = new JSONArray();
        
        sql = "select choconame from choco_stock where chocoid = " + chocoid + ";";
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            if(rs.next()) {
                res.put("name", rs.getString("choconame"));
            }
        }
        catch (SQLException err) {
            err.printStackTrace();
            return "{\"status\": \"FAILED\"}";
        }

        sql = "select recipe.ingredientsid, ingredientsname, recipe.ingredientsamount from ingredients join recipe on (recipe.ingredientsid = ingredients.ingredientsid) where chocoid =" +  chocoid + ";";
        dbcon.extractData(sql);
        rs = dbcon.getResult();
        try {
            while(rs.next()) {
                JSONObject item = new JSONObject();
                item.put("id", rs.getInt("recipe.ingredientsid"));
                item.put("ingredients", rs.getString("ingredientsname"));
                item.put("amount", rs.getInt("recipe.ingredientsamount"));
                items.add(item);
            }
            res.put("recipe", items);
        }
        catch (SQLException err) {
            err.printStackTrace();
            return "{\"status\": \"FAILED\"}";
        }
        return res.toJSONString();
        
    }
    
}
