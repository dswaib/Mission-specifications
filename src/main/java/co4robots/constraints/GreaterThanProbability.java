package co4robots.constraints;

import co4robots.engine.PSPConstants;

public class GreaterThanProbability extends ProbabilityBound
{
    public GreaterThanProbability( double aProbability )
    {
        super(aProbability);
    }
    
    public int getType() 
    {
        return PSPConstants.CP_Greater;
    }

    public String getSpecificationAsSEL() 
    {
        StringBuilder sb = new StringBuilder( super.getSpecificationAsSEL() );

        sb.append( "greater than " );
        sb.append( String.format( "%.4f", getProbability() ) );
        
        return sb.toString();
    }
    
    public String toString()
    {
        return String.format( "%s Prob > %.4f", super.toString(), getProbability() );
    }
}
