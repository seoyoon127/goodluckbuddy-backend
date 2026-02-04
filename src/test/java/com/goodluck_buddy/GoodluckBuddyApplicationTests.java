package com.goodluck_buddy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        properties = {
                // JWT
                "jwt.token.secretKey=testtest-secrettest-secrettest-secrettest-secrettest-secret",
                "jwt.token.expiration.access=3600000",
                "jwt.token.expiration.refresh=86400000",

                // OAuth (혹시라도 남아있을 경우 대비)
                "spring.security.oauth2.client.registration.google.client-id=test",
                "spring.security.oauth2.client.registration.google.client-secret=test",

                // DB
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.username=sa",
                "spring.datasource.password="
        }
)
@ActiveProfiles("test")
class GoodluckBuddyApplicationTests {

    @Test
    void contextLoads() {
    }

}
