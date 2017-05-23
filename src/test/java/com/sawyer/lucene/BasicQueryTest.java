package com.sawyer.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * LuceneQuickStartTest
 * Created by Sawyer on 2017/5/22.
 * Copyright © 2017年 Sawyer. All rights reserved.
 */
public class BasicQueryTest {

    private static final File INDEX_DIR_FILE = new File("/Users/zhangsiyao1/Desktop/indexDir");

    @Test
    public void baseSearchTest() throws IOException, ParseException {
        /* 索引目录对象 */
        Directory directory = FSDirectory.open(INDEX_DIR_FILE);
        /* 索引读取工具 */
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        /* 索引搜索工具 */
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        /*
        * 创建查询解析器
        * 1.查询字段名称
        * 2.分词解析器
        * */
        QueryParser queryParser = new QueryParser("title", new IKAnalyzer());
        /* 获取查询对象 */
        Query query = queryParser.parse("谷歌地图之父拉斯");

        /*
        * 搜索数据
        * 1.查询解析器解析后的查询结果
        * 2.查询结果的最大条数
        * */
        TopDocs topDocs = indexSearcher.search(query, 10);

        /* 获取总条数 */
        int totalHits = topDocs.totalHits;
        System.out.println("本地搜索共查询到 " + totalHits + " 匹配记录");

        /*
        * 得分文档数组
        * 1.doc 文档编号（ID）
        * 2.score 文档得分数
        * */
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            /* 文档编号 */
            int docID = scoreDoc.doc;
            /* 通过索引读取器 根据文档编号获取文档 */
            Document document = directoryReader.document(docID);

            System.out.println("DocID: " + document.get("id"));
            System.out.println("Title: " + document.get("title"));

            /* 文档得分 */
            System.out.println("Score: " + scoreDoc.score);
        }
    }
}
