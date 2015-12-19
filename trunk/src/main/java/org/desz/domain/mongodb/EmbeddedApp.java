/*
 * package org.desz.domain.mongodb;
 * 
 * import java.util.List;
 * 
 * import org.springframework.context.ApplicationContext; import
 * org.springframework.context.annotation.AnnotationConfigApplicationContext;
 * import org.springframework.data.mongodb.core.MongoOperations; import
 * org.springframework.data.mongodb.core.query.Criteria; import
 * org.springframework.data.mongodb.core.query.Query; import
 * org.springframework.data.mongodb.core.query.Update;
 * 
 * import org.desz.inttoword.config.repo.EmbeddedConfig;
 * 
 * public class EmbeddedApp {
 * 
 * public static void main(String[] args) {
 * 
 * // For XML // ApplicationContext ctx = new //
 * GenericXmlApplicationContext("SpringConfig.xml");
 * 
 * // For Annotation ApplicationContext ctx = new
 * AnnotationConfigApplicationContext(EmbeddedConfig.class); MongoOperations
 * mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
 * 
 * NumberFrequency freq = new NumberFrequency("1", 1);
 * 
 * // save mongoOperation.save(freq);
 * 
 * // now user object got the created id. System.out.println("1. freq : " +
 * freq);
 * 
 * // query to search user Query searchUserQuery = new
 * Query(Criteria.where("number").is("1"));
 * 
 * // find the saved user again. NumberFrequency sveFrq =
 * mongoOperation.findOne(searchUserQuery, NumberFrequency.class);
 * System.out.println("2. find - sveFrq : " + sveFrq);
 * 
 * // update inc. count mongoOperation.updateFirst(searchUserQuery,
 * Update.update("count", sveFrq.getCount() + 1), NumberFrequency.class);
 * 
 * // find the updated user object NumberFrequency updFrq =
 * mongoOperation.findOne(new Query(Criteria.where("number").is("1")),
 * NumberFrequency.class);
 * 
 * System.out.println("3. updFrq : " + updFrq);
 * 
 * // delete mongoOperation.remove(searchUserQuery, NumberFrequency.class);
 * 
 * // List, it should be empty now. List<NumberFrequency> listFrq =
 * mongoOperation.findAll(NumberFrequency.class); System.out.println(
 * "4. Number of user = " + listFrq.size());
 * 
 * }
 * 
 * }
 */