package com.tango.test;

import com.mongodb.MongoException;
import com.tango.nosql.config.Boot;
import org.bson.BsonNull;
import org.bson.Document;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.SumAggregator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @date 2022/7/23 15:30
 */
@SpringBootTest(classes = Boot.class)
public class MongoTest {
    @Resource
    MongoTemplate mongoTemplate;

    /**
     * 统计
     */
    @Test
    void count(){
        /**
         * [{
         *     $match: {
         *         home_id: {
         *             $gt: '581447'
         *         }
         *     }
         * }, {
         *     $count: 'home_id'
         * }]
         */
        MatchOperation match = new MatchOperation(Criteria.where("home_id").gt("581447"));
        CountOperation count = new CountOperation("home_id");
        Aggregation aggregation = Aggregation.newAggregation(Arrays.asList(match, count));
        AggregationResults<Map> dbz = mongoTemplate.aggregate(aggregation, "patient", Map.class);
        System.out.println(dbz.getMappedResults());
    }

    /**
     * sum
     *  https://www.freesion.com/article/54071366090/
     *
     *  [{
     *     $group: {
     *         _id: null,
     *         total: {
     *             $sum: {
     *                 $toDouble: '$hos_total_fee'
     *             }
     *         }
     *     }
     * }]
     *
     */
    @Test
    void sum(){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("home_id").gt("581447")),
                Aggregation.group().sum("hos_total_fee").as("s")//字符串统计出来都是0
//                ,new ProjectionOperation().andInclude("hos_total_fee").and("hos_total_fee")
        );
        AggregationResults<Map> dbz = mongoTemplate.aggregate(aggregation, "patient", Map.class);
