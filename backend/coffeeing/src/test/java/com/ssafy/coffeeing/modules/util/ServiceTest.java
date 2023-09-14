package com.ssafy.coffeeing.modules.util;

import com.ssafy.coffeeing.dummy.Dummy;
import com.ssafy.coffeeing.dummy.MemberTestDummy;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private Dummy dummy;

    protected Member generalMember;

    protected Member beforeResearchMember;

    @BeforeEach
    void setUp() {
        generalMember = memberRepository.save(MemberTestDummy
                .createGeneralMember("testNickname", "test123", "test1@test.com"));
        beforeResearchMember = memberRepository.save(MemberTestDummy
                .createBeforeResearchMember("paul", "testPassword", "zase@naver.com"));
    }


    @AfterEach
    void clearDatabase() {
        //databaseCleaner.clear();
    }
}
