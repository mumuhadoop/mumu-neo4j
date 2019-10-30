package com.lovecws.mumu.neo4j.driver;

import com.lovecws.mumu.neo4j.Neo4jConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.neo4j.driver.v1.*;

import java.util.*;
import java.util.function.Consumer;

/**
 * @program: mumu-neo4j
 * @description: neo4j驱动使用
 * @author: 甘亮
 * @create: 2019-10-29 18:50
 **/
public class Neo4jDriverNode {
    private static final Logger log = Logger.getLogger(Neo4jDriverNode.class);

    /**
     * 执行cql
     *
     * @param cql cql语句
     * @return
     */
    public List<Map<String, Object>> executionCQL(String cql) {
        Driver driver = Neo4jConfiguration.getDriver();
        Session session = driver.session();
        try {
            return executionCQL(cql, driver, session);
        } catch (Exception ex) {
            log.error(ex);
        } finally {
            driver.close();
            session.close();
        }
        return new ArrayList<>();
    }

    /**
     * 执行事务cql
     *
     * @param cql cql语句
     * @return
     */
    public List<Map<String, Object>> executionTransactionCQL(String cql) {
        Driver driver = Neo4jConfiguration.getDriver();
        Session session = driver.session();
        Transaction transaction = session.beginTransaction();
        try {
            List<Map<String, Object>> maps = executionCQL(cql, driver, transaction);
            transaction.success();
            return maps;
        } catch (Exception ex) {
            log.error(ex);
            transaction.rollbackAsync();
        } finally {
            transaction.close();
            driver.close();
            session.close();
        }
        return new ArrayList<>();
    }

    /**
     * 执行cql
     *
     * @param cql cql语句
     * @return
     */
    public List<Map<String, Object>> executionCQL(String cql, Driver driver, StatementRunner runner) {
        assert runner != null && cql != null;
        List<Map<String, Object>> results = new ArrayList<>();
        try {
            log.debug(cql);
            StatementResult statementResult = runner.run(cql);
            statementResult.stream().forEach(new Consumer<Record>() {
                @Override
                public void accept(Record record) {
                    results.add(record.asMap());
                }
            });
        } catch (Exception ex) {
            log.error(ex);
        }
        return results;
    }

    /**
     * 创建索引
     *
     * @param labelName     标签名称
     * @param attributeName 属性名称
     */
    public List<Map<String, Object>> createIndex(String labelName, String attributeName) {
        String cql = "CREATE INDEX ON :" + labelName + " (" + attributeName + ")";
        return executionCQL(cql);
    }


    /**
     * 删除索引
     *
     * @param labelName     标签名称
     * @param attributeName 属性名称
     */
    public List<Map<String, Object>> dropIndex(String labelName, String attributeName) {
        String cql = "DROP INDEX ON :" + labelName + " (" + attributeName + ")";
        return executionCQL(cql);
    }

    /**
     * 创建主键
     *
     * @param nodeName      节点名称
     * @param labelName     标签名称
     * @param attributeName 属性名称
     */
    public List<Map<String, Object>> createUniqueConstraint(String nodeName, String labelName, String attributeName) {
        String cql = "CREATE CONSTRAINT ON (" + nodeName + ":" + labelName + ") ASSERT " + nodeName + "." + attributeName + " IS UNIQUE";
        return executionCQL(cql);
    }

    /**
     * 删除主键
     *
     * @param nodeName      节点名称
     * @param labelName     标签名称
     * @param attributeName 属性名称
     */
    public List<Map<String, Object>> dropUniqueConstraint(String nodeName, String labelName, String attributeName) {
        String cql = "CREATE CONSTRAINT ON (" + nodeName + ":" + labelName + ") ASSERT " + nodeName + "." + attributeName + " IS UNIQUE";
        return executionCQL(cql);
    }

    /**
     * 创建节点或者merge节点
     *
     * @param actionName create or merge
     * @param nodeName   节点名称
     * @param nodeLabel  节点标签
     * @param profileMap 节点属性
     */
    public List<Map<String, Object>> createOrMergeNode(String actionName, String nodeName, String nodeLabel, Map<String, Object> profileMap) {
        assert Arrays.asList("create", "merge").contains(actionName) && nodeName != null && nodeLabel != null;
        if (profileMap == null) profileMap = new HashMap<>();

        List<String> nodeAttribute = new ArrayList<>();
        for (Map.Entry<String, Object> entry : profileMap.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                value = "'" + value.toString() + "'";
            }
            nodeAttribute.add(entry.getKey() + ":" + value);
        }
        String cql = actionName + " (" + nodeName + ":" + nodeLabel + " {" + StringUtils.join(nodeAttribute, ",") + "} )";
        log.debug(cql);
        return executionCQL(cql);
    }


    /**
     * 创建节点
     *
     * @param nodeName   节点名称
     * @param nodeLabel  节点标签
     * @param profileMap 节点属性
     */
    public List<Map<String, Object>> createNode(String nodeName, String nodeLabel, Map<String, Object> profileMap) {
        return createOrMergeNode("create", nodeName, nodeLabel, profileMap);
    }

    /**
     * merge节点
     *
     * @param nodeName   节点名称
     * @param nodeLabel  节点标签
     * @param profileMap 节点属性
     */
    public List<Map<String, Object>> mergeNode(String nodeName, String nodeLabel, Map<String, Object> profileMap) {
        return createOrMergeNode("merge", nodeName, nodeLabel, profileMap);
    }

    /**
     * 创建多节点,多节点使用事务来插入数据
     *
     * @param actionName create or merge
     * @param nodeName   节点名称
     * @param nodeLabel  节点标签
     * @param profiles   节点属性集合
     */
    public List<List<Map<String, Object>>> createOrMergeNodes(String actionName, String nodeName, String nodeLabel, List<Map<String, Object>> profiles) {
        assert nodeName != null && nodeLabel != null;
        if (profiles == null) profiles = new ArrayList<>();

        Driver driver = Neo4jConfiguration.getDriver();
        Session session = driver.session();
//        Transaction transaction = session.beginTransaction();
        List<List<Map<String, Object>>> results = new ArrayList<>();
        try {
            for (Map<String, Object> profileMap : profiles) {
                List<String> nodeAttribute = new ArrayList<>();
                for (Map.Entry<String, Object> entry : profileMap.entrySet()) {
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        value = "'" + value.toString() + "'";
                    }
                    nodeAttribute.add(entry.getKey() + ":" + value);
                }
                String cql = actionName + " (" + nodeName + ":" + nodeLabel + " {" + StringUtils.join(nodeAttribute, ",") + "} )";
                results.add(executionCQL(cql, driver, session));
            }
//            transaction.success();
            return results;
        } catch (Exception ex) {
            log.error(ex);
//            transaction.rollbackAsync();
        } finally {
//            transaction.close();
            session.close();
            driver.close();
        }
        return results;
    }

    /**
     * 创建多节点
     */
    public List<List<Map<String, Object>>> createNodes(String nodeName, String nodeLabel, List<Map<String, Object>> profiles) {
        return createOrMergeNodes("create", nodeName, nodeLabel, profiles);
    }

    /**
     * 合并多节点
     */
    public List<List<Map<String, Object>>> mergeNodes(String nodeName, String nodeLabel, List<Map<String, Object>> profiles) {
        return createOrMergeNodes("merge", nodeName, nodeLabel, profiles);
    }
}
