package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
        //ladaan 10e
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void kortilleLadattuRahaaOikein() {
        kortti.lataaRahaa(1000);
        assertEquals(kortti.toString(), "saldo: 20.0");
    }

    @Test
    public void kortiltaOtetaanRahaaSaldoOikeinTasaRaha() {
        assertTrue(kortti.otaRahaa(1000) == true);
        assertEquals(kortti.toString(), "saldo: 0.0");

    }

    @Test
    public void kortiltaOtetetaanRahaaSaldoEiVahene() {
        assertTrue(kortti.otaRahaa(2000) == false);
        assertEquals(kortti.toString(), "saldo: 10.0");
    }

}
