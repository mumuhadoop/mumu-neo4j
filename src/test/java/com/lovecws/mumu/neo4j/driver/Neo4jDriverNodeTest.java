package com.lovecws.mumu.neo4j.driver;

import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: mumu-neo4j
 * @description: ${description}
 * @author: 甘亮
 * @create: 2019-10-29 19:50
 **/
public class Neo4jDriverNodeTest {

    private Neo4jDriverNode driverOperation = new Neo4jDriverNode();
    private static final Logger log = Logger.getLogger(Neo4jDriverNodeTest.class);

    @Test
    public void createNode() {
        Map<String, Object> map = new HashMap<>();
        driverOperation.createNode("person", "Person", map);
    }

    @Test
    public void createClassNodes() {
        List<Map<String, Object>> profiles = new ArrayList<>();
        profiles.add(ImmutableMap.of("id", 1, "name", "小班", "class_no", "101", "school", "惠佳新城幼儿园", "persons", 34));
        profiles.add(ImmutableMap.of("id", 2, "name", "中班", "class_no", "102", "school", "惠佳新城幼儿园", "persons", 54));
        profiles.add(ImmutableMap.of("id", 3, "name", "大班", "class_no", "103", "school", "惠佳新城幼儿园", "persons", 60));
        driverOperation.mergeNodes("MumuClass", profiles);
    }

    @Test
    public void createStudentNodes() {
        List<Map<String, Object>> profiles = new ArrayList<>();
        profiles.add(ImmutableMap.of("id", 1, "name", "甘子慕", "class_no", "101", "sex", "男", "height", 98));
        profiles.add(ImmutableMap.of("id", 2, "name", "甘子谦", "class_no", "101", "sex", "男", "height", 65));
        profiles.add(ImmutableMap.of("id", 3, "name", "甘子瑞", "class_no", "101", "sex", "男", "height", 120));
        profiles.add(ImmutableMap.of("id", 4, "name", "潘俊成", "class_no", "102", "sex", "男", "height", 120));
        profiles.add(ImmutableMap.of("id", 5, "name", "甘子龙", "class_no", "102", "sex", "男", "height", 120));
        profiles.add(ImmutableMap.of("id", 6, "name", "甘亮", "class_no", "103", "sex", "男", "height", 170));
        profiles.add(ImmutableMap.of("id", 7, "name", "陈伟生", "class_no", "103", "sex", "女", "height", 168));
        profiles.add(ImmutableMap.of("id", 8, "name", "陈大", "class_no", "103", "sex", "男", "height", 165));
        profiles.add(ImmutableMap.of("id", 9, "name", "陈二", "class_no", "103", "sex", "男", "height", 165));

        driverOperation.mergeNodes("MumuStudent", profiles);
    }


