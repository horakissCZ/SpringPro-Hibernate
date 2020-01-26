package com.springpro;

import com.springpro.configuration.AppConfiguration;
import com.springpro.entity.Album;
import com.springpro.entity.Instrument;
import com.springpro.entity.Singer;
import com.springpro.repository.SingerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class SingerServiceTest {

    private GenericApplicationContext ctx;
    private SingerRepository singerRepository;

    @BeforeEach
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);
        singerRepository = ctx.getBean(SingerRepository.class);
        assertNotNull(singerRepository);
    }

    @Test
    public void testInsert(){
        Singer singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(new Date(
                (new GregorianCalendar(1940, 8, 16)).getTime().getTime()));
        Album album = new Album();
        album.setTitle("My Kind of Blues");
        album.setReleaseDate(new java.sql.Date(
                (new GregorianCalendar(1961, 7, 18)).getTime().getTime()));
        singer.addAlbum(album);
        album = new Album();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(new java.sql.Date(
                (new GregorianCalendar(1962, 3, 20)).getTime().getTime()));
        singer.addAlbum(album);
        singerRepository.save(singer);
        assertNotNull(singer.getId());
        List<Singer> singers = singerRepository.findAllWithAlbum();
        assertEquals(4, singers.size());
        listSingersWithAlbum(singers);
    }

    @Test
    public void testFindAll(){
        List<Singer> singers = singerRepository.findAll();
        assertEquals(3, singers.size());
        listSingers(singers);
    }

    @Test
    public void testFindAllWithAlbum(){
        List<Singer> singers = singerRepository.findAllWithAlbum();
        assertEquals(3, singers.size());
        listSingersWithAlbum(singers);
    }

    @Test
    public void testFindByID(){
        Singer singer = singerRepository.findById(1L);
        assertNotNull(singer);
        log.info(singer.toString());
    }

    @Test
    public void testUpdate(){
        Singer singer = singerRepository.findById(1L);
        //making sure such singer exists
        assertNotNull(singer);
        //making sure we got expected singer
        assertEquals("Mayer", singer.getLastName());
        //retrieve the album
        Album album = singer.getAlbums().stream().filter(
                a -> a.getTitle().equals("Battle Studies")).findFirst().get();
        singer.setFirstName("John Clayton");
        singer.removeAlbum(album);
        singerRepository.save(singer);
        // test the update
        listSingersWithAlbum(singerRepository.findAllWithAlbum());
    }

    private static void listSingers(List<Singer> singers) {
        log.info(" ---- Listing singers:");
        for (Singer singer : singers) {
            log.info(singer.toString());
        }
    }

    private static void listSingersWithAlbum(List<Singer> singers) {
        log.info(" ---- Listing singers with instruments:");
        for (Singer singer : singers) {
            log.info(singer.toString());
            if (singer.getAlbums() != null) {
                for (Album album :
                        singer.getAlbums()) {
                    log.info("\t" + album.toString());
                }
            }
            if (singer.getInstruments() != null) {
                for (Instrument instrument : singer.getInstruments()) {
                    log.info("\tInstrument: " + instrument.getInstrumentId());
                }
            }
        }
    }

    @AfterEach
    public void tearDown(){
        ctx.close();
    }
}
