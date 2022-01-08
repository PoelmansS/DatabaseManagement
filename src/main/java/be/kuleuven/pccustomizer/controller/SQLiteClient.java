package be.kuleuven.pccustomizer.controller;

import be.kuleuven.pccustomizer.controller.Objects.Extra;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SQLiteClient {
    private static final String ConnectionString = "jdbc:sqlite:SanderPoelmans_DamianusWakker.db";
    private Jdbi jdbi;

    public SQLiteClient(){
        jdbi = Jdbi.create(ConnectionString);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    public Jdbi getJdbi() {
        return jdbi;
    }
    /*
    //functie om te testen
    public void voorbeeldFunctieVullen(){
        List<Extra> extras = new ArrayList<Extra>();
        List<String> names = jdbi.withHandle(handle ->
                handle.createQuery("SELECT Name FROM Cooling")
                        .mapTo(String.class)
                        .list());
        List<String> types = jdbi.withHandle(handle ->
                handle.createQuery("SELECT Type FROM Cooling")
                        .mapTo(String.class)
                        .list());
        List<Integer> prices = jdbi.withHandle(handle ->
                handle.createQuery("SELECT Price FROM Cooling")
                        .mapTo(Integer.class)
                        .list());
        for(int i = 0; i < names.size(); i++){
            extras.add(new Extra(names.get(i), types.get(i), prices.get(i)));
        }

        //boolean x = names.contains("high end");
        System.out.println(names);
        System.out.println(types);
        System.out.println(prices);
        System.out.println(extras.get(1).getPrice());
        //System.out.println("het bevat: " + x);
    }

     */

    /*

    //simpelen functie om een volledige kollom op te vragen van Strings
    public void getCollomS(String collom, String tabel, List<String> col){
        col = jdbi.withHandle(handle ->
                handle.createQuery("select collom from tabel")
                        .mapTo(String.class)
                        .list());
    }

    //simpelen functie om een volledige kollom op te vragen van Integers
    public void getCollomInt(String collom, String tabel, List<Integer> col){
        String s = "select " + collom +  " from " + tabel;
        col = jdbi.withHandle(handle ->
                handle.createQuery(s)
                        .mapTo(Integer.class)
                        .list());
    }

    public void voegToeVoorbeeldFunctie(String name, String type, Integer price){
        jdbi.useHandle(handle -> {
            handle.execute("insert into Extra (Name, Type, Price) values (?, ?, ?)", name, type, price);
        });
    }

    public void correctVoorbeeldFunctie(String type , Integer price, String name){
        jdbi.useHandle(handle -> {
            handle.execute("UPDATE Extra SET Type = ?, Price = ? WHERE Name = ?", type, price, name);
        });
    }

    public void deleteVoorbeeldDFunctie(String name){
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM Extra WHERE Name = ?", name);
        });
    }

    public Integer readAndCalculateDBint(String clas, String columnName, String item){
        Integer i = jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + columnName + " FROM " + clas + " WHERE Name = :Name")
                        .bind("Name", item)
                        .mapTo(Integer.class)
                        .one());
        System.out.println(i);
        return i;
    }

    public Integer readAndCalculateDBintV(String name){
        Integer i = jdbi.withHandle(handle ->
                handle.createQuery("SELECT Price FROM CPU WHERE Name = :Name")
                        .bind("Name", name)
                        .mapTo(Integer.class)
                        .one());
        System.out.println(i);
        return i;
    }


     */
}
