/**
 * Copyright (c) 2012, 2018, Werner Keil and others by the @author tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.moneta.format;

import static java.util.Locale.FRANCE;
import static org.javamoney.moneta.format.CurrencyStyle.CODE;
import static org.testng.Assert.assertEquals;

import java.util.Locale;

import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQuery;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.Money;
import org.testng.annotations.Test;

public class MonetaryFormatsTest {
    private static final Locale DANISH = new Locale("da");
    private static final Locale BULGARIA = new Locale("bg", "BG");
    public static final Locale INDIA = new Locale("en, IN");

    @Test(enabled = false)
    public void testParse_DKK_da() {
        AmountFormatQuery formatQuery = AmountFormatQueryBuilder.of(DANISH).set(CODE).build();
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(formatQuery);
        MonetaryAmount amountOk = format.parse("123,01 DKK");
        MonetaryAmount amountKo = format.parse("14 000,12 DKK");
        assertEquals(amountOk.getNumber().doubleValueExact(), 123.01);
        assertEquals(amountKo.getNumber().doubleValueExact(), 14000.12);
    }

    @Test(enabled = false) // FIXME this seems totally wrong, expected [14 000,12 EUR] but found [14 000,12 EUR] why does it work for Lev but not Euro?
    public void testFormat_EUR_fr_FR() {
        AmountFormatQuery formatQuery = AmountFormatQueryBuilder.of(FRANCE).set(CODE).build();
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(formatQuery);
        MonetaryAmount amount = Money.of(14000.12, "EUR");
        String formatted = format.format(amount);
        assertEquals(formatted, "14 000,12 EUR");
    }

    @Test(enabled = false)
    public void testParse_EUR_fr_FR() {
        AmountFormatQuery formatQuery = AmountFormatQueryBuilder.of(FRANCE).set(CODE).build();
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(formatQuery);
        MonetaryAmount amountOk = format.parse("123,01 EUR");
        MonetaryAmount amountKo = format.parse("14 000,12 EUR");
        assertEquals(amountOk.getNumber().doubleValueExact(), 123.01);
        assertEquals(amountKo.getNumber().doubleValueExact(), 14000.12);
    }

    @Test(enabled = false)
    public void testParse_BGN_bg_BG() {
        AmountFormatQuery formatQuery = AmountFormatQueryBuilder.of(BULGARIA).set(CODE).build();
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(formatQuery);
        MonetaryAmount amountOk = format.parse("123,01 BGN");
        MonetaryAmount amountKo = format.parse("14 000,12 BGN");
        assertEquals(amountOk.getNumber().doubleValueExact(), 123.01);
        assertEquals(amountKo.getNumber().doubleValueExact(), 14000.12);
    }

    @Test
    public void testFormat_BGN_bg_BG() {
        AmountFormatQuery formatQuery = AmountFormatQueryBuilder.of(BULGARIA).set(CODE).build();
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(formatQuery);
        MonetaryAmount amount = Money.of(14000.12, "BGN");
        String formatted = format.format(amount);
        assertEquals(formatted, "14 000,12 BGN");
    }

    /**
     * Test related to parsing and formatting Rupees for India.
     * https://github.com/JavaMoney/jsr354-ri/issues/275
     */
    @Test(enabled = false)
    public void testFormat_INR_en_IN() {
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(INDIA);
        Money money = Money.of(67890000000000L, "INR");
        String expectedFormattedString = "INR 6,78,90,00,00,00,000.00";
        String formatted = format.format(money);
        assertEquals(formatted, expectedFormattedString);
        assertEquals(money, Money.parse(expectedFormattedString, format));
    }
}
