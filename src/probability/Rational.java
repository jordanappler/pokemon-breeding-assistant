/**************************************************************************
 * Copyright 2013 Jordan Appler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************************************************************/
package probability;

import java.text.DecimalFormat;

public class Rational
{

    private long num, den;

    public Rational(long numIn, long denIn)
    {
        this.num = numIn;
        this.den = denIn;
    }

    public long getNum()
    {
        return num;
    }

    public long getDen()
    {
        return den;
    }

    public double toDouble()
    {
        return (double) this.num / this.den;
    }

    @Override
    public String toString()
    {
        return this.num + "/" + this.den;
    }

    public String toPercent()
    {
        return new DecimalFormat("###.###").format(100 * this.toDouble()).toString();
    }

    public Rational reciprocal()
    {
        Rational x = new Rational(this.den, this.num);
        return x;
    }

    public Rational multiply(Rational y)
    {
        Rational z = new Rational(this.num * y.num, this.den * y.den);
        z.factor();
        return z;
    }

    public Rational divide(Rational y)
    {
        Rational z = new Rational(this.num * y.den, this.den * y.num);
        z.factor();
        return z;
    }

    public Rational add(Rational y)
    {
        Rational z = new Rational((this.num * y.den + y.num * this.den), this.den * y.den);
        z.factor();
        return z;
    }

    public Rational subtract(Rational y)
    {
        Rational z = new Rational((this.num * y.den - y.num * this.den), this.den * y.den);
        z.factor();
        return z;
    }

    private void factor()
    {
        long factor = greatestCommonFactor(this.getNum(), this.getDen());
        this.num = this.num / factor;
        this.den = this.den / factor;
    }

    private static long greatestCommonFactor(long a, long b)
    {
        if (b == 0)
        {
            return a;
        } else
        {
            return greatestCommonFactor(b, a % b);
        }
    }
}
