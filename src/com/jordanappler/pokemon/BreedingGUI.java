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
package com.jordanappler.pokemon;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.BitSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.jordanappler.pokemon.HatchedPokemonStats.IV;
import com.jordanappler.pokemon.HatchedPokemonStats.Item;
import com.jordanappler.util.Rational;
import com.jordanappler.util.random.ProbabilityTree;

public class BreedingGUI implements ActionListener
{

    JLabel jlabMom;
    JLabel jlabDad;
    JLabel jlabTarget;
    JLabel jlabProbability;
    JLabel jlabFraction;
    JLabel jlabPercent;

    JCheckBox jcbMomHP;
    JCheckBox jcbMomAttack;
    JCheckBox jcbMomDefense;
    JCheckBox jcbMomSpAtt;
    JCheckBox jcbMomSpDef;
    JCheckBox jcbMomSpeed;

    JCheckBox jcbDadHP;
    JCheckBox jcbDadAttack;
    JCheckBox jcbDadDefense;
    JCheckBox jcbDadSpAtt;
    JCheckBox jcbDadSpDef;
    JCheckBox jcbDadSpeed;

    JCheckBox jcbTargetHP;
    JCheckBox jcbTargetAttack;
    JCheckBox jcbTargetDefense;
    JCheckBox jcbTargetSpAtt;
    JCheckBox jcbTargetSpDef;
    JCheckBox jcbTargetSpeed;

    JComboBox jcbMomItem;
    JComboBox jcbDadItem;

    JButton jbtnCalculate;

    public BreedingGUI()
    {
        JFrame jfrm = new JFrame("Pokémon Breeding Guide");
        jfrm.setLayout(new GridLayout(8, 4));
        jfrm.setSize(600, 200);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jlabMom = new JLabel("Mother");
        jlabDad = new JLabel("Father");
        jlabTarget = new JLabel("Target");
        jlabProbability = new JLabel("Probability");
        jlabFraction = new JLabel("");
        jlabPercent = new JLabel("");

        jcbMomHP = new JCheckBox("HP");
        jcbMomAttack = new JCheckBox("Attack");
        jcbMomDefense = new JCheckBox("Defense");
        jcbMomSpAtt = new JCheckBox("Special Attack");
        jcbMomSpDef = new JCheckBox("Special Defense");
        jcbMomSpeed = new JCheckBox("Speed");

        jcbDadHP = new JCheckBox("HP");
        jcbDadAttack = new JCheckBox("Attack");
        jcbDadDefense = new JCheckBox("Defense");
        jcbDadSpAtt = new JCheckBox("Special Attack");
        jcbDadSpDef = new JCheckBox("Special Defense");
        jcbDadSpeed = new JCheckBox("Speed");

        jcbTargetHP = new JCheckBox("HP");
        jcbTargetAttack = new JCheckBox("Attack");
        jcbTargetDefense = new JCheckBox("Defense");
        jcbTargetSpAtt = new JCheckBox("Special Attack");
        jcbTargetSpDef = new JCheckBox("Special Defense");
        jcbTargetSpeed = new JCheckBox("Speed");

        String[] items = { "Power Weight", "Power Bracer", "Power Belt", "Power Lens", "Power Band", "Power Anklet",
                "Destiny Knot", "Everstone", "None" };
        jcbMomItem = new JComboBox(items);
        jcbMomItem.setSelectedItem("None");
        jcbDadItem = new JComboBox(items);
        jcbDadItem.setSelectedItem("None");

        jbtnCalculate = new JButton("Calculate");
        jbtnCalculate.addActionListener(this);

        jfrm.add(jlabMom);
        jfrm.add(jlabDad);
        jfrm.add(jlabTarget);
        jfrm.add(jlabProbability);

        jfrm.add(jcbMomHP);
        jfrm.add(jcbDadHP);
        jfrm.add(jcbTargetHP);
        jfrm.add(jlabFraction);

        jfrm.add(jcbMomAttack);
        jfrm.add(jcbDadAttack);
        jfrm.add(jcbTargetAttack);
        jfrm.add(jlabPercent);

        jfrm.add(jcbMomDefense);
        jfrm.add(jcbDadDefense);
        jfrm.add(jcbTargetDefense);
        jfrm.add(new JLabel());

        jfrm.add(jcbMomSpAtt);
        jfrm.add(jcbDadSpAtt);
        jfrm.add(jcbTargetSpAtt);
        jfrm.add(new JLabel());

        jfrm.add(jcbMomSpDef);
        jfrm.add(jcbDadSpDef);
        jfrm.add(jcbTargetSpDef);
        jfrm.add(new JLabel());

        jfrm.add(jcbMomSpeed);
        jfrm.add(jcbDadSpeed);
        jfrm.add(jcbTargetSpeed);
        jfrm.add(jbtnCalculate);

        jfrm.add(jcbMomItem);
        jfrm.add(jcbDadItem);
        jfrm.add(new JLabel());
        jfrm.add(new JLabel());

        jfrm.setLocationRelativeTo(null);
        jfrm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        jbtnCalculate.setEnabled(false);

        BitSet momIVs = new BitSet(6);
        momIVs.set(IV.HP.ordinal(), jcbMomHP.isSelected());
        momIVs.set(IV.ATTACK.ordinal(), jcbMomAttack.isSelected());
        momIVs.set(IV.DEFENSE.ordinal(), jcbMomDefense.isSelected());
        momIVs.set(IV.SPECIAL_ATTACK.ordinal(), jcbMomSpAtt.isSelected());
        momIVs.set(IV.SPECIAL_DEFENSE.ordinal(), jcbMomSpDef.isSelected());
        momIVs.set(IV.SPEED.ordinal(), jcbMomSpeed.isSelected());
        Item momItem = HatchedPokemonStats.itemValues[jcbMomItem.getSelectedIndex()];

        BitSet dadIVs = new BitSet(6);
        dadIVs.set(IV.HP.ordinal(), jcbDadHP.isSelected());
        dadIVs.set(IV.ATTACK.ordinal(), jcbDadAttack.isSelected());
        dadIVs.set(IV.DEFENSE.ordinal(), jcbDadDefense.isSelected());
        dadIVs.set(IV.SPECIAL_ATTACK.ordinal(), jcbDadSpAtt.isSelected());
        dadIVs.set(IV.SPECIAL_DEFENSE.ordinal(), jcbDadSpDef.isSelected());
        dadIVs.set(IV.SPEED.ordinal(), jcbDadSpeed.isSelected());
        Item dadItem = HatchedPokemonStats.itemValues[jcbDadItem.getSelectedIndex()];

        BitSet goal = new BitSet(6);
        goal.set(IV.HP.ordinal(), jcbTargetHP.isSelected());
        goal.set(IV.ATTACK.ordinal(), jcbTargetAttack.isSelected());
        goal.set(IV.DEFENSE.ordinal(), jcbTargetDefense.isSelected());
        goal.set(IV.SPECIAL_ATTACK.ordinal(), jcbTargetSpAtt.isSelected());
        goal.set(IV.SPECIAL_DEFENSE.ordinal(), jcbTargetSpDef.isSelected());
        goal.set(IV.SPEED.ordinal(), jcbTargetSpeed.isSelected());

        ProbabilityTree tree = new ProbabilityTree(
                new HatchedPokemonStats(momIVs, momItem, dadIVs, dadItem, goal));
        Rational goalProbability = tree.generate();

        jlabFraction.setText(goalProbability.toString());
        jlabPercent.setText(goalProbability.toPercent() + "%");
        jbtnCalculate.setEnabled(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new BreedingGUI();
            }
        });
    }
}
