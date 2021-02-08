package de.hsrm.mi.swtpro.pflamoehus.product.tags;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepo;

    @Override
    public List<Tag> allTags() {
        return tagRepo.findAll();
    }

    @Override
    public Optional<Tag> searchTagWithId(long id) {
        Optional<Tag> tag = tagRepo.findById(id);
        return null;
    }

    @Override
    public Optional<Tag> searchTagWithValue(String value) {
        Optional<Tag> tag = tagRepo.findByValue(value);
        return null;
    }
    
}
