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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

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

        String[] items = { "None", "Power Weight", "Power Bracer", "Power Belt", "Power Lens", "Power Band",
                "Power Anklet", "Everstone", "Destiny Knot" };
        jcbMomItem = new JComboBox(items);
        jcbDadItem = new JComboBox(items);

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
        Pokemon mom = new Pokemon(jcbMomHP.isSelected(), jcbMomAttack.isSelected(), jcbMomDefense.isSelected(),
                jcbMomSpAtt.isSelected(), jcbMomSpDef.isSelected(), jcbMomSpeed.isSelected(),
                jcbMomItem.getSelectedIndex());
        Pokemon dad = new Pokemon(jcbDadHP.isSelected(), jcbDadAttack.isSelected(), jcbDadDefense.isSelected(),
                jcbDadSpAtt.isSelected(), jcbDadSpDef.isSelected(), jcbDadSpeed.isSelected(),
                jcbDadItem.getSelectedIndex());
        Pokemon target = new Pokemon(jcbTargetHP.isSelected(), jcbTargetAttack.isSelected(),
                jcbTargetDefense.isSelected(), jcbTargetSpAtt.isSelected(), jcbTargetSpDef.isSelected(),
                jcbTargetSpeed.isSelected());
        Instance tree = Instance.createTree(mom, dad, target);
        Rational probability = tree.sumPathChance;
        jlabFraction.setText(probability.toString());
        jlabPercent.setText(probability.toPercent() + "%");
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
