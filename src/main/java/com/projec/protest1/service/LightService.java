package com.projec.protest1.service;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class LightService {

    private static final String QUEUE_NAME = "change_request_queue";
    private static final String QUEUE_ADDRESS = "192.168.45.36";
    private static final String ID = "admin";
    private static final String PASSWD = "admin";

    static final String LIGHT_STATE_URL = "http://192.168.45.36:18080/ledtime";


    public void sendMessage(String message) {
        ConnectionFactory factory = initConFactory();

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, makeArguments());
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public String requestLedState() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        return Unirest.get(LIGHT_STATE_URL).asString().getBody();
    }

    private Map<String, Object> makeArguments() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 1000);
        return arguments;
    }

    private ConnectionFactory initConFactory() {

        // TODO: keytool로 인증서를 저장할 때, 사용한 비밀번호임
        //  실제로 서비스를 한다면, 업로드에 주의해야 하는 부분!
        //  현재는 개발 용도이기 때문에 오픈.
        char[] trustPassphrase = "youswim".toCharArray();
        KeyStore tks = null;
        try {
            tks = KeyStore.getInstance("JKS");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        try {
            tks.load(new FileInputStream("certification/rabbitstore"), trustPassphrase);    //keytool 명령어로 keystore만들때 입력했던 pw
//            tks.load(new FileInputStream("certification/teststore"), trustPassphrase);    //keytool 명령어로 keystore만들때 입력했던 pw
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }

        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance("SunX509");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            tmf.init(tks);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }

        SSLContext c = null;
        try {
            c = SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        try {
            c.init(null, tmf.getTrustManagers(), null);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_ADDRESS);
        factory.setPort(ConnectionFactory.DEFAULT_AMQP_OVER_SSL_PORT);
        factory.setUsername(ID);
        factory.setPassword(PASSWD);
        factory.useSslProtocol(c);
        return factory;
    }
}
