package com.anandakshay.journalApp.service;

import com.anandakshay.journalApp.entity.JournalEntry;
import com.anandakshay.journalApp.entity.User;
import com.anandakshay.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        userService.saveNewJournalEntry(saved,username);
    }

    public List<JournalEntry> getJournalEntries(String username) {
        User user = userService.getUserByUsername(username);
        if(null!=user){
            return user.getJournalEntries();
        }
        return null;
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void removeJournalEntryById(String username, ObjectId id){
        boolean flag = userService.deleteJournalEntry(id,username);
        if(flag) journalEntryRepository.deleteById(id);
    }
}
