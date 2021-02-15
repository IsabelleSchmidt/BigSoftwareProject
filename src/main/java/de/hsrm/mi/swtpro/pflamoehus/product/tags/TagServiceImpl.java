package de.hsrm.mi.swtpro.pflamoehus.product.tags;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.TagServiceException;

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
    public Tag searchTagWithId(long id) {
        Optional<Tag> tag = tagRepo.findById(id);
        if(tag.isEmpty()){
            throw new TagServiceException();
        }
        return tag.get();
    }

    @Override
    public Tag searchTagWithValue(String value) {
        Optional<Tag> tag = tagRepo.findByValue(value);
        if(tag.isEmpty()){
            throw new TagServiceException();
        }
        return tag.get();
    }
    
}
