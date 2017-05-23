package com.sawyer.lucene;

import com.sawyer.lucene.util.LuceneQueryUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.junit.Test;

import java.io.IOException;

/**
 * LuceneQuickStartTest
 * Created by Sawyer on 2017/5/22.
 * Copyright © 2017年 Sawyer. All rights reserved.
 */
public class ExpertQueryTest {

    /*
    * 词条查询
    * 词条是搜索的最小单位 不可再分割 且值必须是字符串
    * */
    @Test
    public void termQueryTest() throws IOException {
        TermQuery termQuery = new TermQuery(new Term("title", "谷歌地图"));
        LuceneQueryUtils.queryByQuery(termQuery, 10);
    }

    /*
    * 通配符查询
    * ? - 任意一个字符
    * * - 任意多个字符
    * */
    @Test
    public void wildcardQuery() throws IOException {
        WildcardQuery query = new WildcardQuery(new Term("title", "*歌"));
        LuceneQueryUtils.queryByQuery(query, 10);
    }

    /*
    * 模糊查询
    * maxEdits: 最大编辑距离 一个单词到另一个单词最少修改次数 [0,2]
    * */
    @Test
    public void fuzzyQueryTest() throws IOException {
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("title", "facebool"), 1);
        LuceneQueryUtils.queryByQuery(fuzzyQuery, 10);
    }

    /*
    * 数值范围查询
    * id[2L,2L] 对非 String 类型的 ID 进行精确查找
    * */
    @Test
    public void numericRangeQueryTest() throws IOException {
        NumericRangeQuery<Long> numericRangeQuery = NumericRangeQuery.newLongRange("id", 2L, 2L, true, true);
        LuceneQueryUtils.queryByQuery(numericRangeQuery, 10);
    }

    /*
    * 组合查询
    * 交集: Occur.MUST + Occur.MUST
    * 并集: Occur.SHOULD + Occur.SHOULD
    * 补集: Occur.MUST_NOT
    * */
    @Test
    public void booleanQueryTest() throws IOException {
        NumericRangeQuery<Long> numericRangeQuery1 = NumericRangeQuery.newLongRange("id", 1L, 3L, true, true);
        NumericRangeQuery<Long> numericRangeQuery2 = NumericRangeQuery.newLongRange("id", 2L, 4L, true, true);
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(numericRangeQuery1, BooleanClause.Occur.MUST_NOT);
        booleanQuery.add(numericRangeQuery2, BooleanClause.Occur.SHOULD);
        LuceneQueryUtils.queryByQuery(booleanQuery, 10);
    }
}
