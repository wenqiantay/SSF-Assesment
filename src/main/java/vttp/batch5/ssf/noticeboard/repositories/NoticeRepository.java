package vttp.batch5.ssf.noticeboard.repositories;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class NoticeRepository {

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */

	 @Autowired @Qualifier("notice")
	 RedisTemplate<String, Object> redisTemplate;


	//set  id 12343
	//set timestamp 12314344
	public void insertNotices(String payload) {

		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject response = reader.readObject();

		String time = String.valueOf(response.getInt("timestamp"));

		ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
		valueOps.set("id", response.getString("id"));
		valueOps.set("timestamp", time);

	}

	//get id
	public String getId(){

		String id = redisTemplate.opsForValue().get("id") .toString();

		return id;

	}
}
