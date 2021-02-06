package de.hsrm.mi.swtpro.pflamoehus.productapi;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;

public class PictureResponse {
    private List<Errormessage> allErrors;
    private List<Picture> pictures;

    public PictureResponse(){
        // this.pictures = new ArrayList<>();
        allErrors = new ArrayList<>();
    }

    public PictureResponse(List<Errormessage> allErrors){
        // this.pictures = pictures;
        this.allErrors = allErrors;
    }

    public void addErrormessage(Errormessage error){
        allErrors.add(error);
    }

    public List<Errormessage> getAllErrors(){
        return allErrors;
    }

    public void setAllErrors(List<Errormessage> allErrors){
        this.allErrors = allErrors;
    }

    // public List<Picture> getPicture(){
    //     return pictures;
    // }

    // public void addPicture(Picture picture){
    //     this.pictures.add(picture);
    // }

    // public void setAllPictures(List<Picture> allPictures){
    //     this.pictures = allPictures;
    // }

    
}
