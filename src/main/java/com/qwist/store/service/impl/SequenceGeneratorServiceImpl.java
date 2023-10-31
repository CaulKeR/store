package com.qwist.store.service.impl;

import com.qwist.store.model.DatabaseSequence;
import com.qwist.store.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/***
 * @author - Kiryl Karpuk
 */
@Service
@RequiredArgsConstructor
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    public Long generateSequence(String seqName) {
        DatabaseSequence counter =
                mongoOperations
                        .findAndModify(
                                query(where("_id").is(seqName)),
                                new Update().inc("seq", 1),
                                options().returnNew(true).upsert(true),
                                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1L;
    }

}
