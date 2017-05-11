package co4robots.constraints;

import co4robots.engine.PSPConstants;
import co4robots.sel.Event;

public class EventConstraint extends PSPConstraint
{
    private Event fEvent;

    public static EventConstraint newEventConstraint( Event aEvent )
    {
        EventConstraint Result = null;

        if ( aEvent != null )
        {
            if ( !aEvent.isDefault() )
                Result = new EventConstraint( aEvent );
        }
        
        return Result;
    }
    
    public Event getEvent()
    {
        return fEvent;
    }

    public void setEvent( Event aEvent )
    {
        fEvent = aEvent;
    }

    public EventConstraint( Event aEvent )
    {
        fEvent = aEvent;
    }

    public int getConstraintCategory() 
    {
        return PSPConstants.C_Event;
    }

    public int getType() 
    {
        return PSPConstants.C_Event;
    }

    public String getSpecificationAsSEL()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( "without " );
        sb.append( fEvent.getAsSELEvent() );
        
        sb.append( " [holding]" );
        sb.append( " in between" );
        
        return sb.toString();
    }
}
