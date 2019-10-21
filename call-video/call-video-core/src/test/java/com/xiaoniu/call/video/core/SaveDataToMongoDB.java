package com.xiaoniu.call.video.core;

import com.alibaba.druid.support.json.JSONUtils;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.call.video.core.entity.Audio;
import com.xiaoniu.call.video.core.service.AudioService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :LiuYinkai
 * @date :2019-07-26 20:42.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})

public class SaveDataToMongoDB {

    @Autowired
    private AudioService audioService;

    @Test
    public void testSave(){
        List<String> tags = new ArrayList<>();
        tags.add("AT001");
        tags.add("AT002");

        Audio audio = new Audio();
        audio.setAudioNumber("test00001");
        audio.setTitle("慕容铁筐的眼泪1");
        audio.setCategoryNumber("test002");
        audio.setAudioType(1);
        audio.setSinger("慕容铁筐");
        audio.setAudioCover("zuilaidian/img/bld/2019-07-03/cover/d78862aa397cc62f8c3190cf7a58ab7d.jpg");
        audio.setAudioAddress("zuilaidian/video/2019-07-03/bld/8c1589f99263434d02970d4a2ca65a87.mp4");
        audio.setAudioSource(1);
        audio.setChannelListenNumber(6000000);
        audio.setRealListenNumber(60);
        audio.setVirtualListenNumber(4000);
        audio.setSetRingToneNumber(30);
        audio.setTags(tags);
        audio.setWeight(0);
        audio.setStatus(true);
        audio.setReleaseTime(1562728349813L);
        audio.setOperator("大驴");
        audio.setRemark("NB");
        audio.setUpdateTime(1562728349813L);
        audio.setCreateTime(1562728349813L);
        MongodbRepository.save(audio);
    }

    @Test
    public void testQuery(){
        Aggregation aggregation = Aggregation.newAggregation(
                // 第一步：挑选所需的字段，类似select *，*所代表的字段内容
                Aggregation.project("*"),
                // 第二步：sql where 语句筛选符合条件的记录
                Aggregation.match(Criteria.where("status").is(true)),
                // 第三步：分组条件，设置分组字段
                Aggregation.group("audioNumber").sum("audioNumber").as("count"));
        AggregationResults<JSONObject> result = MongodbRepository.getMongoOperations().aggregate(aggregation, "audio", JSONObject.class);
        System.out.println(JSONUtils.toJSONString(result));
    }
}
