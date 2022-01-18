package be.kuleuven.pccustomizer.controller;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

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

}
