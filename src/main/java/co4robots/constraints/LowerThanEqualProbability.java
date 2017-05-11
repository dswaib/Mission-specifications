package co4robots.constraints;

import co4robots.engine.PSPConstants;

public class LowerThanEqualProbability extends ProbabilityBound
{
    public LowerThanEqualProbability( double aProbability )
    {
        super(aProbability);
    }
    
    public int getType() 
    {
        return PSPConstants.CP_LowerEqual;
    }

    public String getSpecificationAsSEL() 
    {
        StringBuilder sb = new StringBuilder( super.getSpecificationAsSEL() );

        sb.append( "lower or equal than " );
        sb.append( String.format( "%.4f", getProbability() ) );
        
        return sb.toString();
    }
    
    public String toString()
    {
        return String.format( "%s Prob <= %.4f", super.toString(), getProbability() );
    }
}
