package com.felipeAlves.ecommerce_api.mailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

@Service
public class EmailService {

	@Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;
    
    public Response enviarEmail(String para, String assunto, String conteudo) {
        Email from = new Email("arnaud.felipe96@gmail.com"); // Email do remetente
        Email to = new Email(para); // Email do destinatário
        Content content = new Content("text/plain", conteudo);
        Mail mail = new Mail(from, assunto, to, content);

        SendGrid sendGrid = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            // Exibe informações para depuração
            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }
}	