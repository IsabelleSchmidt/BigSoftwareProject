package de.hsrm.mi.swtpro.pflamoehus.order.orderapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * OrderRestApi for communication between front- and backend.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@RestController
@CrossOrigin
@RequestMapping("/api/order")
public class OrderRestApi {

    private class OrderMessage {
        private String field;
        private String message;

        public OrderMessage(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "OrderMessage [field=" + field + ", message=" + message + "]";
        }

    }

    
    /** 
     * Delete order.
     * 
     * @return boolean
     */
    @DeleteMapping("/delete/{orderNR}")
    public boolean deleteOrder() {

        return true;
    }

    
    /** 
     * Edit order.
     * 
     * @return OrderMessage
     */
    @PutMapping("/edit/{orderNR}")
    public ResponseEntity<OrderMessage> editOrder() {

        return ResponseEntity.ok().body(new OrderMessage("", ""));
    }

    
    /** 
     * New Order.
     * 
     * @return OrderMessage
     */
    @PostMapping("/new")
    public ResponseEntity<OrderMessage> newOrder() {

        return ResponseEntity.ok().body(new OrderMessage("", ""));
    }

}
