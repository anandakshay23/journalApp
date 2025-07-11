package com.anandakshay.journalApp.controller;


import com.anandakshay.journalApp.entity.JournalEntry;
import com.anandakshay.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAllEntries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<JournalEntry> all = journalEntryService.getJournalEntries(username);
        if (null!=all && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.saveEntry(journalEntry,username);
        return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable("myId") ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        JournalEntry entity  = journalEntryService.getJournalEntries(username).
                stream().filter(e->e.getId().equals(myId)).findFirst().orElse(null);
        if(null!=entity){
            return new ResponseEntity<>(entity, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable("myId") ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.removeJournalEntryById(username,myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@RequestBody JournalEntry newData,@PathVariable("myId") ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        JournalEntry oldData =  journalEntryService.getJournalEntries(username).stream().filter(e->e.getId().equals(myId)).findFirst().orElse(null);
        if(null!=oldData){
            oldData.setTitle(null!=newData.getTitle() && !newData.getTitle().equals("") ? newData.getTitle():oldData.getTitle());
            oldData.setContent(null!=newData.getContent() && !newData.getContent().equals("") ? newData.getContent():oldData.getContent());
            journalEntryService.saveEntry(oldData, username);
            return new ResponseEntity<>(oldData,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
