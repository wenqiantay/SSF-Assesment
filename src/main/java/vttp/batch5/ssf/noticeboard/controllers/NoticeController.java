package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers
@Controller
@RequestMapping
public class NoticeController {

    @Autowired
    NoticeService noticeSvc;

    @GetMapping("/notice")
    public String IndexController(Model model){
        
        Notice notice = new Notice();

        model.addAttribute("notice", notice);

        return "notice";
    }

    @PostMapping("/notice")
    public String postNotice(@Valid @ModelAttribute("notice")Notice notice, BindingResult bindings, Model model){

        if(bindings.hasErrors()){
            return "notice";
        }
        try {

            noticeSvc.postToNoticeServer(notice);
            String id = noticeSvc.getNoticeId();

            model.addAttribute("notice", notice);
            model.addAttribute("id", id);
    
            return "posted";
            

            
        } catch (Exception e) {
        
            String errormsg = e.getMessage();
            model.addAttribute("error", errormsg);
             
            return "error";
        
        }

        
    }
    
    @GetMapping(path = "/status")
    @ResponseBody
    public ResponseEntity<String> checkRedisHealth() {
        boolean isHealthy = noticeSvc.isRedisHealthy();
        if (isHealthy) {
            return ResponseEntity.ok("{}");
        } else {
            return ResponseEntity.status(503)
                    .body("{}");
        }
    }

}


