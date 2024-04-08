package com.api.comic_reader.services.Comic;

import com.api.comic_reader.dtos.ComicDTO;
import com.api.comic_reader.entities.ComicEntity;
import com.api.comic_reader.repositories.ComicRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComicService implements iComicService{
    @Autowired
    private ComicRepository comicRepository;

    @Override
    public List<ComicDTO> findAllComics() throws Exception{
        List<ComicEntity> comics = comicRepository.findAll();

        if(comics.isEmpty())
            throw new Exception("There is no comic added");

        List<ComicDTO> comicDTOList = comics.stream().map(comic -> {
            return ComicDTO.builder()
                    .id(comic.getId())
                    .name(comic.getName())
                    .description(comic.getDescription())
                    .author(comic.getAuthor())
                    .view(comic.getView())
                    .isFinished(comic.getIsFinished())
                    .imagePath(comic.getImagePath())
                    .comicGenres(comic.getComicGenres())
                    .ratings(comic.getRatings())
                    .build();
        }).toList();

        return comicDTOList;
    }

    @Override
    public ComicEntity findComicById(Long comicId) throws Exception {
        Optional<ComicEntity> comic = comicRepository.findById(comicId);

        if(!comic.isPresent())
            throw new Exception("The comic not found");

        return comic.get();
    }

    @Override
    public ComicEntity insertComic(ComicEntity comic) throws Exception {
        ComicEntity result = comicRepository.save(comic);

        return result;
    }

    @Override
    public ComicEntity updateComic(ComicEntity comic, Long comicId) throws Exception {
        Optional<ComicEntity> optionalComicEntity = comicRepository.findById(comicId);

        if(optionalComicEntity.isPresent()) {
            ComicEntity foundedComic = optionalComicEntity.get();

            foundedComic.setComicGenres(comic.getComicGenres());
            foundedComic.setAuthor(comic.getAuthor());
            foundedComic.setDescription(comic.getDescription());
            foundedComic.setImagePath(comic.getImagePath());
            foundedComic.setIsFinished(comic.getIsFinished());
            foundedComic.setName(comic.getName());

            return comicRepository.save(foundedComic);
        }

        throw new Exception("Cannot find the comic with id = " + comicId);
    }

    @Override
    public void deleteComic(Long comicId) throws Exception {
        Boolean exist = comicRepository.existsById(comicId);

        if(exist) comicRepository.deleteById(comicId);
        else throw new Exception("Cannot find the comic with id = " + comicId);
    }
}
