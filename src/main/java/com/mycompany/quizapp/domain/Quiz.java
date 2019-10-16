/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.quizapp.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author geeky
 */
@Entity @Data @NoArgsConstructor
@AllArgsConstructor
public class Quiz implements Serializable {
    
    @Id
    String name;
    
    @ManyToOne
    User auser;
    
    @OneToMany
    List<Question> questions;
    
    public void addQuestion(Question question) {
        questions.add(question);
    }
    
}
