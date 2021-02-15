package de.hsrm.mi.swtpro.pflamoehus.db_test_order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import de.hsrm.mi.swtpro.pflamoehus.order.OrderRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetailsRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.order.status.StatusRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Statuscode;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class StatusRepoTests {

    final Statuscode STATUSCODE = Statuscode.READYFORSHIPPING;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StatusRepository statusRepo;

    @BeforeEach
    void clear_repos() {
        orderDetailsRepository.deleteAll();
        orderRepository.deleteAll();
        statusRepo.deleteAll();
    }

    @Test
    @DisplayName("Persist status entity (empty table)")
    void status_persist() {
        final Status unmanaged = new Status();
        unmanaged.setStatuscode(STATUSCODE);
        

        final Status managed = statusRepo.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);

        assertThat(statusRepo.count()).isEqualTo(1);

    }

    @Test
    @DisplayName("ProductRepository findByStatuscode")
    void product_name_findByName() {
        final int COUNT = 2;


        final Status status = new Status();
        status.setStatuscode(STATUSCODE);

        final Status status1 = new Status();
        status1.setStatuscode(Statuscode.INPROGRESS);

        statusRepo.save(status);
        statusRepo.save(status1);

        assertThat(statusRepo.count()).isEqualTo(COUNT);
        Optional<Status> fund = statusRepo.findByStatuscode(Statuscode.READYFORSHIPPING);
        assertThat(fund.isPresent());
        assertThat(fund.get().getStatuscode()).isEqualTo(STATUSCODE);

        
    }
    
}
