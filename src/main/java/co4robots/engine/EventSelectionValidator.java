package co4robots.engine;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import co4robots.sel.Event;
import co4robots.sel.patterns.Pattern;
import co4robots.sel.scopes.Scope;

public class EventSelectionValidator 
{
    private static Event fScopeEvents[] = new Event[2];
    
    public static void updateScopeEvents( Scope aScope ) 
    {
        fScopeEvents[0] = aScope.getQ();
        fScopeEvents[1] = aScope.getR();
    }
    
    private static boolean isEventUsedInScope( Event aEvent )
    {
        if ( aEvent != null )
        {
            for ( int i = 0; i < 2; i++ )
            {
                Event lEvent = fScopeEvents[i];

                if ( lEvent == null || lEvent.isDefault() )
                    continue;
        
                // true if already used
                if ( lEvent.equals( aEvent ) )
                    return true;
            }
        }
        
        return false;
    }

    private static boolean fIsEditUpdate = false;
    
    public static void startEditUpdate()
    {
        fIsEditUpdate = true;
    }
    
    public static void stopEditUpdate()
    {
        fIsEditUpdate = false;
    }
    
    public static boolean isScopeEventSelectionPossible( JFrame aMessageFrame, Event aEvent ) 
    {
        if ( fIsEditUpdate )
            return true;
        
        if ( isEventUsedInScope( aEvent ) || isEventUsedInPattern( aEvent ) )
        {
            JOptionPane.showMessageDialog( aMessageFrame, 
                                           String.format( "Illegal scope event \"%s\"", aEvent.toString() ), 
                                           "Error",
                                           JOptionPane.ERROR_MESSAGE );
            
            return false;
        }
        else
            return true;
    }
    
    private static ArrayList<Event> fPatternEvents = new ArrayList<Event>();

    public static void clearSelection()
    {
        fScopeEvents[0] = null;
        fScopeEvents[1] = null;
        fPatternEvents.clear();        
    }
    
    public static void updatePatternEvents( Pattern aPattern )
    {
        fPatternEvents.clear();
        
        if ( aPattern != null ){
            fPatternEvents.addAll( aPattern.getEvents() );
        }
    }
    
    private static boolean isEventUsedInPattern( Event aEvent )
    {
        if ( aEvent != null )
        {
            for ( Event pe : fPatternEvents )
            {
                if ( pe.isDefault() )
                    continue;
        
                // true if already used
                if ( pe.equals( aEvent ) )
                    return true;
            }
        }
        
        return false;
    }

    public static boolean isPatternEventSelectionPossible( JFrame aMessageFrame, Event aEvent ) 
    {
        if ( fIsEditUpdate )
            return true;

        if ( isEventUsedInScope( aEvent ) || isEventUsedInPattern( aEvent ) )
        {
            JOptionPane.showMessageDialog( aMessageFrame, 
                                           String.format( "Illegal pattern event \"%s\"", aEvent.toString() ), 
                                           "Error",
                                           JOptionPane.ERROR_MESSAGE );
            
            return false;
            
        }

        return true;
    }

    public static boolean isPatternEventSelectionPossible( JFrame aMessageFrame, Event aEvent, Event aAltEvent )
    {
        if ( aEvent != null  )
        {
            if ( aEvent.isDefault() )
                return true;
        }          
        
        return isPatternEventSelectionPossible( aMessageFrame, aEvent );
    }
}
