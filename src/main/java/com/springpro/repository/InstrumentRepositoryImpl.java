package com.springpro.repository;

import com.springpro.entity.Instrument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Repository("instrumentRepository")
@RequiredArgsConstructor
public class InstrumentRepositoryImpl implements InstrumentRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Instrument save(Instrument instrument) {
        sessionFactory.getCurrentSession().saveOrUpdate(instrument);
        log.info("Instrument saved with id: " + instrument.getInstrumentId());
        return instrument;
    }
}
