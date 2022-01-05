package be.kuleuven.pccustomizer.controller;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    //functie om te testen
    public void voorbeeldFunctie(){
        List<String> names = jdbi.withHandle(handle ->
                handle.createQuery("select Name, CPU, Cooling, Extra from Computer")
                        .mapTo(String.class)
                        .list());
        //boolean x = names.contains("high end");
        //System.out.println(names);
        System.out.println("het bevat: " + x);
    }

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
}
