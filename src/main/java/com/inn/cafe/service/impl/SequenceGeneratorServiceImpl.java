package com.inn.cafe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.inn.cafe.entities.CustomSequence;
import com.inn.cafe.service.SequenceGeneratorService;

@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

	@Autowired
	private MongoOperations operations;
	
	@Override
	public int generateSequence(String seqName) {
		CustomSequence customSequence = operations.findAndModify(query(where("_id").is(seqName))
				, new Update().inc("seq", 1),
				options().returnNew(true).upsert(true),
				CustomSequence.class);
				
				return customSequence.getSeq();
				
	}

}
