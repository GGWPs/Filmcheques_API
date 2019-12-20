package nl.han.ise.DAO;

import redis.clients.jedis.Jedis;

public class RapportageDAO {



    public String test(){
        Jedis jedis = new Jedis();
        jedis.set("test", "hahahaha");
        return jedis.get("test");
    }

    public String retrieve(){
        Jedis jedis = new Jedis();
        return jedis.get("test");
    }
}
