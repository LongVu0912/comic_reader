package com.api.comic_reader.services.Comic;

import com.api.comic_reader.dtos.ComicDTO;
import com.api.comic_reader.entities.ComicEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface iComicService {
    List<ComicDTO> findAllComics() throws Exception;

    ComicEntity findComicById(Long comicId) throws Exception;

    ComicEntity insertComic(ComicEntity comic) throws Exception;

    ComicEntity updateComic (ComicEntity comic, Long comicId) throws Exception;

    void deleteComic(Long comicId) throws Exception;
}
