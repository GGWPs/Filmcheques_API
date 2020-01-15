package nl.han.ise.DAO;

import nl.han.ise.ConnectionFactory;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RapportageDAO {

    private ConnectionFactory connectionFactory;
    private Jedis jedis;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public RapportageDAO(){
        connectionFactory = new ConnectionFactory();
        jedis = new Jedis(connectionFactory.getRedisHost());
    }

    //Vraagt de opgevraagde rapportage op via REDIS
    public String getRapportage(String rapportage){
        String rapportageJson = jedis.get(rapportage);
        jedis.close();
        return rapportageJson;
    }

    //Leegt de Redis database en vult hem opnieuw in vanuit de SQL database.
    public void addAndUpdateAllRapportages(){
        logger.log(Level.INFO, "redis geupdated op " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        jedis.flushAll();
        List<String> lijstRapportages = lijstRapportagesSql();
        for(int i = 0; i <= lijstRapportages.size()-1; i++){
            addOrUpdateRapportage(lijstRapportages.get(i));
        }
        jedis.close();
    }

    //Functie om een rapportage toe te voegen of te wijzigen
    public void addOrUpdateRapportage(String rapportage){
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT (SELECT * FROM "+rapportage+" FOR JSON AUTO) AS Rapportage")) {
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getString("Rapportage") != null){
                    jedis.set(rapportage, resultSet.getString("Rapportage"));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL EXCEPTION BIJ OPHALEN VAN RAPPORTAGES", e);
            throw new RuntimeException(e);
        }
    }

    //Functie om een lijst van rapportages op te halen van Jedis.
    public List<String> lijstRapportagesRedis(){
        List<String> rapportageLijst = new ArrayList<>();
        Set<String> keys = jedis.keys("RAPPORTAGE_*");
        for (String key : keys) {
            rapportageLijst.add(key);
        }
        java.util.Collections.sort(rapportageLijst);
        jedis.close();
        return rapportageLijst;
    }

    //Functie om een lijst van rapportages op te halen van SQL.
    public List<String> lijstRapportagesSql() {
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
