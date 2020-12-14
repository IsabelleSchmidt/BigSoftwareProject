package de.hsrm.mi.swtpro.pflamoehus.product.pictureService;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.PictureRepository;

@Service
public class pictureServiceImpl implements pictureService {

    @Autowired
    PictureRepository pictureRepository;

    @Override
    public List<Picture> findAll() {
        
        return pictureRepository.findAll();
    }

    @Override
    public Picture findPictureWithID(long id) {
        
        return pictureRepository.findById(id);
    }

    @Override
    public List<Picture> findPicturesWithProduct(Product product) {
        
        return pictureRepository.findByProduct(product);
    }

    @Override
    public List<Picture> findAllWithPath(String path) {
        //Filtert die Liste nach Bildern die den relativen Pfad enthalten
        Predicate<Picture> byRelPath = picture -> picture.getPath().contains(path);
        return findAll().stream().filter(byRelPath).collect(Collectors.toList());
    }

    
}
