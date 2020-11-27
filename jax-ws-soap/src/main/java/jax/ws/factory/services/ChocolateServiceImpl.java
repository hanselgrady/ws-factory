package jax.ws.factory.services;

import javax.jws.WebService;
import java.sql.*;

@WebService(endpointInterface = "jax.ws.factory.services.SaldoService")
public class ChocolateServiceImpl implements ChocolateService {
    @Override
    public void addChocolate(String nama, String[] bahan, int[] jumlah) {
        // Asumsi :
        // <bahan[i]> yang dibutuhkan untuk membuat <nama> adalah <jumlah[i]>
        // bahan.length sama dengan jumlah.length
        int count = -1;
        int ingredientsid = 0;
        int chocoid = 0;
        String sql;
        ResultSet rs;
        DatabaseConnector dbcon = new DatabaseConnector();
        for (int i = 0; i < bahan.length; i++) {
            // Cek bahan sudah ada atau belum
            sql = "SELECT COUNT(*) AS DATA FROM (SELECT * FROM ingredients WHERE ingredientsname = '" + bahan[i] + "');"
            dbcon.extractData(sql);
            rs = dbcon.getResult();
            try {
                rs.next();
                count = rs.getInt("data");
            }
            catch (SQLException err) {
                err.printStackTrace();
                System.exit(1);
            }
            // atur data dalam basis data sesuai dengan keberadaan bahan
            if (count == 0) {
                sql = "SELECT MAX(ingredientsid) FROM ingredients";
                dbcon.extractData(sql);
                try {
                    rs.next();
                    ingredientsid = rs.getInt("ingredientsid") + 1;
                }
                catch (SQLException err) {
                    err.printStackTrace();
                    System.exit(1);
                }
                sql = "INSERT INTO ingredients (ingredientsid, ingredientsname, ingredientsamount) VALUES (" + ingredientsid + ", '" + bahan[i] + "', 0);";
                dbcon.updateDatabase(sql);
            }
            else {
                sql = "SELECT ingredientsid FROM ingredients WHERE ingredientsname = '" + bahan[i] + "';";
                dbcon.extractData(sql);
                try {
                    rs.next();
                    ingredientsid = rs.getInt("ingredientsid");
                }
                catch (SQLException err) {
                    err.printStackTrace();
                    System.exit(1);
                }
            }
            sql = "SELECT MAX(chocoid) FROM ingredients";
            dbcon.extractData(sql);
            try {
                rs.next();
                chocoid = rs.getInt("chocoid") + 1;
            }
            catch (SQLException err) {
                err.printStackTrace();
                System.exit(1);
            }
            sql = "INSERT INTO recipe (chocoid, ingredientsid, ingredientsamount) VALUES (" + chocoid + ", " + ingredientsid + ", " + jumlah[i] ")";
            dbcon.updateDatabase(sql);
            System.out.println("SUCCESSFULLY ADDING NEW CHOCOLATE RECIPE TO DATABASE");
        }
    }
}