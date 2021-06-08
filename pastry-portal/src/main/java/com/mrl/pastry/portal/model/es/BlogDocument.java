package com.mrl.pastry.portal.model.es;

//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import java.io.Serializable;
//
///**
// * Blog index
// * 单机分片：1，副本：0
// * ES这里只作为全文检索、高亮工具，用来弥补MySQL全文检索的不足，所以非order by/检索字段可以排除
// *
// * @author MrL
// * @version 1.0
// * @date 2021/4/25
// */
//@Data
//@Document(indexName = "blog", replicas = 0)
//public class BlogDocument implements Serializable {
//
//    @Id
//    private Long id;
//
//    @Field(type = FieldType.Long)
//    private Long author;
//
//    @Field(type = FieldType.Integer)
//    private Integer type;
//
//    @Field(type = FieldType.Integer)
//    private Integer status;
//
//    @Field(analyzer = "ik_max_word", type = FieldType.Text)
//    private String title;
//
//    @Field(analyzer = "ik_max_word", type = FieldType.Text)
//    private String content;
//
//    @Field(analyzer = "ik_max_word", type = FieldType.Text)
//    private String summary;
//
//    @Field(type = FieldType.Integer)
//    private Integer priority;
//}
