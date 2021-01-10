package de.hsrm.mi.swtpro.pflamoehus.payload.request;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import de.hsrm.mi.swtpro.pflamoehus.payload.response.JwtResponse;

/**
 * Data Transfer Object for an Order
 */
@Validated
public class OrderRequest {


    public class ProductDTO{

        @Positive(message = "the articlenr is incorrect. It needs to be positive.")
        private long articleNR;

        @Positive(message = "the amount of products to buy cannot be zero or less.")
        private int amount; 
        
        public long getArticleNR() {
            return articleNR;
        }

        public void setArticleNR(long articleNR) {
            this.articleNR = articleNR;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        
    }

    @Digits(integer = 5, fraction = 2, message = "The price can only have up to 5 digits before the comma and 2 after.")
    @Positive(message = "the price needs to be a positive value")
    private double priceTotal;

    @Size(min = 1, message = "You need to buy at least one product per order.")
    private List<@Valid ProductDTO> allProductsOrdered= new ArrayList<>();

    @NotNull(message = "Please add a user to the order.")
    @Valid
    private JwtResponse jwtToken; //used to identify user

    //Getter and Setter

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public List<ProductDTO> getAllProductsOrdered() {
        return allProductsOrdered;
    }

    public void setAllProductsOrdered(List<ProductDTO> allProductsOrdered) {
        this.allProductsOrdered = allProductsOrdered;
    }

    public JwtResponse getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(JwtResponse jwtToken) {
        this.jwtToken = jwtToken;
    }
    
    
}
