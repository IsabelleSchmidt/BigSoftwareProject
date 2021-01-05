package de.hsrm.mi.swtpro.pflamoehus.db_test_order;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.OrderRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderRepoTests{

    private final LocalDate DATE = LocalDate.of(2021, 06, 01);

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
        Order unmanaged = new Order();
        unmanaged.setDeliveryDate(DATE);

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
        }

        List<Order> allOrdersWithAscendingDate = orderRepo.findAllByOrderByDeliveryDateAsc();
        Order prev = null;

        for(Order akt : allOrdersWithAscendingDate){
            if(prev == null){
                prev = akt;
            }else{
                assertThat(akt.getDeliveryDate().isAfter(prev.getDeliveryDate()));
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
        user.setGender("FEMALE");
        userRepo.save(user);
        order.setUser(user);

        assertThat(orderRepo.findByUser(user).isPresent());


        assertThat(orderRepo.findById(order.getOrderNR()).isPresent());
    }
}