package de.janka.capstonedrazen.config;

import com.mastercard.developer.interceptors.OpenFeignOAuth1Interceptor;
import com.mastercard.developer.utils.AuthenticationUtils;
import de.janka.capstonedrazen.rest.ImmoScoutAPI;
import de.janka.capstonedrazen.rest.ImmoScoutClient;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Configuration
public class ImmoScoutClientConfig {

    @Value("classpath:/sender_keystore.p12")
    private Resource pkcs12KeyFile;

    @Bean
    public ImmoScoutAPI getGithubAPI() throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {
        String url = "https://rest.sandbox-immobilienscout24.de/restapi/api";

        File file = FileUtils.getFile(pkcs12KeyFile.getFile());
        PrivateKey privateKey = AuthenticationUtils.loadSigningKey(file.getPath(), "immoScout", "VcZDUQQJAyy2ub25");
        RequestInterceptor interceptor = new OpenFeignOAuth1Interceptor("bootCampCapstoneKey", privateKey,url);

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .requestInterceptor(interceptor)
                .logger(new Slf4jLogger(ImmoScoutClient.class))
                .logLevel(Logger.Level.FULL)
                .target(ImmoScoutAPI.class, url);
    }



}
