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

public class Instance
{

    int statsRemaining;
    int parentStatsRemaining;
    Instance[] subPaths;
    Rational pathChance;
    Rational sumPathChance;
    Pokemon child;
    Pokemon statsDecided;

    public Instance()
    {
        /*
         * Default start: 100% chance so far 6 stats undecided 3 stats from parents
         * Pokemon with no perfect stats Pokemon showing no chance of perfect stats so
         * far Pokemon with the ideal stats
         */
        this(new Rational(1, 1), 6, 3, new Pokemon(), new Pokemon(), new Pokemon(), new Pokemon(), new Pokemon());
    }

    public Instance(Rational pathChance, int statsRemaining, int parentStatsRemaining, Pokemon child,
            Pokemon statsDecided, Pokemon mom, Pokemon dad, Pokemon ideal)
    {
        this.statsRemaining = statsRemaining;
        this.parentStatsRemaining = parentStatsRemaining;
        this.subPaths = null;
        this.pathChance = pathChance;
        this.child = child;
        this.statsDecided = statsDecided;
        // come up with dead end cases:
        // cannot possibly reach ideal - make sumPathChance 0
        // already have reached ideal - make sumPathChance 1
        // do nothing else
        if (child.statsFromIdeal(ideal) - statsRemaining > 0)
        {
            this.sumPathChance = new Rational(0, 1);
        } else if (child.statsFromIdeal(ideal) == 0)
        {
            this.sumPathChance = new Rational(1, 1);
        } else if (parentStatsRemaining > 0)
        {
            this.subPaths = new Instance[2 * statsRemaining];
            int indexDefined = 0;
            if (!statsDecided.getHP())
            {
                // if mom and dad are same, put in same group and double pathChance -- hopefully
                // helps eliminate unncessary computation
                // if different do as normal
                if (mom.getHP() == dad.getHP())
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, statsRemaining), statsRemaining - 1,
                            parentStatsRemaining - 1, new Pokemon(child, Pokemon.HP, mom.getHP()),
                            new Pokemon(statsDecided, Pokemon.HP, true), mom, dad, ideal);
                } else
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1, new Pokemon(child, Pokemon.HP, mom.getHP()),
                            new Pokemon(statsDecided, Pokemon.HP, true), mom, dad, ideal);
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1, new Pokemon(child, Pokemon.HP, dad.getHP()),
                            new Pokemon(statsDecided, Pokemon.HP, true), mom, dad, ideal);
                }
            }
            if (!statsDecided.getAttack())
            {
                // if mom and dad are same, put in same group and double pathChance -- hopefully
                // helps eliminate unncessary computation
                // if different do as normal
                if (mom.getAttack() == dad.getAttack())
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, statsRemaining), statsRemaining - 1,
                            parentStatsRemaining - 1, new Pokemon(child, Pokemon.ATTACK, mom.getAttack()),
                            new Pokemon(statsDecided, Pokemon.ATTACK, true), mom, dad, ideal);
                } else
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.ATTACK, mom.getAttack()),
                            new Pokemon(statsDecided, Pokemon.ATTACK, true), mom, dad, ideal);
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.ATTACK, dad.getAttack()),
                            new Pokemon(statsDecided, Pokemon.ATTACK, true), mom, dad, ideal);
                }
            }
            if (!statsDecided.getDefense())
            {
                // if mom and dad are same, put in same group and double pathChance -- hopefully
                // helps eliminate unncessary computation
                // if different do as normal
                if (mom.getDefense() == dad.getDefense())
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, statsRemaining), statsRemaining - 1,
                            parentStatsRemaining - 1, new Pokemon(child, Pokemon.DEFENSE, mom.getDefense()),
                            new Pokemon(statsDecided, Pokemon.DEFENSE, true), mom, dad, ideal);
                } else
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.DEFENSE, mom.getDefense()),
                            new Pokemon(statsDecided, Pokemon.DEFENSE, true), mom, dad, ideal);
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.DEFENSE, dad.getDefense()),
                            new Pokemon(statsDecided, Pokemon.DEFENSE, true), mom, dad, ideal);
                }
            }
            if (!statsDecided.getSpAtt())
            {
                // if mom and dad are same, put in same group and double pathChance -- hopefully
                // helps eliminate unncessary computation
                // if different do as normal
                if (mom.getSpAtt() == dad.getSpAtt())
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, statsRemaining), statsRemaining - 1,
                            parentStatsRemaining - 1, new Pokemon(child, Pokemon.SPATT, mom.getSpAtt()),
                            new Pokemon(statsDecided, Pokemon.SPATT, true), mom, dad, ideal);
                } else
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.SPATT, mom.getSpAtt()),
                            new Pokemon(statsDecided, Pokemon.SPATT, true), mom, dad, ideal);
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.SPATT, dad.getSpAtt()),
                            new Pokemon(statsDecided, Pokemon.SPATT, true), mom, dad, ideal);
                }
            }
            if (!statsDecided.getSpDef())
            {
                // if mom and dad are same, put in same group and double pathChance -- hopefully
                // helps eliminate unncessary computation
                // if different do as normal
                if (mom.getSpDef() == dad.getSpDef())
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, statsRemaining), statsRemaining - 1,
                            parentStatsRemaining - 1, new Pokemon(child, Pokemon.SPDEF, mom.getSpDef()),
                            new Pokemon(statsDecided, Pokemon.SPDEF, true), mom, dad, ideal);
                } else
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.SPDEF, mom.getSpDef()),
                            new Pokemon(statsDecided, Pokemon.SPDEF, true), mom, dad, ideal);
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.SPDEF, dad.getSpDef()),
                            new Pokemon(statsDecided, Pokemon.SPDEF, true), mom, dad, ideal);
                }
            }
            if (!statsDecided.getSpeed())
            {
                // if mom and dad are same, put in same group and double pathChance -- hopefully
                // helps eliminate unncessary computation
                // if different do as normal
                if (mom.getSpeed() == dad.getSpeed())
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, statsRemaining), statsRemaining - 1,
                            parentStatsRemaining - 1, new Pokemon(child, Pokemon.SPEED, mom.getSpeed()),
                            new Pokemon(statsDecided, Pokemon.SPEED, true), mom, dad, ideal);
                } else
                {
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.SPEED, mom.getSpeed()),
                            new Pokemon(statsDecided, Pokemon.SPEED, true), mom, dad, ideal);
                    this.subPaths[indexDefined++] = new Instance(new Rational(1, 2 * statsRemaining),
                            statsRemaining - 1, parentStatsRemaining - 1,
                            new Pokemon(child, Pokemon.SPEED, dad.getSpeed()),
                            new Pokemon(statsDecided, Pokemon.SPEED, true), mom, dad, ideal);
                }
            }
            this.sumPathChance = new Rational(0, 1);
            try
            {
                for (int i = 0; i < indexDefined; i++)
                {
                    this.sumPathChance = subPaths[i].pathChance.multiply(subPaths[i].sumPathChance)
                            .add(this.sumPathChance);
                }
            } catch (ArithmeticException ae)
            {
                this.sumPathChance = new Rational(1, 1);
            }
        } else
        {
            this.subPaths = new Instance[2];
            // for random 1/32 chances here on out
            if (!statsDecided.getHP())
            {
                this.subPaths[0] = new Instance(new Rational(1, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.HP, Pokemon.PERFECT), new Pokemon(statsDecided, Pokemon.HP, true),
                        mom, dad, ideal);
                this.subPaths[1] = new Instance(new Rational(31, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.HP, Pokemon.IMPERFECT), new Pokemon(statsDecided, Pokemon.HP, true),
                        mom, dad, ideal);
            } else if (!statsDecided.getAttack())
            {
                this.subPaths[0] = new Instance(new Rational(1, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.ATTACK, Pokemon.PERFECT),
                        new Pokemon(statsDecided, Pokemon.ATTACK, true), mom, dad, ideal);
                this.subPaths[1] = new Instance(new Rational(31, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.ATTACK, Pokemon.IMPERFECT),
                        new Pokemon(statsDecided, Pokemon.ATTACK, true), mom, dad, ideal);
            } else if (!statsDecided.getDefense())
            {
                this.subPaths[0] = new Instance(new Rational(1, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.DEFENSE, Pokemon.PERFECT),
                        new Pokemon(statsDecided, Pokemon.DEFENSE, true), mom, dad, ideal);
                this.subPaths[1] = new Instance(new Rational(31, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.DEFENSE, Pokemon.IMPERFECT),
                        new Pokemon(statsDecided, Pokemon.DEFENSE, true), mom, dad, ideal);
            } else if (!statsDecided.getSpAtt())
            {
                this.subPaths[0] = new Instance(new Rational(1, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.SPATT, Pokemon.PERFECT),
                        new Pokemon(statsDecided, Pokemon.SPATT, true), mom, dad, ideal);
                this.subPaths[1] = new Instance(new Rational(31, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.SPATT, Pokemon.IMPERFECT),
                        new Pokemon(statsDecided, Pokemon.SPATT, true), mom, dad, ideal);
            } else if (!statsDecided.getSpDef())
            {
                this.subPaths[0] = new Instance(new Rational(1, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.SPDEF, Pokemon.PERFECT),
                        new Pokemon(statsDecided, Pokemon.SPDEF, true), mom, dad, ideal);
                this.subPaths[1] = new Instance(new Rational(31, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.SPDEF, Pokemon.IMPERFECT),
                        new Pokemon(statsDecided, Pokemon.SPDEF, true), mom, dad, ideal);
            } else
            {
                this.subPaths[0] = new Instance(new Rational(1, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.SPEED, Pokemon.PERFECT),
                        new Pokemon(statsDecided, Pokemon.SPEED, true), mom, dad, ideal);
                this.subPaths[1] = new Instance(new Rational(31, 32), statsRemaining - 1, 0,
                        new Pokemon(child, Pokemon.SPEED, Pokemon.IMPERFECT),
                        new Pokemon(statsDecided, Pokemon.SPEED, true), mom, dad, ideal);
            }
            try
            {
                this.sumPathChance = subPaths[0].pathChance.multiply(subPaths[0].sumPathChance)
                        .add(subPaths[1].pathChance.multiply(subPaths[1].sumPathChance));
            } catch (ArithmeticException ae)
            {
                this.sumPathChance = new Rational(1, 1);
            }
        }
        // for remaining parent stats, find possible scenarios
        // create array of new instances so it's possible to cycle through to add
        // start with stats not decided, and find the chances of perfect for each of
        // them
        // add up likelihoods for each instance (with each subpath's pathChance
        // and sumPathChance multiplied and added together) and put in sumPathChance
    }

    public static int powerItemCheck(Pokemon mom, Pokemon dad, Pokemon child, Pokemon statsDecided)
    {
        Pokemon powerBroker;
        if (!mom.hasPowerItem() && !dad.hasPowerItem())
        {
            return 0;
        } else if (mom.hasPowerItem())
        {
            powerBroker = mom;
        } else
        {
            powerBroker = dad;
        }
        switch (powerBroker.getItem())
        {
        case Pokemon.POWER_WEIGHT:
            statsDecided.setHP(true);
            child.setHP(powerBroker.getHP());
            break;
        case Pokemon.POWER_BRACER:
            statsDecided.setAttack(true);
            child.setAttack(powerBroker.getAttack());
            break;
        case Pokemon.POWER_BELT:
            statsDecided.setDefense(true);
            child.setDefense(powerBroker.getDefense());
            break;
        case Pokemon.POWER_LENS:
            statsDecided.setSpAtt(true);
            child.setSpAtt(powerBroker.getSpAtt());
            break;
        case Pokemon.POWER_BAND:
            statsDecided.setSpDef(true);
            child.setSpDef(powerBroker.getSpDef());
            break;
        case Pokemon.POWER_ANKLET:
            statsDecided.setSpeed(true);
            child.setSpeed(powerBroker.getSpeed());
            break;
        }
        return 1;
    }

    public static Instance createTree(Pokemon mom, Pokemon dad, Pokemon ideal)
    {
        // create some sort of GUI asking user to enter in parents' stats and item
        // also ask what perfect stats the user wants the child to have, or just number
        // set up starting tree with ideal child or number of perfect stats
        // DO NOT ALLOW TWO POKEMON TO HOLD POWER ITEMS!!!!!!!!!!!!!! - for now
        // only look for number of IVs now, don't worry about matching

        int statsRemaining = 6;
        int parentStatsRemaining = 3;
        if (mom.hasDestinyKnot() || dad.hasDestinyKnot())
        {
            parentStatsRemaining = 5;
        }
        Pokemon child = new Pokemon();
        Pokemon statsDecided = new Pokemon();

        int used = powerItemCheck(mom, dad, child, statsDecided);
        statsRemaining -= used;
        parentStatsRemaining -= used;

        Instance tree = new Instance(new Rational(1, 1), statsRemaining, parentStatsRemaining, child, statsDecided, mom,
                dad, ideal);
        return tree;
    }
}
