package co4robots.constraints;

import co4robots.engine.PSPConstants;

public class GreaterThanEqualProbability extends ProbabilityBound
{
    public GreaterThanEqualProbability( double aProbability )
    {
        super(aProbability);
    }
    
    public int getType() 
    {
        return PSPConstants.CP_GreaterEqual;
    }

    public String getSpecificationAsSEL() 
    {
        StringBuilder sb = new StringBuilder( super.getSpecificationAsSEL() );

        sb.append( "greater or equal than " );
        sb.append( String.format( "%.4f", getProbability() ) );
        
        return sb.toString();
    }
    
    public String toString()
    {
        return String.format( "%s Prob >= %.4f", super.toString(), getProbability() );
    }
}
