package com.mycompany.unicafe;

import org.junit.Before;

import static org.junit.Assert.*;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kassapaate;
    Maksukortti maksukortti;

    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        //ladataan 100 euroa
        maksukortti = new Maksukortti(10000);
        //
    }

    @Test
    public void luotuKassapaateOlemassa() {
        assertEquals(kassapaate.kassassaRahaa(), 100000);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 0);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 0);
    }

    @Test
    public void kateisostoEdullinenKassassaRiittavastiRahaa() {
        kassapaate.syoEdullisesti(240);
        assertEquals(kassapaate.kassassaRahaa(), (100000 + 240));
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 1);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 0);

    }

    @Test
    public void kateisostoEdullinenKassassaRiittavastiRahaaVaihtorahat() {
        kassapaate.syoEdullisesti(1000);
        assertEquals(kassapaate.kassassaRahaa(), (100000 + 240));
        assertEquals(kassapaate.syoEdullisesti(1000), 760);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 2);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 0);
    }

    @Test
    public void kateisostoEdullinenKassassaRiittavastiRahaaMaksuLiianPieni() {
        kassapaate.syoEdullisesti(100);
        assertEquals(kassapaate.syoEdullisesti(100), 100);
        assertEquals(kassapaate.kassassaRahaa(), 100000);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 0);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 0);
    }

    @Test
    public void kateisostoMaukasKassassaRiittavastiRahaa() {
        kassapaate.syoMaukkaasti(400);
        assertEquals(kassapaate.kassassaRahaa(), (100000 + 400));
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 0);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 1);

    }

    @Test
    public void kateisostoMaukasKassassaRiittavastiRahaaVaihtorahat() {
        kassapaate.syoMaukkaasti(1000);
        assertEquals(kassapaate.kassassaRahaa(), (100000 + 400));
        assertEquals(kassapaate.syoMaukkaasti(1000), (1000 - 400));
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 0);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 2);
    }

    @Test
    public void kateisostoMaukasKassassaRiittavastiRahaaMaksuLiianPieni() {
        kassapaate.syoEdullisesti(100);
        assertEquals(kassapaate.syoMaukkaasti(100), 100);
        assertEquals(kassapaate.kassassaRahaa(), 100000);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 0);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 0);
    }

    @Test
    public void korttiostoEdullinenKassassaRiittavastiRahaa() {

        assertEquals(kassapaate.syoEdullisesti(maksukortti), true);
        assertEquals(kassapaate.kassassaRahaa(), 100000);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 1);
        assertEquals(maksukortti.saldo(), 10000 - 240);
    }

    @Test
    public void korttiostoEdullinenKortillaEiRiittavastiRahaa() {

        maksukortti.otaRahaa(9999);
        //kortilla rahaa 1 sentti
        assertEquals(kassapaate.syoEdullisesti(maksukortti), false);
        assertEquals(kassapaate.kassassaRahaa(), 100000);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 0);
        assertEquals(maksukortti.saldo(), 1);
    }

    @Test
    public void kortilleLadataanRahaaKassaKasvaa() {
        //lisataan 10e

        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(maksukortti.saldo(), 11000);
        assertEquals(kassapaate.kassassaRahaa(), 101000);
    }

    @Test
    public void kortilleLadataanRahaaNegatiivinenSumma() {
        kassapaate.lataaRahaaKortille(maksukortti, -1);
        assertEquals(maksukortti.saldo(), 10000);
        assertEquals(kassapaate.kassassaRahaa(), 100000);
    }

    @Test
    public void korttiostoMaukasKassassaRiittavastiRahaa() {
        assertEquals(kassapaate.syoMaukkaasti(maksukortti), true);
        assertEquals(kassapaate.kassassaRahaa(), 100000);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 1);
        assertEquals(maksukortti.saldo(), 10000 - 400);
    }

    @Test
    public void korttiostoMaukasKortillaEiRiittavastiRahaa() {

        maksukortti.otaRahaa(9999);
        //kortilla rahaa 1 sentti
        assertEquals(kassapaate.syoMaukkaasti(maksukortti), false);
        assertEquals(kassapaate.kassassaRahaa(), 100000);

        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 0);
        assertEquals(maksukortti.saldo(), 1);

    }
}
