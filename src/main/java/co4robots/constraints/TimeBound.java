package co4robots.constraints;

import co4robots.engine.PSPConstants;
import co4robots.sel.Event;

public abstract class TimeBound extends PSPConstraint
{
    private Event fTimedEvent;
    private String fTimeUnit;

    public Event getTimedEvent()
    {
        return fTimedEvent;
    }
    
    public String getTimeUnit()
    {
        return fTimeUnit;
    }
    
    public void setTimeUnit( String aTimeUnit )
    {
        fTimeUnit = aTimeUnit;
    }
    
    public TimeBound( Event aTimedEvent )
    {
        this( aTimedEvent, null );
    }

    public TimeBound( Event aTimedEvent, String aTimeUnit )
    {
        fTimedEvent = aTimedEvent;

        if ( aTimeUnit == null )
            fTimeUnit = "time units";
        else
            fTimeUnit = aTimeUnit;
    }

    public int getConstraintCategory()
    {
        return PSPConstants.C_Time;
    }
}
