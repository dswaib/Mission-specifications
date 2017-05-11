package co4robots.engine;

public class PSPConstants 
{
    // Scopes
    public static final int S_Globally = 1;
    public static final int S_BeforeR = 2;
    public static final int S_AfterQ = 3;
    public static final int S_BetweenQandR = 4;
    public static final int S_AfterQuntilR = 5;
    
    // Patterns
    
    // Pattern Class
    public static final int PC_Occurrence = 1;
    public static final int PC_Order = 2;

    // Occurrence
    public static final int P_Universality = 1;
    public static final int P_Absence = 2;
    public static final int P_Existence = 3;
    public static final int P_BoundedExistence = 4;
    public static final int P_TransientState = 5;
    public static final int P_SteadyState = 6;
    public static final int P_MinimumDuration = 7;
    public static final int P_MaximumDuration = 8;
    public static final int P_Recurrence = 9;

    public static final int P_LastOccurrence = P_Recurrence;
    
    // Order
    public static final int P_Precedence = P_LastOccurrence + 1;
    public static final int P_PrecedenceChain1N = 11;
    public static final int P_PrecedenceChainN1 = 12;
    public static final int P_Until = 13;
    public static final int P_Response = 14;
    public static final int P_ResponseChain1N = 15;
    public static final int P_ResponseChainN1 = 16;
    public static final int P_ResponseInvariance = 17;

    public final int P_LastOrder = P_ResponseInvariance;

    // Constraints
    public static final int C_Event = 1;
    public static final int C_Time = 2;
    public static final int C_Probability = 3;

    // Time
    public static final int CT_Upper = 1;
    public static final int CT_Lower = 2;
    public static final int CT_Interval = 3;

    // Probability
    public static final int CP_Lower = 1;
    public static final int CP_LowerEqual = 2;
    public static final int CP_Greater = 3;
    public static final int CP_GreaterEqual = 4;
}
