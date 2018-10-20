package com.cmpe451.retro.data.repositories;

import com.cmpe451.retro.data.entities.Story;
import org.springframework.data.repository.CrudRepository;

public interface StoryRepository extends CrudRepository<Story,Long> {
}
