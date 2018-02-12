package com.footinit.ibasemvp.utils;

import android.app.ProgressDialog;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by Abhijit on 05-02-2018.
 */


public class CommonUtilsTest {

    private static final String CORRECT_INPUT = "naikabhiit@gmail.com";
    private static final String NO_AT_THE_RATE = "naikabhiitgmail.com";
    private static final String NO_DOMAIN = "naikabhiit@gmail";
    private static final String NO_AT_THE_RATE_AND_DOMAIN = "naikabhiitgmail";
    private static final String UN_ALLOWED_SPECIAL_CHARACTERS1 = "naik$abhiit@gmail.com";
    private static final String UN_ALLOWED_SPECIAL_CHARACTERS2 = "naik#abhiit@gmail.com";
    private static final String UN_ALLOWED_SPECIAL_CHARACTERS3 = "naik%abhiit@gmail.com";


    @Test
    public void isEmailValid_NoAtTheRate_False() {
        assertFalse(CommonUtils.isEmailValid(NO_AT_THE_RATE));
    }

    @Test
    public void isEmailValid_NoDomain_False() {
        assertFalse(CommonUtils.isEmailValid(NO_DOMAIN));
    }

    @Test
    public void isEmailValid_NoAtTheRateAndDomain_False() {
        assertFalse(CommonUtils.isEmailValid(NO_AT_THE_RATE_AND_DOMAIN));
    }

    @Test
    public void isEmailValid_UnAllowedSpecialCharacters1_False() {
        assertFalse(CommonUtils.isEmailValid(UN_ALLOWED_SPECIAL_CHARACTERS1));
    }

    @Test
    public void isEmailValid_UnAllowedSpecialCharacters2_False() {
        assertFalse(CommonUtils.isEmailValid(UN_ALLOWED_SPECIAL_CHARACTERS2));
    }

    @Test
    public void isEmailValid_UnAllowedSpecialCharacters3_False() {
        assertFalse(CommonUtils.isEmailValid(UN_ALLOWED_SPECIAL_CHARACTERS3));
    }

    @Test
    public void isEmailValid_CorrectInput_True() {
        assertTrue(CommonUtils.isEmailValid(CORRECT_INPUT));
    }


    @Test
    public void getNegativeLong_PositiveInt_NegativeLong() {
        assertThat(CommonUtils.getNegativeLong(2), is(-2l));
    }

    @Test
    public void getNegativeLong_NegativeInt_PositiveLong() {
        assertThat(CommonUtils.getNegativeLong(-2), is(2l));
    }
}