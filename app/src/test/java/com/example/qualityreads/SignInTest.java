package com.example.qualityreads;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SignInTest {

    private SignIn signin;
    boolean email = true;
    boolean password = true;

    @Before
    public void setUp() throws Exception {
        signin = new SignIn();
    }

    @Test
    public void validEmail() {
        email = signin.validEmail();
        assertEquals(false, email);
    }

    @Test
    public void validPassword() {
        password = signin.validPassword();
        assertEquals(false, password);
    }
}