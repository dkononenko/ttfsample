package ru.dynsys.ttfsample;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CatService {
    public Cat create(String name) {
        StringUtils.hasText(name);

        Cat cat = new Cat(name);
        em.persist(cat);

        return cat;
    }

    @PersistenceContext
    private EntityManager em;
}
