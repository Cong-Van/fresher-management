package com.vmo.freshermanagement.intern.service.impl;

import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.CronjobService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.vmo.freshermanagement.intern.constant.SecurityConstant.ADMINISTRATION;
import static com.vmo.freshermanagement.intern.constant.SecurityConstant.EMAIL_TO_SEND;

@Service
public class CronJobServiceImpl implements CronjobService {

    private FresherRepository fresherRepository;
    private JavaMailSender javaMailSender;

    public CronJobServiceImpl(FresherRepository fresherRepository,
                              JavaMailSender javaMailSender) {
        this.fresherRepository = fresherRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Scheduled(cron = "0 0 20 ? * FRI") // Send every Friday at 20:00
    public void sendFresherMarkByEmail() throws MessagingException, UnsupportedEncodingException {
        List<Fresher> freshers = fresherRepository.findAllNotGraduatedFresher();
        for (Fresher fresher : freshers) {
            sendEmail(fresher);
        }
    }

    private void sendEmail(Fresher fresher) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String MARK_COLUMN = "        <td align=\"center\"> %s </td>%n";
        String subject = "Fresher mark report";
        String content = "<p>Hello, " + fresher.getName() + "</p>" +
                "<p>I want to send you the marks of assignments:</p>" +
                "<table border=\"1\">\n" +
                "      <tr>\n" +
                "        <td>Mark1</td>\n" +
                "        <td>Mark2</td>\n" +
                "        <td>Mark3</td>\n" +
                "        <td>Mark avg</td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                String.format(MARK_COLUMN, fresher.getMark1()) +
                String.format(MARK_COLUMN, fresher.getMark2()) +
                String.format(MARK_COLUMN, fresher.getMark3()) +
                String.format(MARK_COLUMN, fresher.getMarkAvg()) +
                "      </tr>\n" +
                "    </table><br>" +
                "<p>Hope you study well!</p>";

        helper.setFrom(EMAIL_TO_SEND, ADMINISTRATION);
        helper.setTo(fresher.getEmail());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

}
