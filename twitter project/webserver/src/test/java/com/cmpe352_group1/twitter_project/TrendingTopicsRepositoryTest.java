package com.cmpe352_group1.twitter_project;

import com.cmpe352_group1.twitter_project.data.TrendingTopicsEntity;
import com.cmpe352_group1.twitter_project.data.TrendingTopicsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrendingTopicsRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrendingTopicsRepository trendingTopicsRepository;

@Test
    public void whengivendate_returntweet(){
        Date d = new Date(18,0,3);
        TrendingTopicsEntity tweet= new TrendingTopicsEntity("first",d);
        entityManager.persist(tweet);
        entityManager.flush();

        TrendingTopicsEntity found=trendingTopicsRepository.findByDate(tweet.getDate()).get(0);
        assertThat(tweet.getName().equals(found.getName()));
    }

}
