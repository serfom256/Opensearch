package com.opensearch.common.chain;

import com.opensearch.entity.LookupResult;
import com.opensearch.entity.Query;

import java.util.List;

public class GroupByBlock extends QueryChain{

    GroupByBlock(QueryChain nextBlock) {
        super(nextBlock);
    }

    @Override
    public List<LookupResult> evaluate(List<LookupResult> resultList, Query query) {
        return null;
    }
}
