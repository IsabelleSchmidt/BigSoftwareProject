package de.hsrm.mi.swtpro.pflamoehus.email.emailservice;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64.Encoder;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * EmailServiceImpl for implementing the interface 'EmailService'.
 * 
 * @author Sarah Wenzel
 * @version 1
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javamailsender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    
    /**
     * Sends an email from pflamoehus@mi.de
     * 
     * @param to The receiver of the email.
     * @param body The message of the email.
     * @param topic The title of the email.
     * 
     */
    @Override
    public void sendEmail(String to, String body, String topic) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("pflamoehus@mi.de");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javamailsender.send(simpleMailMessage);
       LOGGER.info("Standard Email gesendet.");
    }

    /**
     * Sends an order confirmation email from pflamoehus@mi.de
     * The email contains a dynamically rendered html invoice
     * @param order the order that this confirmation email is for
     * @param user  the user that placed the order and receives this email
     */
    @Override
    @Transactional
    public void sendHTMLmail( Order order,User user) throws MessagingException, IOException {
    
        HashMap<String, Object> contextdata = new HashMap<>(); //data to fill thymeleaf form
        Encoder encoder = Base64.getEncoder(); //encoder to encode images to base64 strings

        
        Map<Long,String> picturePerOrderedProduct = new HashMap<>(); //Saves a Base64 String of an image to an articlenumber of a product
        Map<Product, Integer> allProducts = new HashMap<>();  //Stores a product to the amount bought
       
         //Get all products of an Order and encode their first image to a Base64 string
        for(OrderDetails detail: order.getOrderdetails()){

            allProducts.put(detail.getProduct(),detail.getProductAmount());
           Product product =  detail.getProduct();
           String path = product.getAllPictures().iterator().next().getPath();
           URL url = getClass().getResource("/static"+path);
           path = url.getPath().replaceFirst("/", "").replaceAll("%20", " ");

           try{
                Path filepath = Paths.get(path); 
                byte[] bytes = Files.readAllBytes(filepath); 
                String base64String = encoder.encodeToString(bytes);
                String base64Image = "data:image/png;base64," + base64String;
                picturePerOrderedProduct.put(product.getArticlenr(), base64Image);

           }catch(InvalidPathException ipe){
               LOGGER.error("Pfad: "+path+" konnte nicht gelesen werden.");
               sendEmail(user.getEmail(),"Vielen Dank fuer Ihre Bestellung im Pflamoehus! \n Ihre Bestellnummer: "+order.getOrderNR() , "Bestellbestaetigung");
           }
          
          
        }
        //fill the form with information
        contextdata.put("greeting", "Hallo "+user.getFirstName()+" "+user.getLastName());
        contextdata.put("price","Preis: "+order.getPriceTotal());
        contextdata.put("deliverydate", "Lieferdatum: "+order.getDeliveryDate());
        contextdata.put("allproducts", allProducts);
        contextdata.put("totalprice","Gesamtsumme: "+ order.getPriceTotal()+"€");
        contextdata.put("ordernr", "Deine Bestellung mit Nummer: "+order.getOrderNR()+" enthält folgende Produkte");
        contextdata.put("images", picturePerOrderedProduct);

        MimeMessage message = javamailsender.createMimeMessage(); //allows to use attachment to emails
        MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");
      
        Context context = new Context(); //thymeleaf template context
        context.setVariables(contextdata);//add previous defined data to the context
        
        String html = templateEngine.process("orderconfirmation", context); //process the html file
        helper.setTo(user.getEmail());
        helper.setText(html, true);
        helper.setSubject("Ihre Bestellung im pflamoehus");
        helper.setFrom("pflamoehus@mi.de");
        javamailsender.send(message);
        LOGGER.info("HTML Email gesendet.");
    }

 

    
}
