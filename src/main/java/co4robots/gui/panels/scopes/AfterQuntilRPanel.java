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

package co4robots.gui.panels.scopes;

import co4robots.engine.PSPController;
import co4robots.gui.util.EventComboBox;
import co4robots.sel.Event;
import co4robots.sel.scopes.AfterQuntilR;
import co4robots.sel.scopes.Scope;

public class AfterQuntilRPanel extends javax.swing.JPanel implements ScopePanelFeatures 
{
    private PSPController fPSPController;
    private Scope fSelectedScope;

    public Scope getSelectedScope()
    {
        return fSelectedScope;
    }

    public void setSelectedScope( Scope aSelectedScope )
    {
        fSelectedScope = aSelectedScope;
        fScopeA.setSelectedItem( fSelectedScope.getQ() );
        fScopeB.setSelectedItem( fSelectedScope.getR() );
    }

    public AfterQuntilRPanel() 
    {
        initComponents();
        
        fSelectedScope = new AfterQuntilR();
    }

    public void clearSelection() 
    {
        setSelectedScope( new AfterQuntilR() );
    }
    
    public void setController( PSPController aPSPController )
    {
        fPSPController = aPSPController;
        
        fScopeA.setController( aPSPController );
        fScopeB.setController( aPSPController );
    }

    public void updateEvents()
    {
        fScopeA.updateEvents();
        fScopeB.updateEvents();
    }

    private void updateScope()
    {
        fSelectedScope.setQ( (Event)fScopeA.getSelectedItem() );
        fSelectedScope.setR( (Event)fScopeB.getSelectedItem() );

        if ( fPSPController != null )
            fPSPController.updateScope();
    }
 
    private boolean isEventSelectionPossible( Event aEvent )
    {
        if ( fPSPController != null )
            return fPSPController.isScopeEventSelectionPossible( aEvent );
        
        return true;
    }

    private void checkSelection( EventComboBox aScope )
    {
        Event lEvent = (Event)aScope.getSelectedItem();
        
        if ( lEvent != null )
        {
            if ( isEventSelectionPossible( lEvent ) )
            {
                aScope.acceptSelection();
                updateScope();
            }
            else
                aScope.revertSelection();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        fScopeA = new co4robots.gui.util.EventComboBox();
        jLabel7 = new javax.swing.JLabel();
        fScopeB = new co4robots.gui.util.EventComboBox();

        setMaximumSize(new java.awt.Dimension(500, 57));
        setMinimumSize(new java.awt.Dimension(500, 57));
        setPreferredSize(new java.awt.Dimension(500, 57));

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel5.setText("After");

        fScopeA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fScopeAActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel7.setText("until");

        fScopeB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fScopeBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(fScopeA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(fScopeB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(fScopeA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(fScopeB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fScopeAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fScopeAActionPerformed
        // check that selected Event is not used in pattern and fScope5B
        checkSelection( fScopeA );
    }//GEN-LAST:event_fScopeAActionPerformed

    private void fScopeBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fScopeBActionPerformed
        // check that selected Event is not used in pattern and fScope5A
        checkSelection( fScopeB );
    }//GEN-LAST:event_fScopeBActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private co4robots.gui.util.EventComboBox fScopeA;
    private co4robots.gui.util.EventComboBox fScopeB;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
