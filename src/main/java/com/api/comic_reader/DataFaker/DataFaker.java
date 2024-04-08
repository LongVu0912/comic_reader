package com.api.comic_reader.DataFaker;

import com.api.comic_reader.entities.*;
import com.api.comic_reader.entities.composite_keys.BookmarkKey;
import com.api.comic_reader.entities.composite_keys.ComicGenreKey;
import com.api.comic_reader.repositories.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Configuration
public class DataFaker {
    @Autowired
    private ComicUserRepository comicUserRepository;
    @Autowired
    private ComicRepository comicRepository;
    @Autowired
    private ComicGenreRepository comicGenreRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private CommentRepository chapterCommentRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Bean
    public String createFakeData() {

        Faker faker = new Faker();
        Integer number_of_comics = 20;
        Integer number_of_users = 20;
        Integer number_of_genres = 20;
        Integer number_of_chapter = 10;
        Integer number_of_chapter_comment = 10;


        // Create roles and save them
        roleRepository.save(RoleEntity.builder().name("admin").build());
        roleRepository.save(RoleEntity.builder().name("user").build());


        //create comic user fake db
        for (int i = 1; i <= number_of_users; i++) {
            comicUserRepository.save(
                    ComicUserEntity.builder()
                            .username("username" + i)
                            .password("123456")
                            .isBanned(false)
                            .isMale(faker.bool().bool())
                            .fullName(faker.name().fullName())
                            .dateOfBirth(faker.date().birthday())
                            .role(roleRepository.findByName("user"))
                            .build()
            );
        }

        //create comic fake db
        for (int i = 1; i <= number_of_comics; i++) {
            comicRepository.save(
                    ComicEntity.builder()
                            .author(faker.book().author())
                            .description(faker.lorem().sentence(10))
                            .imagePath("/")
                            .isDeleted(false)
                            .isFinished(faker.bool().bool())
                            .name(faker.book().title())
                            .view(Long.valueOf(faker.random().nextInt(0, 10000)))
                            .build()
            );
        }

        //create genre fake db
        for (int i = 1; i <= number_of_genres; i++) {
            genreRepository.save(
                    GenreEntity.builder()
                            .name(faker.book().genre())
                            .genreDescription("Description of " + faker.book().genre())
                            .build()
            );
        }

        //create chapter fake db
        for (int i = 1; i <= number_of_comics; i++) {
            for (int j = 1; j <= number_of_chapter; j++) {
                chapterRepository.save(
                        ChapterEntity.builder()
                                .chapterNumber(Long.valueOf(j))
                                .pageQuantity(Long.valueOf(100))
                                .title(faker.book().title())
                                .comic(comicRepository.findById(Long.valueOf(i)).get())
                                .build()
                );
            }
        }

        //create chapter comments fake db

        // Create a LocalDate object representing the first day of the year 2020
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        for (int i = 1; i <= number_of_chapter; i++) {
            for (int j = 1; j <= number_of_chapter_comment; j++) {

                chapterCommentRepository.save(
                        CommentEntity.builder()
                                .content(faker.lorem().sentence(15))
                                .createAt(faker.date().between(calendar.getTime(), Calendar.getInstance().getTime()))
                                .chapter(chapterRepository.findById(Long.valueOf(i)).get())
                                .comicUser(comicUserRepository.findById(Long.valueOf(faker.random().nextInt(1, number_of_users))).get())
                                .build()
                );
            }
        }

        //create ratings fake db
        for (int i = 1; i <= number_of_comics; i++) {
            for (int j = 1; j <= number_of_users - 10; j++) {

                ratingRepository.save(
                        RatingEntity.builder()
                                .score(faker.random().nextInt(1,5))
                                .comic(comicRepository.findById(Long.valueOf(i)).get())
                                .comicUser(comicUserRepository.findById(Long.valueOf(faker.random().nextInt(1,number_of_users))).get())
                                .build()
                );
            }
        }

        //create bookmarks fake db
        for (int i = 1; i <= number_of_users; i++) {
            ChapterEntity chapter = chapterRepository.findById(Long.valueOf(faker.random().nextInt(1, number_of_chapter*number_of_comics))).get();
            ComicUserEntity comicUser = comicUserRepository.findById(Long.valueOf(i)).get();

            bookmarkRepository.save(
                    BookmarkEntity.builder()
                            .chapter(chapter)
                            .comicUser(comicUser)
                            .id(BookmarkKey.builder()
                                    .chapterId(chapter.getId())
                                    .comicUserId(comicUser.getId())
                                    .build()
                            )
                            .build()
            );
        }

        //create comic_genre fake db
        for (int i = 1; i <= number_of_comics; i++) {
            for (int j = 1; j <= 5; j++) {
                ComicEntity comic = comicRepository.findById(Long.valueOf(i)).get();
                GenreEntity genre = genreRepository.findById(Long.valueOf(faker.random().nextInt(1,number_of_genres))).get();

                comicGenreRepository.save(
                        ComicGenreEntity.builder()
                                .comic(comic)
                                .genre(genre)
                                .id(ComicGenreKey.builder()
                                        .comicId(comic.getId())
                                        .genreId(genre.getId())
                                        .build()
                                )
                                .build()
                );
            }
        }

        return "Initialize user comic successfully";
    }
}
