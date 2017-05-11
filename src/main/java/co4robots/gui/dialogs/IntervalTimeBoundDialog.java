package co4robots.gui.dialogs;

import co4robots.engine.PSPController;

public class IntervalTimeBoundDialog extends TimeBoundDialog 
{
    public IntervalTimeBoundDialog( PSPController aPSPController )
    {
        this( true, aPSPController );
    }
    
    public IntervalTimeBoundDialog( boolean aModal, PSPController aPSPController ) 
    {
        super( aModal, aPSPController );
        restrictToInterval();
    }
}
