package de.hsrm.mi.swtpro.pflamoehus.tags;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
    String findValueById(String value);
    String findIdByValue(int id);
}
