package com.lovecws.mumu.neo4j;

import org.neo4j.driver.v1.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

/**
 * neo4j配置文件信息
 */
public class Neo4jConfiguration {

    private static final String NEO4J_BOLT = "bolt://192.168.0.25:7687";
    private static final String NEO4J_USERNAME = "neo4j";
    private static final String NEO4J_PASSWORD = "lovecws";

    private static final String EMBEDDED_DBPATH = "E:/data/neo4j/db/data1/";

    /**
     * 获取图数据库的驱动
     *
     * @return
     */
    public static Driver getDriver() {
        return getDriver(NEO4J_BOLT, NEO4J_USERNAME, NEO4J_PASSWORD);
    }

    /**
     * 获取指定服务区的图数据库的驱动
     *
     * @return
     */
    public static Driver getDriver(String driverBolt, String userName, String password) {
        AuthToken authToken = AuthTokens.basic(userName, password);
        return GraphDatabase.driver(driverBolt, authToken, Config.defaultConfig());
    }

    /**
     * 获取内嵌的图数据库服务
     *
     * @return
     */
    public static GraphDatabaseService getGraphDatabaseService() {
        return getGraphDatabaseService(EMBEDDED_DBPATH);
    }

    /**
     * 获取指定内嵌文件路径的图数据库服务
     *
     * @param embeddedDbPath 内嵌服务数据库路径
     * @return
     */
    public static GraphDatabaseService getGraphDatabaseService(String embeddedDbPath) {
        GraphDatabaseFactory factory = new GraphDatabaseFactory();
        return factory.newEmbeddedDatabase(new File(embeddedDbPath));
    }
}