//        List<Document> documents = Arrays.asList(new Document("$group",
//                new Document("_id",
//                        new BsonNull())
//                        .append("total",
//                                new Document("$sum",
//                                        new Document("$toDouble", "$hos_total_fee")))));
        System.out.println(dbz.getMappedResults());
    }


    /**
     * 关联查询
     */
    @Test
    void join(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("tang"));
        LookupOperation lookup = LookupOperation.newLookup()
                .from("tag")
                .localField("_id")
                .foreignField("_id")
                .as("id");
        Aggregation aggregation = Aggregation.newAggregation(lookup);
        AggregationResults<Map> dbz = mongoTemplate.aggregate(aggregation, "dbz", Map.class);
        System.out.println(dbz.getMappedResults());
    }

    /**
     * 字段查询
     */
    @Test
    void field(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("tang"));
        mongoTemplate.executeQuery(query, "dbz", document -> {
            System.out.println(document);
        });
    }

    /**
     * 查询
     */
    @Test
    void query(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("tang"));
        mongoTemplate.executeQuery(query, "dbz", document -> {
            System.out.println(document);
        });
    }

    /**
     * 修改
     */
    @Test
    void update(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("tang"));
        UpdateDefinition update = new Update().set("age", 99);
        mongoTemplate.updateMulti(query, update, "dbz");
    }

    /**
     * 删除
     */
    @Test
    void del(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("tang"));
        mongoTemplate.remove(query);
    }

    /**
     * 新增
     */
    @Test
    void insert(){
        mongoTemplate.insert("""
                {
                  	"1": "黄启荣",
                  	"2": "邹赵敏",
                  	"3": "曹耀宇",
                  	"4": "王芳",
                  	"5": "外一（甲状腺乳腺外科、普外科）",
                  	"6": "220209296",
                  	"7": "5****1195409132592",
                  	"12": "否",
                  	"13": "1954-09-13",
                  	"14": "男",
                  	"15": "55",
                  	"16": "160",
                  	"17": "2022-02-07 10:03:50.000000",
                  	"18": "2022-03-15 08:30:00.000000",
                  	"21": "城镇居民基本医疗保险",
                  	"22": "门诊",
                  	"23": 21.48,
                  	"24": "",
                  	"25": 36,
                  	"26": "非医嘱离院",
                  	"28": "",
                  	"30": "",
                  	"31": "",
                  	"32": "",
                  	"33": "",
                  	"34": "",
                  	"35": "",
                  	"36": "",
                  	"37": "",
                  	"38": "",
                  	"39": "",
                  	"40": "24419.33",
                  	"41": "16437.61",
                  	"42": "2421.00",
                  	"43": "0.00",
                  	"44": "648.00",
                  	"45": "5.00",
                  	"46": "0.00",
                  	"47": "2209.50",
                  	"48": "1130.00",
                  	"49": "0.00",
                  	"50": "1262.00",
                  	"51": "1242.00",
                  	"52": "0.00",
                  	"53": "0.00",
                  	"54": "0.00",
                  	"55": "0.00",
                  	"56": "0.00",
                  	"57": "10201.94",
                  	"58": "0.00",
                  	"59": "0.00",
                  	"60": "0.00",
                  	"61": "0.00",
                  	"62": "0.00",
                  	"63": "6300.00",
                  	"64": "0.00",
                  	"65": "0.00",
                  	"66": "17.82",
                  	"67": "224.07",
                  	"68": "0.00",
                  	"69": "0.00",
                  	"83": "",
                  	"84": "",
                  	"87": [
                  		"无法确定或无记录"
                  	],
                  	"88": "",
                  	"285": "",
                  	"301": "20",
                  	"302": "89",
                  	"303": "92",
                  	"304": "66",
                  	"339": "",
                  	"340": "",
                  	"341": "",
                  	"342": "",
                  	"343": "",
                  	"344": "",
                  	"375": "",
                  	"376": "",
                  	"377": "",
                  	"378": "",
                  	"379": "不实施",
                  	"380": "",
                  	"381": "",
                  	"382": "",
                  	"383": "",
                  	"386": "",
                  	"389": "",
                  	"390": "不实施",
                  	"391": [],
                  	"398": "不实施",
                  	"399": [],
                  	"400": "",
                  	"401": "",
                  	"402": "",
                  	"403": "",
                  	"404": "不实施",
                  	"405": "",
                  	"407": "",
                  	"408": "否",
                  	"409": [],
                  	"410": [
                  		"基础疾病"
                  	],
                  	"411": [],
                  	"412": [
                  		"恶性肿瘤"
                  	],
                  	"413": [],
                  	"414": [],
                  	"415": "",
                  	"416": "是",
                  	"417": [
                  		"无法确定或无记录"
                  	],
                  	"418": "否",
                  	"419": [],
                  	"420": [],
                  	"421": "",
                  	"422": [],
                  	"423": "",
                  	"424": "",
                  	"425": [],
                  	"428": "",
                  	"429": "",
                  	"430": [
                  		"其他"
                  	],
                  	"431": "",
                  	"432": "否",
                  	"433": [],
                  	"434": "",
                  	"435": "",
                  	"436": "",
                  	"437": "",
                  	"438": [
                  		"无法确定或无记录"
                  	],
                  	"439": "",
                  	"440": [
                  		"无法确定或无记录"
                  	],
                  	"441": "",
                  	"442": "",
                  	"443": "",
                  	"444": [
                  		"无法确定或无记录"
                  	],
                  	"445": "",
                  	"446": [],
                  	"447": "",
                  	"448": "",
                  	"449": "",
                  	"450": "",
                  	"451": "",
                  	"452": "",
                  	"453": "",
                  	"454": "",
                  	"725": "不实施",
                  	"727": "是",
                  	"783": "",
                  	"1195": "",
                  	"1233": "C00-C97 恶性肿瘤住院接受化疗、放疗等非手术治疗的患者",
                  	"1234": "C00-C97恶性肿瘤住院接受病损或组织手术切除治疗的患者",
                  	"1235": "",
                  	"1236": "",
                  	"1237": "",
                  	"1238": "",
                  	"1239": "",
                  	"1240": "",
                  	"1241": "",
                  	"1242": "",
                  	"1243": "",
                  	"1244": "",
                  	"1278": "",
                  	"1281": "",
                  	"1296": [
                  		"其他临床科室的其他中高风险患者"
                  	],
                  	"1298": "外一（甲状腺乳腺外科、普外科）",
                  	"1299": "C22.900",
                  	"1300": "C22.900",
                  	"1301": "",
                  	"1302": "",
                  	"1305": "否",
                  	"1311": "",
                  	"1313": "",
                  	"1318": "",
                  	"1326": "",
                  	"3983": "无法确定或无出院带药记录"
                  }
                ""","rel");
    }
}
