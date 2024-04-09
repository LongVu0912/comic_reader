package com.api.comic_reader.services.ComicUser;
import java.util.List;

import com.api.comic_reader.entities.ComicUserEntity;
public interface iComicUserService {
    List<ComicUserEntity> getAllUsers();
    ComicUserEntity login(String username, String password) throws Exception;
    void register(ComicUserEntity comicUser) throws Exception;
}
