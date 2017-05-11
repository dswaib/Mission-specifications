package co4robots.engine;

import java.util.ArrayList;
import java.util.Iterator;

import co4robots.sel.Event;

public class EventStorage implements Iterable<Event>
{
    private ArrayList<Event> fElements;
    
    public EventStorage()
    {
        fElements = new ArrayList<Event>();
    }
    
    public int size()
    {
        return fElements.size();
    }

    public Event get( int aIndex )
    {
        return fElements.get( aIndex );
    }

    private boolean addEvent( Event aEvent )
    {
        if ( !fElements.contains( aEvent ) )
        {
            fElements.add( aEvent );
            return true;
        }
        else
        {
            return false;
        }
    }

    // gui support to add new event
    public Event newEvent( String aDescriptor )
    {
        Event Result = new Event( aDescriptor );
        
        if ( addEvent( Result ) )
        {
            return Result;
        }
        else
            return null;
    }

    public Event newEvent( String aName, String aSpecification )
    {
        Event Result = new Event( aName, aSpecification );
        
        if ( addEvent( Result ) )
        {
            return Result;
        }
        else
            return null;
    }

    public Iterator<Event> iterator() 
    {
        return fElements.iterator();
    }
}
