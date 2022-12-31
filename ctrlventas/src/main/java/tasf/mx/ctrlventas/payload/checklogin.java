/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasf.mx.ctrlventas.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author silot
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class checklogin {

    private Boolean islogin;

    public checklogin() {
    }

    /**
     * @return the islogin
     */
    public Boolean getIslogin() {
        return islogin;
    }

    /**
     * @param islogin the islogin to set
     */
    public void setIslogin(Boolean islogin) {
        this.islogin = islogin;
    }

}
