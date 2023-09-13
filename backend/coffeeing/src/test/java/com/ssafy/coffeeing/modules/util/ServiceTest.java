package com.ssafy.coffeeing.modules.util;

import com.ssafy.coffeeing.dummy.Dummy;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private ProductMapper productMapper;

    @MockBean
    private Dummy dummy;

    @AfterEach
    void clearDatabase() {
//        databaseCleaner.clear();
    }
}
