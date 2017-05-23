package com.sawyer.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * LuceneQuickStartTest
 * Created by Sawyer on 2017/5/23.
 * Copyright © 2017年 Sawyer. All rights reserved.
 */
public class UpdateIndexTest {

    private static final File INDEX_DIR_FILE = new File("/Users/zhangsiyao1/Desktop/indexDir");

    /*
    * 1.Lucene 底层先删除所有匹配的索引 再添加新文档
    * 2.一般修改功能会根据 Term 词条进行匹配
    * 3.根据一个唯一不重复字段进行匹配（ID）
    *
    * 问题: update 时 Term 词条搜索 要求 ID 必须是字符串 如果不是则不能使用这个方法
    * 解决: 先删除该词条 再添加更新后的词条
    * */
    @Test
    public void updateTest() throws IOException {
        /* 创建目录对象 */
        FSDirectory directory = FSDirectory.open(INDEX_DIR_FILE);
        /* 创建索引写出器配置对象 */
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        /* 创建索引写出器 */
        IndexWriter writer = new IndexWriter(directory, config);

        /* 创建更新后的文档对象 */
        Document document = new Document();
        document.add(new StringField("id", "1", Field.Store.YES));
        document.add(new TextField("title", "谷歌地图之父跳槽facebook为了加入传智播客", Field.Store.YES));
        /* 只能通过 Term 词条更新指定文档 */
        writer.updateDocument(new Term("id", "1"), document);

        writer.commit();
        writer.close();
    }

    /*
    * 删除索引
    * */
    @Test
    public void deleteTest() throws IOException {
        /* 创建目录对象 */
        FSDirectory directory = FSDirectory.open(INDEX_DIR_FILE);
        /* 创建索引写出器配置对象 */
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        /* 创建索引写出器 */
        IndexWriter writer = new IndexWriter(directory, config);

        /*
        * 1.根据词条 Term 进行删除 只能匹配 字符串类型 字段
        * */
        writer.deleteDocuments(new Term("id", "1"));

        /*
        * 2.根据 Query 删除 可以匹配任何类型的字段
        * */
        NumericRangeQuery<Long> numericRangeQuery = NumericRangeQuery.newLongRange("id", 2L, 2L, true, true);
        writer.deleteDocuments(numericRangeQuery);

        /* 3.删除所有 */
        writer.deleteAll();

        writer.commit();
        writer.close();
    }
}
