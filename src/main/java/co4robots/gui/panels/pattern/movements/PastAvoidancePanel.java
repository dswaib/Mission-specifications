/**
 *
 * Copyright (C) 2011-2014 Swinburne University of Technology
 *
 * This file is part of PSPWizard, a tool for machine-assisted 
 * definition of temporal formulae capturing pattern-based system
 * properties, developed at the Faculty of Science, Engineering and
 * Technology at Swinburne University of Technology, Australia.
 * The patterns, structured English grammar and mappings are due to
 *
 *   Marco Autili, Universita` dell'Aquila
 *   Lars Grunske, University of Stuttgart
 *   Markus Lumpe, Swinburne University of Technology
 *   Patrizio Pelliccione, University of Gothenburg
 *   Antony Tang, Swinburne University of Technology
 *
 * Details about the PSP framework can found in
 *   "Aligning Qualitative, Real-Time, and Probabilistic
 *    Property Specification Patterns Using a Structured
 *    English Grammar"
 *
 *
 * PSPWizard is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * PSPWizard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PSPWizard; see the file COPYING.  If not, write to
 * the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */

package co4robots.gui.panels.pattern.movements;

import co4robots.Co4robotsGUI;
import co4robots.constraints.ProbabilityBound;
import co4robots.constraints.TimeBound;
import co4robots.engine.PSPController;
import co4robots.gui.dialogs.ProbabilityBoundDialog;
import co4robots.gui.dialogs.TimeBoundDialog;
import co4robots.gui.panels.pattern.PatternPanelFeatures;
import co4robots.sel.Event;
import co4robots.sel.patterns.Pattern;
import co4robots.sel.patterns.movements.PastAvoidance;
import co4robots.sel.patterns.occurrence.Absence;

public class PastAvoidancePanel extends javax.swing.JPanel implements PatternPanelFeatures
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PSPController fPSPController;
    private PastAvoidance fSelectedPattern;

    public Pattern getSelectedPattern()
    {
        return fSelectedPattern;
        
    }

    public void setSelectedPattern( Pattern aSelectedPattern ) 
    {
        if ( aSelectedPattern instanceof PastAvoidance )
        {
            fSelectedPattern = (PastAvoidance)aSelectedPattern;
            
            // update gui elements
            fP.setSelectedItem( fSelectedPattern.getP() );

            if ( fSelectedPattern.getPTimeBound() == null )
                fTimeBound.setText( "Time Bound" );
            else
                fTimeBound.setText( fSelectedPattern.getPTimeBound().toString() );

            if ( fSelectedPattern.getProbabilityBound() == null )
                fProbabilityBound.setText( "Probability Bound" );
            else
                fProbabilityBound.setText( fSelectedPattern.getProbabilityBound().toString() );
        }
    }

    public PastAvoidancePanel() 
    {
        initComponents();
        this.setBackground(Co4robotsGUI.BACKGROUNDCOLOR);
        fSelectedPattern = new PastAvoidance();
    }

    public void clearSelection()
    {
        setSelectedPattern( new PastAvoidance() );
    }
    
    public void setController( PSPController aPSPController )
    {
        fPSPController = aPSPController;
        
        fP.setController( fPSPController );
    }

    public void updateEvents()
    {
        fP.updateEvents();
    }

    private void updatePattern()
    {
        if ( fPSPController != null )
            fPSPController.updatePattern();
    }

    private boolean isEventSelectionPossible( Event aEvent )
    {
        if ( fPSPController != null )
            return fPSPController.isPatternEventSelectionPossible( aEvent );

        return true;
    }

    private void updateP()
    {
        fP.acceptSelection();

        fSelectedPattern.setP( (Event)fP.getSelectedItem() );

        updatePattern();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    		
        fP = new co4robots.gui.util.EventComboBox();
        fTimeBound = new javax.swing.JButton();
        fProbabilityBound = new javax.swing.JButton();
        fLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(536, 350));

        fP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fPActionPerformed(evt);
            }
        });

        fTimeBound.setText("Time Bound");
        fTimeBound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fTimeBoundActionPerformed(evt);
            }
        });

        fProbabilityBound.setText("Probability Bound");
        fProbabilityBound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fProbabilityBoundActionPerformed(evt);
            }
        });

        fLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        fLabel4.setText("[holds]");

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel16.setText("it is never the case that");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(fP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fTimeBound, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fProbabilityBound, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fLabel4)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fProbabilityBound)
                    .addComponent(fTimeBound))
                .addContainerGap(144, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fTimeBoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fTimeBoundActionPerformed
        TimeBoundDialog lDialog = new TimeBoundDialog( fPSPController );

        TimeBound lNewTimeBound = lDialog.showDialog( fSelectedPattern.getP(), fSelectedPattern.getPTimeBound() );

        if ( lNewTimeBound == null )
            fTimeBound.setText( "Time Bound" );
        else
            fTimeBound.setText( lNewTimeBound.toString() );

        fSelectedPattern.setPTimeBound( lNewTimeBound );
        updatePattern();
    }//GEN-LAST:event_fTimeBoundActionPerformed

    private void fProbabilityBoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fProbabilityBoundActionPerformed
        ProbabilityBoundDialog lDialog = new ProbabilityBoundDialog( fPSPController );

        ProbabilityBound lNewProbabilityBound = lDialog.showDialog( fSelectedPattern.getProbabilityBound() );

        if ( lNewProbabilityBound == null )
            fProbabilityBound.setText( "Probability Bound" );
        else
            fProbabilityBound.setText( lNewProbabilityBound.toString() );

        fSelectedPattern.setProbabilityBound( lNewProbabilityBound );
        updatePattern();
    }//GEN-LAST:event_fProbabilityBoundActionPerformed

    private void fPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fPActionPerformed
        // check that selected Event is not used in pattern
        Event lEvent = (Event)fP.getSelectedItem();
        
        if ( lEvent != null )
        {
            if ( fP.isStable( lEvent ) || isEventSelectionPossible( lEvent ) )
            {
                updateP();
            }
            else
                fP.revertSelection();
        }
    }//GEN-LAST:event_fPActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fLabel4;
    private co4robots.gui.util.EventComboBox fP;
    private javax.swing.JButton fProbabilityBound;
    private javax.swing.JButton fTimeBound;
    private javax.swing.JLabel jLabel16;
    // End of variables declaration//GEN-END:variables

}
