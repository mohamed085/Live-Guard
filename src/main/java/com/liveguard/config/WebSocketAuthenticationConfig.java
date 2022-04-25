package com.liveguard.config;

import com.auth0.jwt.JWT;
import com.liveguard.domain.LiveGuardUserDetails;
import com.liveguard.domain.User;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.UserRepository;
import com.liveguard.util.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer {

    private final UserRepository userRepository;

    public WebSocketAuthenticationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("Authorization");
                    log.debug("WebSocketAuthenticationConfig | Authorization: {}", authorization);

                    String token = authorization.get(0).split(" ")[1];
                    log.debug("WebSocketAuthenticationConfig | token: " + token);

                    String email = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                            .build()
                            .verify(token)
                            .getSubject();

                    User user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new BusinessException("This email not exist", HttpStatus.BAD_REQUEST));

                    log.debug("WebSocketAuthenticationConfig | user: " + user.getEmail());

                    LiveGuardUserDetails userDetails = new LiveGuardUserDetails(user);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(email,null, userDetails.getAuthorities());

//                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                return message;


            }
        });
    }
}
