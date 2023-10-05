package com.ssafy.coffeeing.modules.util;

import com.ssafy.coffeeing.dummy.Dummy;
import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    protected EntityManager em;

    @MockBean
    protected SecurityContextUtils securityContextUtils;

    @MockBean
    private Dummy dummy;

    protected Member generalMember;

    protected Member beforeResearchMember;

    protected static final Integer REVIEW_PAGE_SIZE = 6;
    protected static final Integer BOOKMARK_PAGE_SIZE = 8;

    @BeforeEach
    void setUp() {
        redisTemplate.delete("feedLike");
        generalMember = memberRepository.save(MemberTestDummy
                .createGeneralMember("testNickname", "{noop}test123", "test1@test.com"));
        beforeResearchMember = memberRepository.save(MemberTestDummy
                .createBeforeResearchMember("paul", "{noop}testPassword", "zase@naver.com"));
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @AfterEach
    void clearDatabase() {
        //databaseCleaner.clear();
    }
}
