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

import com.jordanappler.util.Rational;

public class ProbabilityTree
{
    protected BinaryEvent event;
    protected Rational goalProbability;

    public ProbabilityTree(BinaryEvent event)
    {
        this.event = event;
        goalProbability = new Rational(0, 1);
    }

    public Rational generate()
    {
        BinaryEvent[] refinedEvents = event.refine();
        for (BinaryEvent refinedEvent : refinedEvents)
        {
            Boolean satisfiesGoal = refinedEvent.satisfiesGoal();
            if (satisfiesGoal == null)
            {
                ProbabilityTree subtree = new ProbabilityTree(refinedEvent);
                goalProbability = goalProbability.add(subtree.generate());
            } else if (satisfiesGoal.booleanValue())
                goalProbability = goalProbability.add(refinedEvent.getProbability());
        }
        return goalProbability;
    }
}
