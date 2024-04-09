package com.api.comic_reader.services.ComicUser;

import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.repositories.ComicUserRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ComicUserService implements iComicUserService {
    ComicUserRepository comicUserRepository;
    public ComicUserService() {
        
    }
    @Override
    public List<ComicUserEntity> getAllUsers() {
        List<ComicUserEntity> users = comicUserRepository.findAll();
        return users;
    }
    @Override
    public ComicUserEntity login(String username, String password) {
        return null;
    }
    @Override
    public void register(ComicUserEntity comicUser) {
        try {
            comicUserRepository.save(comicUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
