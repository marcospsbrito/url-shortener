package com.github.marcospsbrito.urlshortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.LinkedHashSet;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class KeyGeneratorTest {

    @InjectMocks
    private KeyGenerator generator;


    @Test
    public void generate() {
        String key = generator.generate();

        assertIsValid(key);
    }

    @Test
    public void generateHundredThousandValidWithoutColision() {
        HashSet<String> set = new LinkedHashSet<String>();
        for(int i=0; i<100000;i++){
            String key = generator.generate();
            set.add(key);

            assertIsValid(key);
        }
        assertThat(set.size()).isEqualTo(100000);
    }

    private void assertIsValid(String key) {
        assertThat(key.length()).isGreaterThanOrEqualTo(5);
        assertThat(key.length()).isLessThanOrEqualTo(36);
        assertThat(key).containsPattern("[A-Za-z0-9]*");
    }
}