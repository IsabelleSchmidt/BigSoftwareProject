package de.hsrm.mi.swtpro.pflamoehus.db_test_product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.dao.DataIntegrityViolationException;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.PictureRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.Tag;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.TagRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class TagRepositoryTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private PictureRepository pictureRepo;


    private final String VALUE = "Keramik";

    @Test
    void basecheck() {
        assertThat(TagRepository.class).isInterface();
        assertThat(tagRepo).isNotNull();
    }

    @Test
    @DisplayName("persist Tag")
    void persist_tag() {

        pictureRepo.deleteAll();
        productRepo.deleteAll();
        tagRepo.deleteAll();

        Tag unmanaged = new Tag();
        unmanaged.setValue(VALUE);

        final Tag managed = tagRepo.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);

        assertThat(tagRepo.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("Save and delete tags from repository")
    void save_and_delete_tag() {

        pictureRepo.deleteAll();
        productRepo.deleteAll();
        tagRepo.deleteAll();

        List<Tag> allTags = new ArrayList<Tag>();

        final int COUNT = 5;
        for (int i = 0; i < COUNT; i++) {
            Tag tag1 = new Tag();
            allTags.add(tag1);
            tagRepo.save(tag1);
            assertEquals(tagRepo.count(),i + 1, "The repo should hold " + (i + 1) + " tags.");
        }

        for (int i = COUNT; i > 0; i--) {
            tagRepo.delete(allTags.get((i - 1)));
            assertEquals(tagRepo.count(),i - 1, "The repo should have deleted " + (COUNT - (i - 1)) + " tags.");
        }

    }

    @Test
    @DisplayName("TagRepository findBy.. ")
    void repo_findBy() {

        pictureRepo.deleteAll();
        productRepo.deleteAll();
        tagRepo.deleteAll();

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();

        tag1.setValue(VALUE);

        tag2.setValue("Different Value");

        tagRepo.save(tag1);
        tagRepo.save(tag2);

        Optional<Tag> tag3;
        tag3 = tagRepo.findByValue(VALUE);
        assertThat(tag3.get().getValue()).isEqualTo(tag1.getValue());

        tag3 = tagRepo.findById(tag2.getId());
        assertThat(tag3.get().getId()).isEqualTo(tag2.getId());

    }

    @Test
    @DisplayName("create two tags with the same value")
    void check_unique_values() {

        pictureRepo.deleteAll();
        productRepo.deleteAll();
        tagRepo.deleteAll();

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();

        tag1.setValue(VALUE);
        tag2.setValue(VALUE);

        tagRepo.save(tag1);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            tagRepo.save(tag2);
        });

        assertThat(tagRepo.count()).isEqualTo(1);

    }

}
