package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import com.github.geekuniversity_java_215.cmsbackend.authserver.entities.BlacklistedToken;
import com.github.geekuniversity_java_215.cmsbackend.authserver.repository.BlacklistedTokenRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BlacklistTokenService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public BlacklistTokenService(BlacklistedTokenRepository blacklistedTokenRepository) {
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }

    /**
     * Add access_token to blacklist
     * @param token AccessToken
     */
    public void blacklist(AccessToken token) {
        blacklistedTokenRepository.save(new BlacklistedToken(token.getId(), token.getExpiredAt()));
    }

    /**
     * Get all blacklisted tokens from position to now
     */
    public Map<Long,Long> getFrom(Long from) {
       
       //ToDo: replace by projection ?

       return blacklistedTokenRepository.findByIdGreaterThanEqual(from).stream()
               .collect(Collectors.toMap(BlacklistedToken::getId, BlacklistedToken::getTokenId));
    }

    public void vacuum() {
        blacklistedTokenRepository.vacuum();
    }

}




