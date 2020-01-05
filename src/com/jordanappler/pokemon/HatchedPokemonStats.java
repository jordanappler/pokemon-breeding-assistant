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
package com.jordanappler.pokemon;

import java.util.BitSet;

import com.jordanappler.util.Rational;
import com.jordanappler.util.random.BinaryEvent;

public class HatchedPokemonStats extends BinaryEvent
{
    public enum IV
    {
        HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED
    }

    // NOTE power items have same ordinal value as their associated IVs
    public enum Item
    {
        POWER_WEIGHT, POWER_BRACER, POWER_BELT, POWER_LENS, POWER_BAND, POWER_ANKLET, DESTINY_KNOT, EVERSTONE, NOTHING
    }

    public static Item[] itemValues = Item.values();

    protected BitSet motherIVs;
    protected Item motherItem;

    protected BitSet fatherIVs;
    protected Item fatherItem;

    protected BitSet goal;

    public HatchedPokemonStats(BitSet motherIVs, Item motherItem, BitSet fatherIVs, Item fatherItem, BitSet goal)
    {
        super(6);

        this.motherIVs = motherIVs;
        this.motherItem = motherItem;

        this.fatherIVs = fatherIVs;
        this.fatherItem = fatherItem;

        this.goal = goal;
    }

    public HatchedPokemonStats(HatchedPokemonStats toCopy)
    {
        super(toCopy);

        motherIVs = toCopy.motherIVs;
        motherItem = toCopy.motherItem;

        fatherIVs = toCopy.fatherIVs;
        fatherItem = toCopy.fatherItem;

        goal = toCopy.goal;
    }

    protected static boolean isPowerItem(Item item)
    {
        return item.ordinal() < 6;
    }

    protected boolean hasDestinyKnot()
    {
        return (motherItem == Item.DESTINY_KNOT || fatherItem == Item.DESTINY_KNOT);
    }

    @Override
    public BinaryEvent[] refine()
    {
        switch (resolved.cardinality())
        {
        case 0:
            return initialRefinement();
        case 1:
        case 2:
            return inheritanceRefinement();
        case 3:
        case 4:
            if (hasDestinyKnot())
                return inheritanceRefinement();
            else
                return randomAssignmentRefinement();
        case 5:
            return randomAssignmentRefinement();
        default:
            System.out.println("Pokemon IVs fully resolved");
            return new HatchedPokemonStats[0];
        }
    }

    @Override
    public Boolean satisfiesGoal()
    {
        boolean satisfiesConstraints = true;
        boolean failsConstraints = false;

        for (int i = 0; i < 6; i++)
        {
            if (goal.get(i))
            {
                if (resolved.get(i))
                {
                    if (!state.get(i))
                    {
                        satisfiesConstraints = false;
                        failsConstraints = true;
                        break;
                    }
                } else
                    satisfiesConstraints = false;
            }
        }

        if (failsConstraints)
            return new Boolean(false);
        else if (satisfiesConstraints)
            return new Boolean(true);
        else
            return null;
    }

    protected BinaryEvent[] initialRefinement()
    {
        BinaryEvent[] states;

        if (isPowerItem(motherItem))
        {
            states = new HatchedPokemonStats[1];
            HatchedPokemonStats resolution = new HatchedPokemonStats(this);
            resolution.setBit(motherItem.ordinal(), motherIVs.get(motherItem.ordinal()));
            states[0] = resolution;
        } else if (isPowerItem(fatherItem))
        {
            states = new HatchedPokemonStats[1];
            HatchedPokemonStats resolution = new HatchedPokemonStats(this);
            resolution.setBit(fatherItem.ordinal(), fatherIVs.get(fatherItem.ordinal()));
            states[0] = resolution;
        } else
        {
            states = inheritanceRefinement();
        }

        return states;
    }

    protected BinaryEvent[] inheritanceRefinement()
    {
        int numUnresolvedIVs = 6 - resolved.cardinality();
        int numResolutions = 2 * numUnresolvedIVs;
        BinaryEvent[] states = new BinaryEvent[numResolutions];
        Rational stateLikelihood = new Rational(1, numResolutions);
        int stateIndex = 0;
        for (int i = 0; i < 6; i++)
        {
            if (!resolved.get(i))
            {
                HatchedPokemonStats state = new HatchedPokemonStats(this);
                state.setBit(i, motherIVs.get(i), stateLikelihood);
                states[stateIndex++] = state;

                state = new HatchedPokemonStats(this);
                state.setBit(i, fatherIVs.get(i), stateLikelihood);
                states[stateIndex++] = state;
            }
        }

        return states;
    }

    protected BinaryEvent[] randomAssignmentRefinement()
    {
        int i = 0;
        while (resolved.get(i))
            i++;
        HatchedPokemonStats[] states = new HatchedPokemonStats[2];

        HatchedPokemonStats state = new HatchedPokemonStats(this);
        state.setBit(i, true, new Rational(1, 32));
        states[0] = state;

        state = new HatchedPokemonStats(this);
        state.setBit(i, false, new Rational(31, 32));
        states[1] = state;

        return states;
    }
}
