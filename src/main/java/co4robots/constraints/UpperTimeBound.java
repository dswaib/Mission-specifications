package co4robots.constraints;

import co4robots.engine.PSPConstants;
import co4robots.sel.Event;

public class UpperTimeBound extends TimeBound
{
    private long fUpperLimit;
    
    public long getUpperLimit()
    {
        return fUpperLimit;
    }
    
    public UpperTimeBound( Event aTimedEvent, long aUpperLimit, String aTimeUnit )
    {
        super( aTimedEvent, aTimeUnit );
        
        fUpperLimit = aUpperLimit;
    }

    public int getType() 
    {
        return PSPConstants.CT_Upper;
    }

    public String getSpecificationAsSEL() 
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( "within " );
        if ( fUpperLimit == Long.MAX_VALUE )
            sb.append( "∞" );
        else
            sb.append( fUpperLimit );
        sb.append( " " );
        sb.append( getTimeUnit() );
        
        return sb.toString();
    }
}
