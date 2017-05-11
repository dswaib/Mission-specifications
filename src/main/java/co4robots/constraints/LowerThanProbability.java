package co4robots.constraints;

import co4robots.engine.PSPConstants;

public class LowerThanProbability extends ProbabilityBound
{
    public LowerThanProbability( double aProbability )
    {
        super(aProbability);
    }
    
    public int getType() 
    {
        return PSPConstants.CP_Lower;
    }

    public String getSpecificationAsSEL() 
    {
        StringBuilder sb = new StringBuilder( super.getSpecificationAsSEL() );

        sb.append( "lower than " );
        sb.append( String.format( "%.4f", getProbability() ) );
        
        return sb.toString();
    }
    
    public String toString()
    {
        return String.format( "%s Prob < %.4f", super.toString(), getProbability() );
    }
}
