package co4robots.constraints;

import co4robots.engine.PSPConstants;
import co4robots.sel.Event;

public class LowerTimeBound extends TimeBound
{
    private long fLowerLimit;
    
    public long getLowerLimit()
    {
        return fLowerLimit;
    }
    
    public LowerTimeBound( Event aTimedEvent, long aLowerLimit, String aTimeUnit )
    {
        super( aTimedEvent, aTimeUnit );
        
        fLowerLimit = aLowerLimit;
    }

    public int getType() 
    {
        return PSPConstants.CT_Lower;
    }

    public String getSpecificationAsSEL() 
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( "after " );
        sb.append( fLowerLimit );
        sb.append( " " );
        sb.append( getTimeUnit() );
        
        return sb.toString();
    }
}
