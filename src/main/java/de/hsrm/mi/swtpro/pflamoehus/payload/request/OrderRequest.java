package de.hsrm.mi.swtpro.pflamoehus.payload.request;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;


/*
 * Data Transfer Object for an Order
 * 
 * @author Svenja Schenk
 * @version 1
 */
@Validated
public class OrderRequest {


    /**
     * Mask for a product included in a order
     * 
     * @author Svenja Schenk
     */
    public static class ProductDTO{

        @Positive(message = "The articlenr is incorrect. It needs to be positive.")
        private long articleNR;

        @Positive(message = "The amount of products to buy cannot be zero or less.")
        private int amount; 
        
        
        public ProductDTO(){
            //Empty constructor
        }

        public ProductDTO(int amount, long articleNR){
            this();
            this.amount = amount;
            this.articleNR = articleNR;
        }
        /**
         * Get articlenr.
         * 
         * @return long
         */
        public long getArticleNR() {
            return articleNR;
        }

        /**
         * Set articlenr.
         * 
         * @param articleNR to be set
         */
        public void setArticleNR(long articleNR) {
            this.articleNR = articleNR;
        }

        /**
         * Get amount.
         * 
         * @return int
         */
        public int getAmount() {
            return amount;
        }

        /**
         * Set amount.
         * 
         * @param amount to be set
         */
        public void setAmount(int amount) {
            this.amount = amount;
        }

        
    }

    @Digits(integer = 5, fraction = 2, message = "The price can only have up to 5 digits before the comma and 2 after.")
    @Positive(message = "the price needs to be a positive value")
    private double priceTotal;

    @Size(min = 1, message = "You need to buy at least one product per order.")
    private List<@Valid ProductDTO> allProductsOrdered= new ArrayList<>();
    
    /** 
     * Get price in total.
     * 
     * @return double
     */
    public double getPriceTotal() {
        return priceTotal;
    }

    
    /** 
     * Set price in total.
     * 
     * @param priceTotal to be set
     */
    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    
    /** 
     * Get all ordered products.
     * 
     * @return list of all ordered products
     */
    public List<ProductDTO> getAllProductsOrdered() {
        return allProductsOrdered;
    }

    
    /** 
     * Set all orderes produtcts.
     * 
     * @param allProductsOrdered to be set
     */
    public void setAllProductsOrdered(List<ProductDTO> allProductsOrdered) {
        this.allProductsOrdered = allProductsOrdered;
    }
    
    
}
