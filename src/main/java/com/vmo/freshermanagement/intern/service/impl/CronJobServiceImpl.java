package com.vmo.freshermanagement.intern.service.impl;

import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.CronjobService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.vmo.freshermanagement.intern.constant.SecurityConstant.*;

@Service
public class CronJobServiceImpl implements CronjobService {

    private FresherRepository fresherRepository;
    private JavaMailSender javaMailSender;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

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
                "        <td align=\"center\">" + fresher.getMark1() +"</td>\n" +
                "        <td align=\"center\">" + fresher.getMark2() +"</td>\n" +
                "        <td align=\"center\">" + fresher.getMark3() +"</td>\n" +
                "        <td align=\"center\">" + fresher.getMarkAvg() +"</td>\n" +
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