    @Test
    public void createGradeNodes() {
        List<Map<String, Object>> profiles = new ArrayList<>();
        profiles.add(ImmutableMap.of("id", 1, "name", "语文", "student_id", 1, "eval", "优异", "grade", 98));
        profiles.add(ImmutableMap.of("id", 2, "name", "数学", "student_id", 1, "eval", "一般", "grade", 68));
        profiles.add(ImmutableMap.of("id", 3, "name", "英语", "student_id", 1, "eval", "优异", "grade", 100));
        profiles.add(ImmutableMap.of("id", 4, "name", "政治", "student_id", 1, "eval", "优异", "grade", 88));

        profiles.add(ImmutableMap.of("id", 5, "name", "语文", "student_id", 2, "eval", "一般", "grade", 62));
        profiles.add(ImmutableMap.of("id", 6, "name", "数学", "student_id", 2, "eval", "一般", "grade", 68));
        profiles.add(ImmutableMap.of("id", 7, "name", "英语", "student_id", 2, "eval", "优异", "grade", 100));
        profiles.add(ImmutableMap.of("id", 8, "name", "政治", "student_id", 2, "eval", "优异", "grade", 88));

        profiles.add(ImmutableMap.of("id", 9, "name", "语文", "student_id", 3, "eval", "一般", "grade", 66));
        profiles.add(ImmutableMap.of("id", 10, "name", "数学", "student_id", 3, "eval", "一般", "grade", 78));
        profiles.add(ImmutableMap.of("id", 11, "name", "英语", "student_id", 3, "eval", "优异", "grade", 100));
        profiles.add(ImmutableMap.of("id", 12, "name", "政治", "student_id", 3, "eval", "优异", "grade", 99));

        profiles.add(ImmutableMap.of("id", 13, "name", "语文", "student_id", 4, "eval", "一般", "grade", 69));
        profiles.add(ImmutableMap.of("id", 14, "name", "数学", "student_id", 4, "eval", "一般", "grade", 75));
        profiles.add(ImmutableMap.of("id", 15, "name", "英语", "student_id", 4, "eval", "优异", "grade", 88));
        profiles.add(ImmutableMap.of("id", 16, "name", "政治", "student_id", 4, "eval", "优异", "grade", 90));

        profiles.add(ImmutableMap.of("id", 17, "name", "语文", "student_id", 5, "eval", "一般", "grade", 66));
        profiles.add(ImmutableMap.of("id", 18, "name", "数学", "student_id", 5, "eval", "一般", "grade", 78));
        profiles.add(ImmutableMap.of("id", 19, "name", "英语", "student_id", 5, "eval", "优异", "grade", 100));
        profiles.add(ImmutableMap.of("id", 20, "name", "政治", "student_id", 5, "eval", "优异", "grade", 99));

        profiles.add(ImmutableMap.of("id", 21, "name", "语文", "student_id", 6, "eval", "一般", "grade", 63));
        profiles.add(ImmutableMap.of("id", 22, "name", "数学", "student_id", 6, "eval", "一般", "grade", 68));
        profiles.add(ImmutableMap.of("id", 23, "name", "英语", "student_id", 6, "eval", "优异", "grade", 100));
        profiles.add(ImmutableMap.of("id", 24, "name", "政治", "student_id", 6, "eval", "优异", "grade", 88));

        profiles.add(ImmutableMap.of("id", 25, "name", "语文", "student_id", 7, "eval", "一般", "grade", 65));
        profiles.add(ImmutableMap.of("id", 26, "name", "数学", "student_id", 7, "eval", "一般", "grade", 69));
        profiles.add(ImmutableMap.of("id", 27, "name", "英语", "student_id", 7, "eval", "优异", "grade", 100));
        profiles.add(ImmutableMap.of("id", 28, "name", "政治", "student_id", 7, "eval", "优异", "grade", 95));

        profiles.add(ImmutableMap.of("id", 29, "name", "语文", "student_id", 8, "eval", "一般", "grade", 68));
        profiles.add(ImmutableMap.of("id", 30, "name", "数学", "student_id", 8, "eval", "一般", "grade", 68));
        profiles.add(ImmutableMap.of("id", 31, "name", "英语", "student_id", 8, "eval", "优异", "grade", 100));
        profiles.add(ImmutableMap.of("id", 32, "name", "政治", "student_id", 8, "eval", "优异", "grade", 88));

        profiles.add(ImmutableMap.of("id", 33, "name", "语文", "student_id", 9, "eval", "一般", "grade", 67));
        profiles.add(ImmutableMap.of("id", 34, "name", "数学", "student_id", 9, "eval", "一般", "grade", 68));
        profiles.add(ImmutableMap.of("id", 35, "name", "英语", "student_id", 9, "eval", "差", "grade", 55));
        profiles.add(ImmutableMap.of("id", 36, "name", "政治", "student_id", 9, "eval", "优异", "grade", 88));

        List<List<Map<String, Object>>> mergeNodes = driverOperation.mergeNodes("MumuGrade", profiles);
        log.info(mergeNodes);
    }

    @Test
    public void returnAllByReleationShip() {
        List<Map<String, Object>> maps = driverOperation.returnAllByReleationShip("MumuClassGradeShip");
        log.info(maps);
    }
}
