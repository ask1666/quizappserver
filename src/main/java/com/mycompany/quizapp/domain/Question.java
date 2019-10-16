/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.quizapp.domain;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A user of the system. Bound to the authentication system
 *
 * @author Asbjørn
 */
/**
 *
 * @author geeky
 */
@Entity @Data @AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {
    
    @Id
    String question;
    String answer;
    
    @ManyToOne
    Quiz quiz;
    
    
    
}
