package com.anandakshay.journalApp.service;

import com.anandakshay.journalApp.entity.JournalEntry;
import com.anandakshay.journalApp.entity.User;
import com.anandakshay.journalApp.repository.JournalEntryRepository;
import com.anandakshay.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewEntry(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add("USER");
        userRepository.save(user);
    }

    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        journalEntryRepository.deleteAll(user.getJournalEntries());
        userRepository.deleteUserByUsername(username);
    }

    public void saveNewJournalEntry(JournalEntry journalEntry,String username) {
        User user = userRepository.findByUsername(username);
        user.getJournalEntries().add(journalEntry);
        userRepository.save(user);
    }

    public boolean deleteJournalEntry(ObjectId id,String username) {
        User user = userRepository.findByUsername(username);
        boolean flag = user.getJournalEntries().removeIf(j -> j.getId().equals(id));
        userRepository.save(user);
        return flag;
    }

    public List<User> getUserEntries() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
