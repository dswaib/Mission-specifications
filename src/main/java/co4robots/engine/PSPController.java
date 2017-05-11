package co4robots.engine;

import javax.swing.JFrame;

import co4robots.sel.Event;

public interface PSPController extends Iterable<Event>
{
    // reset PSP Wizard
    public void reset();

    public JFrame getHostFrame();

    public Event newEvent( String aName );
    public Event newEvent( String aName, String aSpecification );
        
    public boolean isScopeEventSelectionPossible( Event aEvent );
    public void updateScope();
    

    public boolean isPatternEventSelectionPossible( Event aEvent );
    public boolean isPatternEventSelectionPossible( Event aEvent, Event aAltEvent );
    public void updatePattern();
}
