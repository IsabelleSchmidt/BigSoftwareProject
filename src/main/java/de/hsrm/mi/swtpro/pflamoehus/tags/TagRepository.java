package de.hsrm.mi.swtpro.pflamoehus.tags;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
    
    Tag findByValue(String value);
    Tag findById(long id);
}
