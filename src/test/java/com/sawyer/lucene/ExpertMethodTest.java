package com.sawyer.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * LuceneQuickStartTest
 * Created by Sawyer on 2017/5/23.
 * Copyright © 2017年 Sawyer. All rights reserved.
 */
public class ExpertMethodTest {

    private static final File INDEX_DIR_FILE = new File("/Users/zhangsiyao1/Desktop/indexDir");

    @Test
    public void highLightTest() throws IOException, ParseException, InvalidTokenOffsetsException {
        /* 目录对象 */
        FSDirectory directory = FSDirectory.open(INDEX_DIR_FILE);
        /* 读取工具 */
        DirectoryReader reader = DirectoryReader.open(directory);
        /* 搜索工具 */
        IndexSearcher searcher = new IndexSearcher(reader);

        /* parse 方式获得 Query 对象 */
        QueryParser queryParser = new QueryParser("title", new IKAnalyzer());
        Query query = queryParser.parse("谷歌地图");

        /* HTML 格式化器 */
        Formatter formatter = new SimpleHTMLFormatter("<em>", "</em>");
        QueryScorer queryScorer = new QueryScorer(query);
        /* 准备高亮工具 */
        Highlighter highlighter = new Highlighter(formatter, queryScorer);

        /* 搜索 */
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("TotalHits: " + topDocs.totalHits);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = reader.document(scoreDoc.doc);
            /*
            * 高亮工具处理普通查询结果
            * 参数一: 分词器
            * 参数二: 高亮字段名
            * 参数三: 高亮字段原始值
            * */
            String highLightTitle = highlighter.getBestFragment(new IKAnalyzer(), "title", document.get("title"));
            System.out.println(highLightTitle);
        }
    }

    @Test
    public void sortTest() {

    }
}
