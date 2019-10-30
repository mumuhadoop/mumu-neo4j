package com.lovecws.mumu.neo4j.cypher;

import com.lovecws.mumu.neo4j.Neo4jConfiguration;
import org.neo4j.cypher.internal.ExecutionEngine;
import org.neo4j.cypher.internal.javacompat.ExecutionResult;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

/**
 * @program: mumu-neo4j
 * @description: ${description}
 * @author: 甘亮
 * @create: 2019-10-29 18:45
 **/
public class Neo4jCypherOperation {

    public void createNode(){
        GraphDatabaseService gds = Neo4jConfiguration.getGraphDatabaseService();
//        ExecutionEngine execEngine = new ExecutionEngine(gds);
//        ExecutionEngine execEngine = new ExecutionEngine(graphDb);
//        ExecutionResult execResult = execEngine.execute("MATCH (java:JAVA) RETURN java");
//        String results = execResult.dumpToString();
//        System.out.println(results);
    }

}
