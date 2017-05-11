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

package co4robots.sel.patterns.order;

import co4robots.constraints.ProbabilityBound;
import co4robots.constraints.TimeBound;
import co4robots.engine.PSPConstants;
import co4robots.sel.Event;
import co4robots.sel.patterns.Order;

public class Precedence extends Order
{
    private TimeBound fPTimeBound;
    
    public TimeBound getPTimeBound()
    {
        return fPTimeBound;
    }

    public void setPTimeBound( TimeBound aPTimeBound )
    {
        fPTimeBound = aPTimeBound;
    }
    
    public Precedence()
    {
        this( Event.getDefault(), Event.getDefault(), null, null );
    }
    
    public Precedence( Event aP, Event aS, TimeBound aPTimeBound, ProbabilityBound aProbBound )
    {
        super( aP, aS, null, aProbBound );

        fPTimeBound = aPTimeBound;
    }

    public int getType() 
    {
        return PSPConstants.P_Precedence;
    }
   
    public String getSpecificationAsSEL() 
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( "if " );
        sb.append( getP().getAsSELEvent() );
        sb.append( " [holds]" );

        sb.append( " then it must be the case that " );
        sb.append( getS().getAsSELEvent() );
        sb.append( " [has occurred]" );

        if ( fPTimeBound != null )
        {
            sb.append( " " );
            sb.append( fPTimeBound.getSpecificationAsSEL() );
        }

        sb.append( " before " );
        sb.append( getP().getAsSELEvent() );
        sb.append( " [holds]" );

        if ( getProbabilityBound() != null )
        {
            sb.append( " " );
            sb.append( getProbabilityBound().getSpecificationAsSEL() );
        }
        
        return sb.toString();
    }    
}