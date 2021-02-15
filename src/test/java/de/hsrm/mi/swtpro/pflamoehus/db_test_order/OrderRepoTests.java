package de.hsrm.mi.swtpro.pflamoehus.db_test_order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.OrderRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.Gender;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OrderRepoTests{

    private final LocalDate DATE = LocalDate.now().plusDays(10);
    private final String MAIL = "hans@gmx.de";
    private final double TOTALPRICE = 3;

    @Autowired
    UserRepository userRepo;
    
    @Autowired
    private OrderRepository orderRepo;

    @BeforeEach
    public void clear(){
        orderRepo.deleteAll();
    }

    @Test
    @DisplayName("Save new Orders into empty table")
    public void persist_empty(){
        orderRepo.deleteAll();
        Order unmanaged = new Order();
        unmanaged.setDeliveryDate(DATE);
        unmanaged.setCustomerEmail(MAIL);
        unmanaged.setPriceTotal(TOTALPRICE);

       Order managed = orderRepo.save(unmanaged);
       assertThat(managed.toString()).contains(DATE.toString());
       assertThat(orderRepo.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("Save and Delete multiple entries")
    public void save_delete(){

        orderRepo.deleteAll();
        List<Order> allorders= new ArrayList<>();
        

        for(int i = 1; i<=10; i++){
            Order order = new Order();
            LocalDate date = LocalDate.of(2021, 05, i);

            order.setDeliveryDate(date);
            order.setCustomerEmail(MAIL);
            order.setPriceTotal(TOTALPRICE+i);
            allorders.add(order);
            orderRepo.save(order);
        }

        assertThat(orderRepo.count()).isEqualTo(10);

        for(int i = 9; i>=0; i--){
            orderRepo.delete(allorders.get(i));
            assertThat(orderRepo.count()).isEqualTo(i);
        }
        
    }

    @Test
    @DisplayName("findBy ID & User & DATE asc")
    public void findBy(){

        for(int i = 1; i<=5; i++){
            Order order = new Order();
            order.setDeliveryDate(LocalDate.of(2022, 04, i));
            order.setCustomerEmail(i+MAIL);
            order.setPriceTotal(TOTALPRICE+i);
        }

        List<Order> allOrdersWithAscendingDate = orderRepo.findAllByOrderByDeliveryDateAsc();
        Order prev = null;

        for(Order akt : allOrdersWithAscendingDate){
            if(prev == null){
                prev = akt;
            }else{
                assertTrue(akt.getDeliveryDate().isAfter(prev.getDeliveryDate()));
                prev = akt;
            }
        }

        Order order = new Order();
        User user = new User();
        user.setEmail("olaf1@gmx.net");
        user.setBirthdate(LocalDate.of(2000, 8, 12));
        user.setLastName("Paulus");
        user.setFirstName("bernd");
        user.setPassword("HAhasdsndfjk92389.");
        user.setGender(Gender.FEMALE);
        userRepo.save(user);
        order.setUser(user);
        order.setCustomerEmail(MAIL);
        order.setDeliveryDate(DATE);
        order.setPriceTotal(TOTALPRICE);
        orderRepo.save(order);

        assertTrue(orderRepo.findByUser(user).size()>0);

        assertTrue(orderRepo.findByCustomerEmail(MAIL).size()>0);
        assertTrue(orderRepo.findById(order.getOrderNR()).isPresent());
    }
}