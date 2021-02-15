package de.hsrm.mi.swtpro.pflamoehus.test_request;

import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swtpro.pflamoehus.payload.request.OrderRequest;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class OrderRequestValueTests {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final int CORRECT_AMOUNT = 1;
    private final int INCORRECT_AMOUNT = -1;
    private final long INCORRECT_ARTICLENR = 0;
    private final long CORRECT_ARTICLENR = 10;
    private final double CORRECT_PRICETOTAL = 9.90;
    private final double INCORRECT_PRICETOTAL = 0;
    private List<OrderRequest.ProductDTO> correctProducts = new ArrayList<>();
    private List<OrderRequest.ProductDTO> incorrectProducts = new ArrayList<>();

    @BeforeAll
    public void createProducts(){
        correctProducts.add(new OrderRequest.ProductDTO(1,1));
    }

    @AfterAll
    public void closeFactory(){
        factory.close();
    }

    @Test
    @DisplayName("only invalid attributes should cause validationerrors.")
    public void invalid_orderDTO_attributes_cause_validationerrors(){
        OrderRequest request = new OrderRequest();

        request.setAllProductsOrdered(correctProducts);
        request.setPriceTotal(CORRECT_PRICETOTAL);
        assertEquals(0,validator.validate(request).size());

        request.setAllProductsOrdered(incorrectProducts);
        assertEquals(1,validator.validate(request).size());

        request.setPriceTotal(INCORRECT_PRICETOTAL);
        assertEquals(2,validator.validate(request).size());

    }

    @Test
    @DisplayName("only invalid ProductDTO attributes should cause validationerrors.")
    public void invalid_productDTO_attributes_cause_validationerrors(){
        OrderRequest.ProductDTO product = new OrderRequest.ProductDTO(CORRECT_AMOUNT, CORRECT_ARTICLENR);

        assertEquals(0, validator.validate(product).size());
        
        product.setAmount(INCORRECT_AMOUNT);

        assertEquals(1, validator.validate(product).size());

        product.setArticleNR(INCORRECT_ARTICLENR);

        assertEquals(2, validator.validate(product).size());
    }


}