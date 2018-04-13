package com.cmpe352_group1.twitter_project;

import com.cmpe352_group1.twitter_project.data.TrendingTopicEntity;
import com.cmpe352_group1.twitter_project.data.TrendingTopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class TrendingTopicRepositoryTests {

    @Autowired
    private TrendingTopicRepository trendingTopicRepository;
    @Autowired
    private MongoOperations mongoOps;

    @Before
    public void reset() {
    }

    //------------------------------------------------- delete by id

    @Test
    public void testDeleteUsingId() {

        trendingTopicRepository.deleteById(3L);

        assertThat(mongoOps.findById(3L, TrendingTopicEntity.class), is(equalTo(null)));
    }

    //------------------------------------------------- exists

    @Test
    public void testExists() {
        assertThat(trendingTopicRepository.existsById(22L), is(equalTo(false)));
    }

    //------------------------------------------------- save

    @Test
    public void testSave() {
        TrendingTopicEntity another = new TrendingTopicEntity("Another", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        another.setId(10L);

        TrendingTopicEntity continentInserted = trendingTopicRepository.save(another);

        assertThat(continentInserted.getName(), is(equalTo("Another")));
        assertThat(mongoOps.findById(10L, TrendingTopicEntity.class).getName(), is(equalTo("Another")));
    }

}