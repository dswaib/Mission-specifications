package co4robots.sel.patterns;

import java.util.ArrayList;

import co4robots.constraints.ProbabilityBound;
import co4robots.constraints.TimeBound;
import co4robots.engine.PSPConstants;
import co4robots.sel.Event;

public abstract class Movements extends Pattern
{
    private Event fP;
    private TimeBound fPTimeBound;
    private ProbabilityBound fProbBound;

    public Event getP()
    {
        return fP;
    }
    
    public void setP( Event aP )
    {
        fP = aP;
    }
    
    public TimeBound getPTimeBound()
    {
        return fPTimeBound;
    }

    public void setPTimeBound( TimeBound aPTimeBound )
    {
        fPTimeBound = aPTimeBound;
    }

    public ProbabilityBound getProbabilityBound()
    {
        return fProbBound;
    }

    public void setProbabilityBound( ProbabilityBound aProbBound )
    {
        fProbBound = aProbBound;
    }

    public Movements( Event aP, TimeBound aPTimeBound, ProbabilityBound aProbBound )
    {
        fP = aP;
        fPTimeBound = aPTimeBound;
        fProbBound = aProbBound;
    }

    public int getPatternCategory()
    {
        return PSPConstants.PC_Movements;
    }
        
    public ArrayList<Event> getEvents() 
    {
        ArrayList<Event> Result = new ArrayList<Event>();
        
        Result.add( getP() );
        
        return Result;
    }
}
