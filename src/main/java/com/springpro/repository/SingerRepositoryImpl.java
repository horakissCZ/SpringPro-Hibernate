package com.springpro.repository;

import com.springpro.entity.Singer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Repository("singerRepository")
@RequiredArgsConstructor
public class SingerRepositoryImpl implements SingerRepository {

    private final SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Singer> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Singer s", Singer.class).list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Singer> findAllWithAlbum() {
        return sessionFactory.getCurrentSession().getNamedQuery("Singer.findAllWithAlbum").list();
    }

    @Override
    @Transactional(readOnly = true)
    public Singer findById(Long id) {
        return (Singer) sessionFactory
                .getCurrentSession()
                .getNamedQuery("Singer.findById")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Singer save(Singer singer) {
        sessionFactory.getCurrentSession().saveOrUpdate(singer);
        log.info("Singer saved with id: " + singer.getId());
        return singer;
    }

    @Override
    public void delete(Singer singer) {
        sessionFactory.getCurrentSession().delete(singer);
        log.info("Singer deleted with id: " + singer.getId());
    }
}
