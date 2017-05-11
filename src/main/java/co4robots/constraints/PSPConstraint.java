package co4robots.constraints;

public abstract class PSPConstraint 
{
    public abstract int getConstraintCategory();

    public abstract int getType();

    public abstract String getSpecificationAsSEL();
        
    public String toString()
    {
        return getSpecificationAsSEL();
    }    
}
