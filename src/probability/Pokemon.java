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

public class Pokemon
{

    /* Stats constants */
    public static final boolean PERFECT = true;
    public static final boolean IMPERFECT = false;
    public static final int HP = 0;
    public static final int ATTACK = 1;
    public static final int DEFENSE = 2;
    public static final int SPATT = 3;
    public static final int SPDEF = 4;
    public static final int SPEED = 5;

    /* Items constants */
    public static final int NOTHING = 0;
    public static final int POWER_WEIGHT = 1;
    public static final int POWER_BRACER = 2;
    public static final int POWER_BELT = 3;
    public static final int POWER_LENS = 4;
    public static final int POWER_BAND = 5;
    public static final int POWER_ANKLET = 6;
    public static final int EVERSTONE = 7;
    public static final int DESTINY_KNOT = 8;

    /* Stats */
    private boolean hp;
    private boolean attack;
    private boolean defense;
    private boolean spatt;
    private boolean spdef;
    private boolean speed;

    /* Item */
    private int item;

    public Pokemon()
    {
        this.hp = IMPERFECT;
        this.attack = IMPERFECT;
        this.defense = IMPERFECT;
        this.spatt = IMPERFECT;
        this.spdef = IMPERFECT;
        this.speed = IMPERFECT;

        this.item = NOTHING;
    }

    public Pokemon(boolean hp, boolean attack, boolean defense, boolean spatt, boolean spdef, boolean speed)
    {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spatt = spatt;
        this.spdef = spdef;
        this.speed = speed;

        this.item = NOTHING;
    }

    public Pokemon(boolean hp, boolean attack, boolean defense, boolean spatt, boolean spdef, boolean speed, int item)
    {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spatt = spatt;
        this.spdef = spdef;
        this.speed = speed;

        this.item = item;
    }

    public Pokemon(Pokemon toCopy)
    {
        this.hp = toCopy.hp;
        this.attack = toCopy.attack;
        this.defense = toCopy.defense;
        this.spatt = toCopy.spatt;
        this.spdef = toCopy.spdef;
        this.speed = toCopy.speed;

        this.item = toCopy.item;
    }

    public Pokemon(Pokemon toCopy, int stat, boolean replace)
    {
        this(toCopy);
        switch (stat)
        {
        case HP:
            this.hp = replace;
            break;
        case ATTACK:
            this.attack = replace;
            break;
        case DEFENSE:
            this.defense = replace;
            break;
        case SPATT:
            this.spatt = replace;
            break;
        case SPDEF:
            this.spdef = replace;
            break;
        case SPEED:
            this.speed = replace;
            break;
        }
    }

    public boolean getHP()
    {
        return this.hp;
    }

    public boolean getAttack()
    {
        return this.attack;
    }

    public boolean getDefense()
    {
        return this.defense;
    }

    public boolean getSpAtt()
    {
        return this.spatt;
    }

    public boolean getSpDef()
    {
        return this.spdef;
    }

    public boolean getSpeed()
    {
        return this.speed;
    }

    public int getItem()
    {
        return this.item;
    }

    public void setHP(boolean hp)
    {
        this.hp = hp;
    }

    public void setAttack(boolean attack)
    {
        this.attack = attack;
    }

    public void setDefense(boolean defense)
    {
        this.defense = defense;
    }

    public void setSpAtt(boolean spatt)
    {
        this.spatt = spatt;
    }

    public void setSpDef(boolean spdef)
    {
        this.spdef = spdef;
    }

    public void setSpeed(boolean speed)
    {
        this.speed = speed;
    }

    public int getNumberOfPerfectStats()
    {
        int perfectStats = 0;
        if (this.hp)
            perfectStats++;
        if (this.attack)
            perfectStats++;
        if (this.defense)
            perfectStats++;
        if (this.spatt)
            perfectStats++;
        if (this.spdef)
            perfectStats++;
        if (this.speed)
            perfectStats++;
        return perfectStats;
    }

    public boolean hasPowerItem()
    {
        return (this.item > 0 && this.item < 7);
    }

    public boolean hasDestinyKnot()
    {
        return (this.item == DESTINY_KNOT);
    }

    public boolean hasEverstone()
    {
        return (this.item == EVERSTONE);
    }

    public int statsFromIdeal(Pokemon ideal)
    {
        int statsNeeded = 0;
        if (ideal.getHP() && !this.getHP())
            statsNeeded++;
        if (ideal.getAttack() && !this.getAttack())
            statsNeeded++;
        if (ideal.getDefense() && !this.getDefense())
            statsNeeded++;
        if (ideal.getSpAtt() && !this.getSpAtt())
            statsNeeded++;
        if (ideal.getSpDef() && !this.getSpDef())
            statsNeeded++;
        if (ideal.getSpeed() && !this.getSpeed())
            statsNeeded++;
        return statsNeeded;
    }
}
