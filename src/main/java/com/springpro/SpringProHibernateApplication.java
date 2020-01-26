package com.springpro;

import com.springpro.configuration.AppConfiguration;
import com.springpro.entity.Album;
import com.springpro.entity.Instrument;
import com.springpro.entity.Singer;
import com.springpro.repository.SingerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

@Slf4j
public class SpringProHibernateApplication {

	public static void main(String[] args) {
		GenericApplicationContext ctx =
				new AnnotationConfigApplicationContext(AppConfiguration.class);
		SingerRepository singerDao = ctx.getBean(SingerRepository.class);
		listSingers(singerDao.findAllWithAlbum());
		ctx.close();
	}

	private static void listSingers(List<Singer> singers) {
		log.info(" ---- Listing singers:");
		for (Singer singer : singers) {
			log.info(singer.toString());
			listChildren(singer);
		}
	}

	private static void listChildren(Singer singer) {
		log.info(singer.toString());
		if (singer.getAlbums() != null) {
			for (Album album :
					singer.getAlbums()) {
				log.info("\t" + album.toString());
			}
		}
		if (singer.getInstruments() != null) {
			for (Instrument instrument : singer.getInstruments()) {
				log.info("\t" + instrument.getInstrumentId());
			}
		}
	}

}
