package com.github.geekuniversity_java_215.cmsbackend.services;


public interface MailService {
    void sendPaymentSuccess();

    //todo endRegConfirmation() - это болванка для отправки уведомлений при регистрации нового клиента/курьера
    //навход необходимо подавать сущность нового пользователя
    void sendRegConfirmation(String client);
}