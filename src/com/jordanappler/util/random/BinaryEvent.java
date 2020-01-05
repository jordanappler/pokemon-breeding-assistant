/**************************************************************************
 * Copyright 2019 Jordan Appler
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
package com.jordanappler.util.random;

import java.util.BitSet;

import com.jordanappler.util.Rational;

public abstract class BinaryEvent
{
    protected BitSet state;
    protected BitSet resolved;

    protected Rational probability;

    protected BinaryEvent()
    {
    }

    public BinaryEvent(int size)
    {
        state = new BitSet(size);
        resolved = new BitSet(size);

        probability = new Rational(1, 1);
    }

    public BinaryEvent(BinaryEvent toCopy)
    {
        state = (BitSet) toCopy.state.clone();
        resolved = (BitSet) toCopy.resolved.clone();
        probability = new Rational(toCopy.probability.getNum(), toCopy.probability.getDen());
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        BinaryEvent toCompare = (BinaryEvent) object;
        return state.equals(toCompare.state) && resolved.equals(toCompare.resolved)
                && probability.equals(toCompare.probability);
    }

    public void setBit(int bitIndex, boolean value, Rational relativeProbability)
    {
        state.set(bitIndex, value);
        resolved.set(bitIndex);

        probability = probability.multiply(relativeProbability);
    }

    public void setBit(int bitIndex, boolean value)
    {
        state.set(bitIndex, value);
        resolved.set(bitIndex);
    }

    public Rational getProbability()
    {
        return probability;
    }

    public abstract BinaryEvent[] refine();

    public abstract Boolean satisfiesGoal();
}
