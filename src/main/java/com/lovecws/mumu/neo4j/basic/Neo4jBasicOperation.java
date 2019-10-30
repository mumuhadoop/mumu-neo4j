package com.lovecws.mumu.neo4j.basic;

import com.alibaba.fastjson.JSON;
import com.lovecws.mumu.neo4j.Neo4jConfiguration;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * neo4j配置文件信息
 */
public class Neo4jBasicOperation {

    public static void main(String[] args) {
        GraphDatabaseService gds = Neo4jConfiguration.getGraphDatabaseService();
        Transaction tx = gds.beginTx();
        try {
            Node fromNode = gds.createNode();
            fromNode.setProperty("table", "person");
            fromNode.setProperty("name", "马云");

            Node toNode = gds.createNode();
            toNode.setProperty("table", "person");
            toNode.setProperty("name", "李彦宏");

            Relationship relationship = fromNode.createRelationshipTo(toNode, DynamicRelationshipType.withName("BELONG"));
            List<String> eventList = new ArrayList<String>();
            eventList.add("2013福布斯中国富豪榜:李彦宏第三、马化腾第五、马云第八 ");
            eventList.add("李彦宏推轻应用马云入股浏览器 移动入口争夺暗战升级 ");
            relationship.setProperty("event", JSON.toJSONString(eventList));

            Node companyNode = gds.createNode();
            companyNode.setProperty("table", "company");
            companyNode.setProperty("name", "阿里巴巴");
            Relationship belongRelationship = fromNode.createRelationshipTo(companyNode, DynamicRelationshipType.withName("BELONG"));
            belongRelationship.setProperty("event", "马云如何掌控阿里巴巴? ");

            tx.success();
        } catch (Throwable e) {
            e.printStackTrace();
            tx.failure();
        } finally {
            tx.close();
        }
        Transaction tx2 = gds.beginTx();
        Iterator<Node> iterator = gds.getAllNodes().iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            Iterator<String> keysIterator = node.getPropertyKeys().iterator();
            while (keysIterator.hasNext()) {
                String key = keysIterator.next();
                System.out.println(key + "->" + node.getProperty(key));
            }
            Iterator<Relationship> relationshipsIterator = node.getRelationships().iterator();
            while (relationshipsIterator.hasNext()) {
                Relationship relationships = relationshipsIterator.next();
                System.out.println("关系：" + relationships.getType());
            }
        }
        tx2.success();
        gds.shutdown();
    }
}
