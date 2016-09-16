import core.IStatistics;
import core.StateStack;


public class GraphicsThread extends Thread implements IStatistics{
    private static final String TAG = "GraphicsThread";

    // desired FPS
    private static final int MAX_FPS = 60;
    // maximum number of frames to be skipped
    private static final int MAX_FRAME_SKIPS = 5;
    // the frame period
    private static final int FRAME_PERIOD = 1000 / MAX_FPS;

    // Stuff for stats
    //private DecimalFormat df = new DecimalFormat("0.##"); // 2 dp
    // we'll be reading the stats every second
    private static final int STAT_INTERVAL = 1000;
    // the average will be calculated by storing
    //  the last n FPSs
    private static final int FPS_HISTORY_NR = 10;
    // last time the status was stored
    private long lastStatusStore = 0l;
    // the status time counter
    private long statusIntervalTimer = 0l;
    // number of frames skipped since the game started
    private long totalFramesSkipped = 0l;
    // number of frames skipped in a tore cycle
    private long framesSkippedPerStatCycle = 0l;
    // number of rendered frames in an interval
    private int frameCountPerStatCycle = 0;
    private long totalFrameCount = 0l;
    // the last FPS value
    private double fpsStore[];
    // the number of time the stat has been read
    private long statsCount = 0l;
    // the average FPS since the game started
    private double averageFPS = 0.0;

    private boolean running;
    private StateStack _stateStack;
    //private MainGamePanel gamePanel;

    public long getGameTimer(){
        return this.statusIntervalTimer;
    }

    public GraphicsThread(StateStack stateStack){
        super();
        this._stateStack = stateStack;
    }

    public void setRunning(boolean running){
        this.running = running;
    }
    
    public double getFps(){
    	return averageFPS;
    }
    
    public long getTotalFramesSkipped(){
    	return totalFramesSkipped;
    }

    /**
     * this is the process that will call the update and render methods
     * 	of our States. 
     * This method should not be called directly. Instead, call the start 
     * 	method of the base class to invoke this process on a separate thread
     */
    @Override
    public void run(){
        //Canvas canvas;
        //Log.d(TAG, "Starting game loop");
        // initialize timing elements for stat gathering
        initTimingElements();

        long beginTime;     // the time when the cycle began
        long timeDiff;      // the time it took for the cycle to execute
        int sleepTime;      // ms to sleep (<0 if we're behind)
        int framesSkipped;  // number of frames being skipped

        sleepTime = 0;

        while (running){
            //canvas = null;
            try {
                synchronized (_stateStack){
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;  // resetting the frames skipped
                    // update the state
                    this._stateStack.update(this);
                    // draws the state
                    this._stateStack.render();
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0){
                        // if sleepTime > 0 we're OK
                        try{
                            // send the thread to sleep for a short time
                            Thread.sleep(sleepTime);
                        }
                        catch (InterruptedException e){}
                    }

                    while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
                        // we need to catch up
                        // update without rendering
                        this._stateStack.update(this);
                        // add frame period to check if in the next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }

                    if (framesSkipped > 0){
                        //Log.d(TAG, "Skipped: "+ framesSkipped);
                    }
                    // for statistics
                    frameCountPerStatCycle += framesSkipped;
                    // calling the routine to store the gathered statistics
                    storeStats();
                }
            } finally {

                }
            } // end finally
        }
    

    /**
     * The statistics - it is called every cycle, it checks if the time since last
     *  store is greater than the statistics gathering period (1 sec) and if so
     *  it calculates the FPS for the last period and stores it
     *
     * It tracks the number of frames per period. The number of frames since
     *  the start of the period are summed up and the calculation takes part
     *  only if the next period and the frame count is reset to 0
     */
    private void storeStats(){
        frameCountPerStatCycle++;
        totalFrameCount++;

        // check the actual time
        statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

        if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL){
            // calculate the actual frames per status check interval
            double actualFPS = (double)(frameCountPerStatCycle / (STAT_INTERVAL / 1000));

            // stores the latest fps in an array
            fpsStore[(int)statsCount % FPS_HISTORY_NR] = actualFPS;

            // increase the number of times statistics was calculated
            statsCount++;

            double totalFps = 0.0;
            // sum up the stored values
            for(int i = 0; i < FPS_HISTORY_NR; i++){
                totalFps += fpsStore[i];
            }

            // obtain the average
            if (statsCount < FPS_HISTORY_NR){
                // in case of the first 10 triggers
                averageFPS = totalFps / statsCount;
            }
            else {
            	averageFPS = totalFps / FPS_HISTORY_NR;
            }
            // saving the number of total frames skipped
            totalFramesSkipped += framesSkippedPerStatCycle;
            // resetting the counters after a stats cycle
            framesSkippedPerStatCycle = 0;
            statusIntervalTimer = 0;
            frameCountPerStatCycle = 0;

            statusIntervalTimer = System.currentTimeMillis();
            lastStatusStore = statusIntervalTimer;
        }
    }

    private void initTimingElements(){
        // initialize timing elements
        fpsStore = new double[FPS_HISTORY_NR];
        for (int i = 0; i < FPS_HISTORY_NR; i++){
            fpsStore[i] = 0.0;
        }
        //Log.d(TAG + ".initTimingElements()", "Timing elements for stats initialized");
    }
}
