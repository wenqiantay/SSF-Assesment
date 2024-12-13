package vttp.batch5.ssf.noticeboard.services;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	@Value("${server.url}")
	private String SERVER_URL;

	@Autowired
	NoticeRepository noticeRepo;

	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public void postToNoticeServer(Notice notice) throws ParseException, HttpStatusCodeException{

		
		//Create JsonArray for List of Catergories
		List<String> categories = notice.getCategories();
	
    	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
   		
		for (int i = 0; i < categories.size(); i++) {
        	String category = categories.get(i);
        	arrayBuilder.add(category); 
    	}
    	JsonArray array = arrayBuilder.build(); 
								
							
		
		//Create JsonObject
		JsonObject json = Json.createObjectBuilder()
						.add("title", notice.getTitle())
						.add("poster", notice.getPoster())
						.add("postDate", notice.dateEpochmilliseconds())
						.add("categories", array)
						.add("text", notice.getText())
						.build();

		System.out.println(json.toString());

		RequestEntity<String> req = RequestEntity
									.post(SERVER_URL)
									.contentType(MediaType.APPLICATION_JSON)
									.accept(MediaType.APPLICATION_JSON)
									.body(json.toString(), String.class);


		RestTemplate template = new RestTemplate();
		try {

			ResponseEntity<String> resp = template.exchange(req, String.class);
			String payload = resp.getBody();

			noticeRepo.insertNotices(payload);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public String getNoticeId(){

		return noticeRepo.getId();
	}
}
