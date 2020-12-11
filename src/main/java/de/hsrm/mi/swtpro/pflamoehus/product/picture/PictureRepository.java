package de.hsrm.mi.swtpro.pflamoehus.product.picture;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    //TODO: soll alle Bilder mit der passenden Artikelnr finden
    List<Optional<Picture>> findByProduct(long articleNr);
}
