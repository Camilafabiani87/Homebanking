//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.utils.CardUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//@SpringBootTest
//public class CardUtilsTests {
//    @Test
//
//    public void cardNumberIsCreated(){
//
//        String cardNumber = CardUtils.getNumberCard();
//
//        assertThat(cardNumber,is(not(emptyOrNullString())));
//
//    }
//    @Test
//
//    public void cardNumberIsString() {
//
//        String cardNumber = CardUtils.getNumberCard();
//
//        assertThat(cardNumber, isA(String.class));
//    }
//    @Test
//
//    public void cvvNumberIsInt(){
//
//        int numberCvv = CardUtils.getNumberCvv();
//
//        assertThat(numberCvv,isA(int.class));
//
//    }
//    @Test
//
//    public void cvvNumberIsNotNull(){
//
//        int numberCvv = CardUtils.getNumberCvv();
//
//        assertThat(numberCvv,notNullValue(int.class));
//
//    }
//}
