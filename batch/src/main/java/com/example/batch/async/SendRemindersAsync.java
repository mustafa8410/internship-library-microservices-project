package com.example.batch.async;

import com.example.batch.entity.Rented;
import com.example.batch.exception.mail.MailNotSentException;
import com.example.batch.repository.RentedRepository;
import com.example.batch.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.Future;

@Slf4j
@Service
public class SendRemindersAsync {

    private final MailService mailService;
    private final RentedRepository rentedRepository;

    public SendRemindersAsync(MailService mailService, RentedRepository rentedRepository) {
        this.mailService = mailService;
        this.rentedRepository = rentedRepository;
    }

    @Async
    public Future<Boolean> sendRemindersAsynchronously(Rented expired){
        log.info("Started async job for rental id: " + expired.getId() + " --- Currently sending mail for: Rent id: "+
                expired.getId() + ", User email: " + expired.getRenter().getMail());
        String title, message;
        title = ("About The Expiration Of Your Rental: " + expired.getBook().getTitle());
        message = "Hello " + expired.getRenter().getName() + ",\nWe want to remind you that your rental of" +
                " the book " + expired.getBook().getTitle() +
                " is expired. We are expecting you to return the book as soon as possible.";
        try{
            mailService.sendMail(expired.getRenter().getMail(), title, message);
        }
        catch (Exception e) {
            throw new MailNotSentException();
        }
        markRentAsAlerted(expired);
        log.info("Email sent to " + expired.getRenter().getMail() + " for rent id: " +
                expired.getId() + " successfully.");
        return new AsyncResult<>(true);
    }

    @Transactional
    public void markRentAsAlerted(Rented rented){
        rented.setAlertSent(true);
        rentedRepository.save(rented);
    }
}
