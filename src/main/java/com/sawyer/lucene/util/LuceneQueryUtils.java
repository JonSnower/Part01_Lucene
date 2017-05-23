package com.sawyer.lucene.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * LuceneQuickStartTest
 * Created by Sawyer on 2017/5/22.
 * Copyright © 2017年 Sawyer. All rights reserved.
 */
public class LuceneQueryUtils {

    private static final File INDEX_DIR_FILE = new File("/Users/zhangsiyao1/Desktop/indexDir");

    public static void queryByQuery(Query query, int maxResult) throws IOException {
        /* 索引目录对象 */
        Directory directory = FSDirectory.open(INDEX_DIR_FILE);
        /* 索引读取工具 */
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        /* 索引搜索工具 */
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        /* 搜索数据 */
        TopDocs topDocs = indexSearcher.search(query, maxResult);

        int totalHits = topDocs.totalHits;
        System.out.println("本地搜索共查询到 " + totalHits + " 匹配记录");
        System.out.println("=======================================");
        /*
        * 得分文档数组
        * */
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            /* 文档编号 */
            int docID = scoreDoc.doc;
            /* 通过索引读取器 根据文档编号获取文档 */
            Document document = directoryReader.document(docID);
            System.out.println("DocID: " + document.get("id"));
            System.out.println("Title: " + document.get("title"));
            System.out.println("Score: " + scoreDoc.score);
            System.out.println("=======================================");
        }
    }
}
