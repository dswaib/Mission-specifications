package co4robots.constraints;

import co4robots.engine.PSPConstants;

public abstract class ProbabilityBound extends PSPConstraint
{
    private double fProbability;
    
    public double getProbability()
    {
        return fProbability;
    }
    
    public ProbabilityBound( double aProbability )
    {
        fProbability = aProbability;
    }
    
    public int getConstraintCategory()
    {
        return PSPConstants.C_Probability;
    }
    
    public String getSpecificationAsSEL() 
    {
        return "with a probability ";
    }
    
    public String toString()
    {
        return "with";
    }
}
