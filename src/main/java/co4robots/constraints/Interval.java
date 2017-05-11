package co4robots.constraints;

import co4robots.engine.PSPConstants;
import co4robots.sel.Event;

public class Interval extends TimeBound
{
    private long fUpperLimit;
    private long fLowerLimit;
    
    public long getUpperLimit()
    {
        return fUpperLimit;
    }
    
    public long getLowerLimit()
    {
        return fLowerLimit;
    }

    public Interval( Event aTimedEvent, long aLowerLimit, long aUpperLimit, String aTimeUnit )
    {
        super( aTimedEvent, aTimeUnit );
        
        fLowerLimit = aLowerLimit;
        fUpperLimit = aUpperLimit;
    }

    public int getType() 
    {
        return PSPConstants.CT_Interval;
    }

    public String getSpecificationAsSEL() 
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( "between " );
        sb.append( fLowerLimit );
        sb.append( " and " );
        if ( fUpperLimit == Long.MAX_VALUE )
            sb.append( "∞" );
        else
            sb.append( fUpperLimit );
        sb.append( " " );
        sb.append( getTimeUnit() );
        
        return sb.toString();
    }
}
