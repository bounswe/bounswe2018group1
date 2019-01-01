package com.cmpe451.retro.data.repositories.search;

import com.cmpe451.retro.data.entities.Memory;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.data.repositories.UserRepository;
import com.cmpe451.retro.models.FilterMemoryRequest;
import com.cmpe451.retro.models.GetMemoryResponseBody;
import com.cmpe451.retro.models.RetroException;
import com.cmpe451.retro.models.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemorySearchRepositoryImpl implements MemorySearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<GetMemoryResponseBody> findMemoriesWithFilter(FilterMemoryRequest filterMemoryRequest) {

        String query = "select distinct mem.* " +
                "from retro.memory as mem " +
                "       left join retro.memory_list_of_tags as memtag on mem.id = memtag.memory_id " +
                "       left join retro.tag as tag on memtag.list_of_tags_id = tag.id ";

        Date startDate = null;
        Date endDate = null;
        List<String> contraints = new ArrayList<>();
        if(Objects.nonNull(filterMemoryRequest.getStartDateYYYY())){
            startDate = createDateFromInts(filterMemoryRequest.getStartDateYYYY(), filterMemoryRequest.getStartDateMM(),
                    filterMemoryRequest.getStartDateDD(), filterMemoryRequest.getStartDateHH());
        }

        if(Objects.nonNull(filterMemoryRequest.getEndDateYYYY())){
            endDate = createDateFromInts(filterMemoryRequest.getStartDateYYYY(), filterMemoryRequest.getStartDateMM(),
                    filterMemoryRequest.getStartDateDD(), filterMemoryRequest.getStartDateHH());
        }

        String timeQuery = null;
        if(Objects.nonNull(startDate) && Objects.nonNull(endDate)){
             timeQuery = String.format("((mem.start_date >= %d and mem.start_date <= %d) or (mem.end_date >= %d and mem.end_date <= %d))"
                    , startDate.getTime(), endDate.getTime(), startDate.getTime(), endDate.getTime());
        }
        else if (Objects.nonNull(startDate)){
            timeQuery = String.format("(mem.start_date >= %d or mem.end_date >= %d)", startDate.getTime(),startDate.getTime());
        }
        else if (Objects.nonNull(endDate)){
            timeQuery = String.format("(mem.start_date <= %d or mem.end_date <= %d)", endDate.getTime(),endDate.getTime());
        }

        if(Objects.nonNull(timeQuery)){
            contraints.add(timeQuery);
        }

        if(Objects.nonNull(filterMemoryRequest.getListOfTags()) && !filterMemoryRequest.getListOfTags().isEmpty()){
            List<String> tags = filterMemoryRequest.getListOfTags().stream().map(TagDto::getText).collect(Collectors.toList());
            String tagList = "('" + String.join("','", tags) + "')";

            String tagQuery = String.format("tag.text in %s",tagList);
            contraints.add(tagQuery);
        }

        if(contraints.size()>0){
            String where = " where " + String.join(" and ", contraints);
            query = query + where;
        }

        List<Memory> memoryList = entityManager.createNativeQuery(query, Memory.class)
                .getResultList();

        List<GetMemoryResponseBody> memoryResponseBodyList = memoryList.stream().map(memory -> {
            Optional<User> userOptional = userRepository.findById(memory.getUserId());
            if (userOptional.isPresent()) {
                return new GetMemoryResponseBody(memory, userOptional.get());
            } else {
                return null;
            }

        }).collect(Collectors.toList());


        return memoryResponseBodyList;
    }

    private Date createDateFromInts(Integer year, Integer month, Integer day, Integer hour){
        if(!Objects.nonNull(year)){
            return null;
        }
        String yearString;
        String monthString = toDateString(month);
        String dayString = toDateString(day);
        String hourString = toDateString(hour);


        yearString = String.valueOf(year);
        while(yearString.length()<4){
            yearString = "0" + yearString;
        }

        String pattern = "yyyy-MM-dd-hh";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String parsableDate = yearString + "-" + monthString + "-" + dayString + "-" + hourString;

        try {
            return simpleDateFormat.parse(parsableDate);
        } catch (ParseException e) {
            throw new RetroException("Invalid start date", HttpStatus.BAD_REQUEST);
        }


    }

    private String toDateString(Integer intDate){
        String retDate;
        if(Objects.nonNull(intDate)){
            retDate = String.valueOf(intDate);
            while(retDate.length()<2){
                retDate = "0" + retDate;
            }
        }
        else{
            retDate = "00";
        }

        return retDate;
    }
}
