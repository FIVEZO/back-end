package com.sparta.toogo.global.email.service;

import com.sparta.toogo.global.email.dto.EmailResponseDto;
import com.sparta.toogo.global.email.exception.EmailException;
import com.sparta.toogo.global.redis.service.RedisService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.sparta.toogo.global.enums.ErrorCode.INCORRECT_CODE;
import static com.sparta.toogo.global.enums.ErrorCode.NOT_FOUND_EMAIL;
import static com.sparta.toogo.global.enums.SuccessCode.EMAIL_VERIFICATION_SENT;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    JavaMailSender emailSender;

    @Value("${AdminMail.id}")
    private String ADMIN_EMAIL;
    private final String ADMIN_NAME = "OE";
    private final RedisService redisService;

    public static final String code = createKey();

    private MimeMessage createMessage(String email) throws Exception {
        System.out.println("보내는 대상 : " + email);
        System.out.println("인증 번호 : " + code);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email);//보내는 대상
        message.setSubject(ADMIN_NAME + " 이메일 인증");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1> 안녕하세요 " + ADMIN_NAME + "입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다.<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:green;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += code + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress(ADMIN_EMAIL, ADMIN_NAME)); // 보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤
            switch (index) {
                //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                case 0 -> key.append((char) ((int) (rnd.nextInt(26)) + 97));
                //  A~Z
                case 1 -> key.append((char) ((int) (rnd.nextInt(26)) + 65));
                // 0~9
                case 2 -> key.append((rnd.nextInt(10)));
            }
        }
        return key.toString();
    }

    // 메세지 전송
    public EmailResponseDto sendSimpleMessage(String email) throws Exception {
        MimeMessage message = createMessage(email);
        try {
            redisService.setCodeExpire(code, email, 60 * 5L); // 유효시간
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
            throw new EmailException(INCORRECT_CODE);
        }
        return new EmailResponseDto(EMAIL_VERIFICATION_SENT);
    }

    // 코드 확인
    public Boolean checkCode(String key) {
        String email = redisService.getCode(key);
        if (email == null) {
            throw new EmailException(NOT_FOUND_EMAIL);
        }
        redisService.deleteCode(code);
        return true;
    }
}