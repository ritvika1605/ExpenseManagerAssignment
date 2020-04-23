package com.assignment.expensemanager.emailservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@Service
@Configuration
public class NotificationService {
    @Autowired
    private JavaMailSender javamailsender;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService() {
        super();
    }
    @Autowired
    public NotificationService(JavaMailSender javamailsender) {
        super();
        this.javamailsender = javamailsender;
    }

    public static int noOfQuickServiceThreads = 20;

    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads); // Creates a thread pool that reuses fixed number of threads(as specified by noOfThreads in this case).

    public String sendNotification(String email, Double totalExpense)throws MailException
    {
        System.out.println(email);

        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setFrom("ritvika.96@gmail.com");
        mail.setTo(email);
        mail.setSubject("Hello Ms. Ritvika");
        mail.setText("Your total expense is: "+totalExpense);

        quickService.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    javamailsender.send(mail);
                }catch(Exception e){
                    logger.error("Exception occur while send a mail : ",e);
                }
            }
        });
        return ("Mail sent to"+email);
        //javamailsender.send(mail);
    }



}
