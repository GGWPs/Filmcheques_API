package nl.han.ise.DAO;

import nl.han.ise.ConnectionFactory;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RapportageDAO {

    private ConnectionFactory connectionFactory;
    private Jedis jedis;
    private Logger logger = Logger.getLogger(getClass().getName());

    public RapportageDAO(){
        connectionFactory = new ConnectionFactory();
        jedis = new Jedis(connectionFactory.getRedisHost());
    }

    //Vraagt de opgevraagde rapportage op via REDIS
    public String getRapportage(String rapportage){
        return jedis.get(rapportage);
    }

    //Haalt een lijst op met rapportages en voegt/wijzigd ze toe aan REDIS
    public void addAndUpdateAllRapportages(){
        List<String> lijstRapportages = lijstRapportages();
        for(int i = 0; i <= lijstRapportages.size()-1; i++){
            addOrUpdateRapportage(lijstRapportages.get(i));
        }
    }

    //Functie om een rapportage toe te voegen of te wijzigen
    public void addOrUpdateRapportage(String rapportage){
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT (SELECT * FROM "+rapportage+" FOR JSON AUTO) AS Rapportage")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                jedis.set(rapportage, resultSet.getString("Rapportage"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL EXCEPTION BIJ OPHALEN VAN RAPPORTAGES", e);
            throw new RuntimeException(e);
        }
    }

    //Functie om een lijst van rapportages op te halen van SQL.
    public List<String> lijstRapportages() {
        List<String> rapportageLijst = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT name FROM sys.views WHERE name LIKE 'RAPPORTAGE_%'")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rapportageLijst.add(resultSet.getString("name"));
            }
            return rapportageLijst;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL EXCEPTION BIJ OPHALEN LIJST VAN RAPPORTAGES", e);
            throw new RuntimeException(e);
        }
    }


}
