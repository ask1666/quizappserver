/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.quizapp.services;

import com.mycompany.quizapp.domain.Question;
import com.mycompany.quizapp.domain.Quiz;
import com.mycompany.quizapp.domain.User;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import lombok.extern.java.Log;

/**
 *
 * @author geeky
 */
@Log
@Path("quizapp")
@Stateless
public class Services {
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    PasswordHash hasher;
    
    @GET
    @Path("getallquiz")
    public List<Quiz> getAllQuiz() {
        return em.createQuery("SELECT q FROM Quiz q ORDER BY q.name DESC").getResultList();
    }
    
    @GET
    @Path("getallquizfromuser")
    public List<Quiz> getAllQuiz(@QueryParam("userid") String userid) {
        User user = em.find(User.class, userid);
        return user.getQuizs();
    }
    
    @GET
    @Path("getallquestionsfromquiz")
    public List<Question> getAllQuestionsFromQuiz(@QueryParam("quizName") String quizName) {
        Quiz quiz = em.find(Quiz.class, quizName);
        return quiz.getQuestions();
    }
    
    @DELETE
    @Path("deletequiz")
    public Response deleteQuiz(@QueryParam("name") String name) {
        Quiz result = em.find(Quiz.class, name);
        if (result != null) {
            em.remove(result);
            return Response.ok(result).build();
        }
        
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("createquiz")
    public Response createQuiz(@QueryParam("name") String name, @QueryParam("username") String username) {
        Quiz quiz = em.find(Quiz.class, name);
        User user = em.find(User.class, username);
        if (quiz != null) {
            log.log(Level.INFO, "Quiz with that name already exists {0}", name);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if(user == null) {
            log.log(Level.INFO, "User doesnt exist", username);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            quiz = new Quiz();
            quiz.setName(name);
            em.persist(name);
            user.addQuiz(quiz);
            em.persist(user);
            return Response.ok(quiz).build();
            
        }
    }
    
    @GET
    @Path("createquestion")
    public Response createQuestion(@QueryParam("question") String question, @QueryParam("answer") String answer, @QueryParam("quiz") String quizName) {
        Question q = em.find(Question.class, question);
        Quiz quiz = em.find(Quiz.class, quizName);
        if (q != null) {
            log.log(Level.INFO, "question already exists", question);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if (quiz == null) {
            log.log(Level.INFO, "quiz doesnt exist", quizName);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            q = new Question();
            q.setQuestion(question);
            q.setAnswer(answer);
            em.persist(q);
            quiz.addQuestion(q);
            em.persist(quiz);
            return Response.ok(q).build();
        }
    }
    
    @GET
    @Path("getallquestions")
    public List<Question> getAllQuestions() {
        return em.createQuery("SELECT q from Question q ORDER BY q.question DESC").getResultList();
    }
    
    @DELETE
    @Path("deletequestion")
    public Response deleteQuestion(@QueryParam("question") String question, @QueryParam("quiz") String quizName) {
        Question q = em.find(Question.class, question);
        if (q == null) {
            log.log(Level.INFO, "question does not exist", question);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            em.remove(q);
            return Response.ok(q).build();
        }
    }
    
    @POST
    @Path("createuser")
    public Response createUser(@FormParam("userid") String userid, @FormParam("password") String password) {
        User user = em.find(User.class, userid);
        if (user != null) {
            log.log(Level.INFO, "User with that username already exists", userid);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            user = new User();
            user.setUserid(userid);
            user.setPassword(hasher.generate(password.toCharArray()));
            em.persist(user);
            return Response.ok(user).build();
        }
    }
    
    @POST
    @Path("login")
    public Response login(@FormParam("userid") String userid, @FormParam("password") String password) {
        User user = em.find(User.class, userid);
        if (user == null) {
            log.log(Level.INFO, "User with that username dont exists", userid);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if (user.getPassword().equals(hasher.generate(password.toCharArray()))) {
            return Response.ok(user).build();
            
        } else {
            log.log(Level.INFO, "Wrong password", password);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("getallusers")
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM auser u ORDER BY u.userid DESC").getResultList();
    }
    
}
