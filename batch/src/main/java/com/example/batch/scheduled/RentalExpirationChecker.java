package com.example.batch.scheduled;

import com.example.batch.async.SendRemindersAsync;
import com.example.batch.entity.Rented;
import com.example.batch.exception.multithreading.ThreadCouldNotExecuteException;
import com.example.batch.repository.RentedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Component
public class RentalExpirationChecker {

    private final RentedRepository rentedRepository;
    private final SendRemindersAsync sendRemindersAsync;

    public RentalExpirationChecker(RentedRepository rentedRepository, SendRemindersAsync sendRemindersAsync) {
        this.rentedRepository = rentedRepository;
        this.sendRemindersAsync = sendRemindersAsync;
    }


    @Scheduled(cron = "0 0 12 * * *")
    public void sendReminders() throws Exception{
        log.info("Scheduled operation of sendReminders() has started.");
        List<Rented> nonRemindedExpiredRentals = rentedRepository.findAllByEndDateBeforeAndAlertSentIsFalse(Date.from(Instant.now()));
        log.info("Extracted all the expired rents with none alert sent. Total amount: " + nonRemindedExpiredRentals.size());
        // String title, message;
        log.info("Going through each of the expired rents and sending reminders...");
        Collection<Future<Boolean>> asyncList = new ArrayList<>();
        /*for(Rented rented: nonRemindedExpiredRentals)
            System.out.println("Rent id: " + rented.getId());*/
        for(Rented expired: nonRemindedExpiredRentals){
            /*log.info("Currently sending mail for: Rent id: " + expired.getId() + ", User email: " + expired.getRenter().getMail());
            title = ("About The Expiration Of Your Rental: " + expired.getBook().getTitle());
            message = "Hello " + expired.getRenter().getName() + ",\nWe want to remind you that your rental of" +
                    " the book " + expired.getBook().getTitle() + " is expired. We are expecting you to return the book as soon as possible.";
            try{
                mailService.sendMail(expired.getRenter().getMail(), title, message);
            }
            catch (Exception e) {
                throw new MailNotSentException();
            }
            markRentAsAlerted(expired);
            log.info("Email sent to " + expired.getRenter().getMail() + " successfully."); */
            asyncList.add(sendRemindersAsync.sendRemindersAsynchronously(expired));
        }
        try{
            while(!checkThreads(asyncList))
                Thread.sleep(1000);
        }
        catch (Exception e) {
            log.error("Something wrong has happened while trying to wait.");
            throw e;
        }

        /*List<CompletableFuture<Boolean>> asyncList = nonRemindedExpiredRentals.stream()
                .map(expired -> sendRemindersAsync.sendRemindersAsynchronously(expired)).collect(Collectors.toList());*/

        // Wait for all async tasks to complete
        //CompletableFuture.allOf(asyncList.toArray(new CompletableFuture[0])).join();



        log.info("sendReminders() finished executing successfully.");
    }

    public boolean checkThreads(Collection<Future<Boolean>> asyncList){
        for(Future<Boolean> thread: asyncList){
            if(!thread.isDone())
                return false;
            try {
                thread.get();
            }
            catch (Exception e) {
                throw new ThreadCouldNotExecuteException();
            }
        }
        return true;
    }

}
