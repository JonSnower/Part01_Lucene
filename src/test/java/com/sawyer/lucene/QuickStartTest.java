package com.sawyer.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * LuceneQuickStartTest
 * Created by Sawyer on 2017/5/22.
 * Copyright © 2017年 Sawyer. All rights reserved.
 */
public class QuickStartTest {

    private static final File INDEX_DIR_FILE = new File("/Users/zhangsiyao1/Desktop/indexDir");

    @Test
    public void createTest() throws IOException {
        /* 1.创建文档对象 */
        Document document = new Document();
        /*
        * 添加索引字段
        *
        * StringField: Field.Store.YES 表示存储到文档列表
        * TestField: 既创建索引 又分词
        * */
        document.add(new StringField("id", "1", Field.Store.YES));
        document.add(new TextField("title", "张思尧是最帅的人，小麦克斯最爱尧尧了", Field.Store.YES));

        /*
        * 2.创建目录类 指定索引在硬盘中位置
        * */
        Directory directory = FSDirectory.open(INDEX_DIR_FILE);

        /*
        * 3.创建分词器对象 analyzer
        * */
        Analyzer analyzer = new IKAnalyzer();

        /*
        * 4.索引写出工具配置对象 config
        * */
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);

        /*
        * 5.创建索引写出工具类
        * */
        IndexWriter writer = new IndexWriter(directory, config);

        /*
        * 6.将文档添加到写出器工具类中
        * */
        writer.addDocument(document);

        /*
        * 7.提交 & 关闭 写出工具
        * */
        writer.commit();
        writer.close();
    }

    @Test
    public void prepareDataTest() throws IOException {
        Document document1 = new Document();
        document1.add(new StringField("id", "1", Field.Store.YES));
        document1.add(new TextField("title", "谷歌地图之父跳槽FaceBook", Field.Store.YES));

        Document document2 = new Document();
        document2.add(new StringField("id", "2", Field.Store.YES));
        document2.add(new TextField("title", "谷歌地图之父加盟FaceBook", Field.Store.YES));

        Document document3 = new Document();
        document3.add(new StringField("id", "3", Field.Store.YES));
        document3.add(new TextField("title", "谷歌地图创始人拉斯离开谷歌加盟Facebook", Field.Store.YES));

        Document document4 = new Document();
        document4.add(new StringField("id", "4", Field.Store.YES));
        document4.add(new TextField("title", "谷歌地图之父跳槽Facebook与Wave项目取消有关", Field.Store.YES));

        Document document5 = new Document();
        document5.add(new StringField("id", "5", Field.Store.YES));
        document5.add(new TextField("title", "谷歌地图之父拉斯加盟社交网站Facebook\n", Field.Store.YES));

        IndexWriter writer = new IndexWriter(FSDirectory.open(INDEX_DIR_FILE), new IndexWriterConfig(Version.LATEST, new IKAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));

        ArrayList<Document> documents = new ArrayList<>();
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);
        documents.add(document4);
        documents.add(document5);

        writer.addDocuments(documents);

        writer.commit();
        writer.close();
    }
}
