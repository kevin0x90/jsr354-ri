/*
  Copyright (c) 2012, 2020, Anatole Tresch, Werner Keil and others by the @author tag.

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy of
  the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations under
  the License.
 */
package org.javamoney.moneta.spi;

import java.math.RoundingMode;

import javax.money.*;

import org.javamoney.moneta.FastMoney;

/**
 * Implementation of {@link javax.money.MonetaryAmountFactory} creating instances of {@link FastMoney}.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class FastMoneyAmountFactory extends AbstractAmountFactory<FastMoney> {

    static final MonetaryContext DEFAULT_CONTEXT =
            MonetaryContextBuilder.of(FastMoney.class).setPrecision(19).setMaxScale(5).setFixedScale(true)
                    .set(RoundingMode.HALF_EVEN).build();
    static final MonetaryContext MAX_CONTEXT =
            MonetaryContextBuilder.of(FastMoney.class).setPrecision(19).setMaxScale(5).setFixedScale(true)
                    .set(RoundingMode.HALF_EVEN).build();

    @Override
    protected FastMoney create(Number number, CurrencyUnit currency, MonetaryContext monetaryContext) {
        return FastMoney.of(number, currency);
    }

    @Override
    public Class<FastMoney> getAmountType() {
        return FastMoney.class;
    }

    @Override
    public NumberValue getMaxNumber() {
        return FastMoney.MAX_VALUE.getNumber();
    }

    @Override
    public NumberValue getMinNumber() {
        return FastMoney.MIN_VALUE.getNumber();
    }

    @Override
    protected MonetaryContext loadDefaultMonetaryContext() {
        return DEFAULT_CONTEXT;
    }

    @Override
    protected MonetaryContext loadMaxMonetaryContext() {
        return MAX_CONTEXT;
    }

}
