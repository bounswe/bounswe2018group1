package com.cmpe451.retro.data.repositories.search;

import com.cmpe451.retro.models.GetMemoryResponseBody;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(name = "filterMapping",
        classes = {
                @ConstructorResult(targetClass = GetMemoryResponseBody.class,
                        columns = {
                                @ColumnResult(name = "id",type = Long.class),
                                @ColumnResult(name = "userId",type = Long.class),
                                @ColumnResult(name = "userNickName",type = Long.class),
                                @ColumnResult(name = "userFirstName",type = Long.class),
                                @ColumnResult(name = "userLastName",type = Long.class),
                                @ColumnResult(name = "userProfilePicUrl",type = Long.class),
                                @ColumnResult(name = "headline",type = Long.class),
                                @ColumnResult(name = "dateOfCreation",type = Long.class),
                                @ColumnResult(name = "listOfLocations",type = Long.class)
                        })
        })
public class FilterMemoryMapper {
}
