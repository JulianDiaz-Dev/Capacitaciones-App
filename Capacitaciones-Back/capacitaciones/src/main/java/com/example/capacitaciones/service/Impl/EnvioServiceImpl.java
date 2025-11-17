package com.example.capacitaciones.service.Impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.capacitaciones.service.EnvioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnvioServiceImpl implements EnvioService {
    private final JavaMailSender mailSender;

    public void enviarCorreo(String to, String subject, String text) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(to);
        mensaje.setSubject(subject);
        mensaje.setText(text);
        mailSender.send(mensaje);
    }
}
