/*
 * Copyright (c) 2022. Innodox Technologies Zrt.
 * All rights reserved.
 */

package hu.perit.ngface.data;

import hu.perit.spvitamin.core.StackTracer;
import hu.perit.spvitamin.spring.json.JSonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
class DataRetrievalParamsTest
{

    @Test
    void getPageNumber()
    {
        DataRetrievalParams dataRetrievalParams = new DataRetrievalParams();
        dataRetrievalParams.setPage(new DataRetrievalParams.Page().index(1).size(10));
        dataRetrievalParams.setSort(new DataRetrievalParams.Sort());

        try
        {
            String json = new JSonSerializer().toJson(dataRetrievalParams);
            log.debug(json);
            DataRetrievalParams deserializedData = JSonSerializer.fromJson(json, DataRetrievalParams.class);
            log.debug(deserializedData.toString());
            assertTrue(dataRetrievalParams.equals(deserializedData));
        }
        catch (IOException e)
        {
            log.error(StackTracer.toString(e));
            fail();
        }

    }
}
