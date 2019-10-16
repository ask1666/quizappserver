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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A user of the system. Bound to the authentication system
 *
 * @author Asbjørn
 */
@Entity @Table(name = "AUSER")
@Data @AllArgsConstructor @NoArgsConstructor
public class User implements Serializable {

    @OneToMany
    List<Quiz> quizs;
    
    @Id
    String userid;
    String password;
    
    public void addQuiz(Quiz quiz) {
        quizs.add(quiz);
    }
}
