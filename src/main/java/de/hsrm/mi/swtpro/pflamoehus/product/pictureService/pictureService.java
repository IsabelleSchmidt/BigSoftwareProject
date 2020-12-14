package de.hsrm.mi.swtpro.pflamoehus.product.pictureService;

import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;

public interface pictureService {
    
    public List<Picture> findAll();

    public Picture findPictureWithID(long id);

    public List<Picture> findPicturesWithProduct(Product product);

    public List<Picture> findAllWithPath(String path);
}
