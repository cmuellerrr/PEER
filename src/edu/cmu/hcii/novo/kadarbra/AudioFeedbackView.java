package edu.cmu.hcii.novo.kadarbra;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * @author Gordon
 *
 */
public class AudioFeedbackView extends SurfaceView implements SurfaceHolder.Callback {
	class AudioFeedbackThread extends Thread {
		private static final int NUMBER_OF_BARS = 16;
		private int BAR_MAXIMUM_OFFSET = 100; // how quickly bars grow in height
		private int BAR_MARGIN = 5; // size of margin between bars
		private int MINIMUM_RMS_READ = 1;
		private int MAXIMUM_FPS = 20;
		
		private int viewWidth;	// width of view
		public int viewHeight;	// height of view 
		
		private Paint pBar;			// paint of bars
		private Paint pThreshold; 	// paint of threshold marker
		private Paint pActive; 		// paint of active items

		
		private int levels[] = new int[NUMBER_OF_BARS];					// pixel heights of bars (if directly corresponding the noise level)
		private int drawnLevels[] = new int[NUMBER_OF_BARS];			// pixel heights of bars (when actually drawn - this is different from the above for making animations smoother)
		public float levelThreshold = 0;								// pixel height of level threshold line
		public float levelThresholdPercent = 0;							// percent of level threshold line

		private float[] drawnLevelSpeeds = new float[NUMBER_OF_BARS];	// pixel heights of bars (if directly corresponding the noise level)
		private int randomLevelIndex;

		private long lastMessageTime;
		private long lastFrameTime;
		private long lastDrawTime;
		private long lastRandomizeTime;
		
		private int busyDrawCounter;
		
        /** Handle to the surface manager object we interact with */
        private SurfaceHolder mSurfaceHolder;

        /** Indicate whether the surface has been created & is ready to draw */
        private boolean mRun = false;
        
        private int state = STATE_INACTIVE;
        
        public AudioFeedbackThread(SurfaceHolder surfaceHolder, Context context) {
            // get handles to some important objects
            mSurfaceHolder = surfaceHolder;
            mContext = context;
            init();
        }
        
        
        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                       // if (System.currentTimeMillis() - lastFrameTime > 1000/MAXIMUM_FPS){
                        //	lastFrameTime = System.currentTimeMillis();
                        	doDraw(c);
                        //}
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        /**
         * Used to signal the thread whether it should be running or not.
         * Passing true allows the thread to run; passing false will shut it
         * down if it's already running. Calling start() after this was most
         * recently called with false will result in an immediate shutdown.
         *
         * @param b true to run, false to shut down
         */
        public void setRunning(boolean b) {
            mRun = b;
        }
        
        /**
         * Sets current state
         * @param s current state
         */
        public void setState(int s){
        	state = s;
        	if (state == STATE_ACTIVE){
        		refreshThresholdLine();
        	}
        }
        
        /**
         * Gets current state
         * @return current state
         */
        public int getCurState(){
        	return state;
        }
        
        /**
         * Sets threshold to the top
         * Sets time for last received message (used to calculate y-coordinate of threshold line)
         */
        public void refreshThresholdLine(){
    		levelThreshold = viewHeight;
    		levelThresholdPercent = 1;
    		lastMessageTime = System.currentTimeMillis();
        }
        
    	/**
    	 * Sets flag for whether to render the busy state animation
    	 * 
    	 * @param busyState
    	 */
    	public void setBusy(boolean busyState){
    		busy = busyState;
    		refreshThresholdLine();
    		busyDrawCounter = 0;
    	}
    	
    	/**
    	 * Gets busy state
    	 * @return
    	 */
    	public boolean getBusy(){
    		return busy;
    	}
        
        /**
         * All drawing is handled here.
         * @param c
         */
		private void doDraw(Canvas c) {
			c.drawColor(Color.BLACK);
			//Log.v("hello","hello");
			
			
			if (busy){
				drawMicInactive(c);
				drawBusy(c,0.15f,0.7f,40);
			}else{
				if (shift)
					drawAudioBars(c);
				else
					drawAudioBarsVert(c);
				drawMicInactive(c);
			}
			
			if (!busy){
				if (state == STATE_INACTIVE){
					drawInactive(c);
				}else if (state == STATE_ACTIVE){
					drawMicThresholdAnimation(c);
					//drawThresholdLine(c);
				}
			}
			//drawBusy(c,0.15f,0.7f,40);
		}
		
		/**
		 * Initializes paint
		 */
		private void init(){
			pBar = new Paint();
			pBar.setColor(Color.parseColor("#a4ece8"));
			//p.setMaskFilter(new BlurMaskFilter(25, Blur.INNER));
			//p.setMaskFilter(new BlurMaskFilter(35, Blur.OUTER));
			//p.setMaskFilter(new BlurMaskFilter(15, Blur.SOLID));
			//pBar.setMaskFilter(new BlurMaskFilter(10, Blur.NORMAL));
			//p.setShadowLayer(70, 0, 0, Color.CYAN);
			
			pActive = new Paint();
			pActive.setColor(Color.parseColor("#FCDA4F"));
			pActive.setStyle(Style.FILL);

			pThreshold = new Paint();
			pThreshold.setColor(Color.parseColor("#FCDA4F"));
			pThreshold.setStrokeWidth(2.0f);
			//pThreshold.setShader(new LinearGradient(8f, 80f, 30f, 20f, Color.RED,Color.WHITE, TileMode.MIRROR));
		
			
			for (int i = 0; i < levels.length; i++ ){
				drawnLevelSpeeds[i] = 1;
				
			}
		}
		
        /* Callback invoked when the surface dimensions change. */
        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (mSurfaceHolder) {
                //viewWidth = width;
                //viewHeight = height;

               // Log.v("viewWidth,viewHeight",width+","+height);
            }
        }
        
        /**
         * Draws the inactive microphone 
         *
         * @param c
         */
        private void drawMicInactive(Canvas c){
        	Resources res = getContext().getResources();
        	Bitmap micInactive = BitmapFactory.decodeResource(res, R.drawable.mic_inactive);

        	Bitmap micInactive_Scaled = scaleImageRelativeToViewHeight(micInactive,0.8f);
        	
        	int left = viewWidth/2-micInactive_Scaled.getWidth()/2;
        	int top = viewHeight/2-micInactive_Scaled.getHeight()/2;
        	
        	int right = viewWidth/2+micInactive_Scaled.getWidth()/2;
        	int bottom = viewHeight/2+micInactive_Scaled.getHeight()/2;
        	
        	c.drawBitmap(micInactive_Scaled, left, top, null);
        }
        
        /**
         * Draws the active microphone 
         *
         * @param c
         * @param clippingProportion % of the microphone that is not filled in
         */
        private void drawMicActive(Canvas c, float clippingProportion, Paint paint){
        	Resources res = getContext().getResources();
        	Bitmap micActive = BitmapFactory.decodeResource(res, R.drawable.mic_active);

        	Bitmap micActive_Scaled = scaleImageRelativeToViewHeight(micActive,0.8f);
        	
        	int left = viewWidth/2-micActive_Scaled.getWidth()/2;
        	int top = viewHeight/2-micActive_Scaled.getHeight()/2;
        	
        	int right = viewWidth/2+micActive_Scaled.getWidth()/2;
        	int bottom = viewHeight/2+micActive_Scaled.getHeight()/2;

        	c.clipRect(left, top + (clippingProportion * micActive_Scaled.getHeight()), right, bottom);
        	
        	c.drawBitmap(micActive_Scaled, left, top, paint);
        }
        
        
        /**
         * Scales image relative to the view height
         * 
         * @param bm input bitmap
         * @param scale height of returned bitmap relative to view height
         * @return scaled bitmap
         */
        private Bitmap scaleImageRelativeToViewHeight(Bitmap bm, float scale){
        	
        	int micWidth = bm.getWidth();
        	int micHeight = bm.getHeight();
        	
        	int scaledHeight = (int) (viewHeight * scale);
        	int scaledWidth = (int)((1.0f*micWidth)/(1.0f*micHeight) * scaledHeight);
        	
        	Bitmap scaledImg = Bitmap.createScaledBitmap(bm, scaledWidth, scaledHeight, false);

        	return scaledImg;
   
        }
        
        /**
         * Draws an indicator for when the view is in the inactive state
         * @param c
         */
        private void drawInactive(Canvas c){
        }
        
        /**
         * Draws the busy animation when the speech recognizer is processing speech
         * Fades in/out the fill inside the microphone
         * 
         * @param c
         * @param startAlpha starting alpha value
         * @param endAlpha ending alpha value
         * @param animationFrames duration of animation
         */
    	private void drawBusy(Canvas c, float startAlpha, float endAlpha, int animationFrames){
    		Paint p = new Paint();
    		p.setColor(Color.parseColor("#a4ece8"));
    		
    		float alpha = startAlpha;
    		if (busyDrawCounter < animationFrames/2){
    			alpha = startAlpha + (endAlpha-startAlpha)/(animationFrames) * busyDrawCounter; 
    		}else if (busyDrawCounter >= animationFrames/2){
    			alpha = endAlpha - (endAlpha-startAlpha)/(animationFrames) * (busyDrawCounter - animationFrames/2);     			
    		}
    		busyDrawCounter++;
    		if (busyDrawCounter >= animationFrames){
    			busyDrawCounter = 0;
    		}
    		    		
    		p.setAlpha((int) (255*alpha));
    		drawMicActive(c, 0, p);
    	}
    	

    	/**
    	 * 
    	 */
        private void drawMicThresholdAnimation(Canvas c){
        	levelThresholdPercent = Math.max(getThresholdLinePercent(), 0);
        	drawMicActive(c, 1f-levelThresholdPercent, pActive);

        	lastDrawTime = System.currentTimeMillis();
        	
        	if (levelThresholdPercent <= 0){
        		setState(STATE_INACTIVE);
        	}
        }
    	
    	/**
    	 * Draws the threshold line
    	 */
    	private void drawThresholdLine(Canvas c){
    		int maxDrawnLevel = getMaximumDrawnLevel();
    		
    		if (levelThreshold < maxDrawnLevel)
    			levelThreshold = maxDrawnLevel;
    		else{
    			levelThreshold = getThresholdLineHeight();

    		}
    		levelThreshold = Math.max(levelThreshold, 0);
    		
    		c.drawLine(0, viewHeight-levelThreshold, viewWidth, viewHeight-levelThreshold, pThreshold);
    		//c.drawRect(0, viewHeight-levelThreshold, viewWidth, viewHeight, pThreshold);

    		lastDrawTime = System.currentTimeMillis();
    		//Log.v("lastDrawTime, offset", lastDrawTime +", "+ levelThreshold);
	    	
    		if (levelThreshold/(float)viewHeight < 0.25f){
    			setState(STATE_INACTIVE);
    		}

    	}
    	
    	/**
    	 * Calculates the amount that the threshold visualizer (microphone) should be filled up
    	 * @return
    	 */
    	private float getThresholdLinePercent(){
    		return 1 - (((System.currentTimeMillis() - lastMessageTime) / ((float)MessageHandler.COMMANDS_TIMEOUT_DURATION)));
    	}
    	
    	/**
    	 * Calculates the distance the line should move downwards when audio level is lower than threshold line
    	 * @return
    	 */
    	private float getThresholdLineOffset(){
    		return (((System.currentTimeMillis() - lastDrawTime) / ((float)MessageHandler.COMMANDS_TIMEOUT_DURATION)) * viewHeight);
    	}
    	
    	/**
    	 * Calculates the distance the line should move downwards when audio level is lower than threshold line
    	 * @return
    	 */
    	private float getThresholdLineHeight(){
    		return viewHeight - (((System.currentTimeMillis() - lastMessageTime) / ((float)MessageHandler.COMMANDS_TIMEOUT_DURATION)) * viewHeight);
    	}
    	

    	/**
    	 * Get maximum drawn bar height
    	 * @return
    	 */
    	private int getMaximumDrawnLevel(){
    		int maxDrawnLevel = 0;
    		// get maximum drawnLevel
    		for (int i = 0; i < drawnLevels.length; i++){
    			maxDrawnLevel = Math.max(maxDrawnLevel, drawnLevels[i]);
    		}
    		return maxDrawnLevel;
    	}
    	
    	
    	/**
    	 * Draws the audio bars (that move from left to right)
    	 * @param c
    	 */
    	private void drawAudioBars(Canvas c){
    		int barWidth = (viewWidth - BAR_MARGIN*levels.length) / levels.length; 
    		
    		for (int i = 0; i < levels.length; i++){
    			//Rect rect = new Rect(i*50+i*BAR_MARGIN,viewHeight-drawnLevels[i],i*50+50+i*BAR_MARGIN,viewHeight);
    			Rect rect = new Rect(i*barWidth+i*BAR_MARGIN,viewHeight-drawnLevels[i],i*barWidth+barWidth+i*BAR_MARGIN,viewHeight);

    			c.drawRect(rect, pBar);	
    		}
    	}
    	
    	/**
    	 * Draws audio bars (that go up and down)
    	 * @param c
    	 */
    	private void drawAudioBarsVert(Canvas c){
    		int barWidth = (viewWidth - BAR_MARGIN*levels.length) / levels.length; 
    		
    		if (getCurState() == STATE_INACTIVE){
    			pBar.setColor(Color.GRAY);
    		}else if (getCurState() == STATE_ACTIVE){
    			pBar.setColor(Color.parseColor("#a4ece8"));
    		}
    		
    		for (int i = 0; i < levels.length; i++){
    			Rect rect;
    			rect = new Rect(i*barWidth+i*BAR_MARGIN, viewHeight-drawnLevels[i],i*barWidth+barWidth+i*BAR_MARGIN,viewHeight);
    			
    			c.drawRect(rect, pBar);	
    		}
    	}
    	
    	
    	/**
    	 * Shifts audio level array by adding on the most recent audio height
    	 * @param pixel most recent audio height
    	 */
    	private void shiftAudioLevelArray(int pixel){
    		for (int i = 0; i < levels.length; i++){
    			if (i == levels.length-1){
    				levels[levels.length-1] = pixel;
    			}else{
    				levels[i] = levels[i+1];
    			}
    		}
    	
    	}
    	
    	
    	/**
    	 * Fills audio levels with the most recent pixel level value and random values based off of this pixel level
    	 */
    	private void fillAudioLevelArrayRandom(int pixel){
			for (int i = 0; i < levels.length; i++){
				if (randomLevelIndex == i)
					levels[i] = pixel;
				else
					levels[i] = (int) (pixel * drawnLevelSpeeds[i] * 2);
			}

    	}
    	
    	
    	/**
    	 * Converts pixel heights of bars to their heights when drawn
    	 */
    	private void convertAllPixelsToDrawnPixels(){
    		for (int i = 0; i < levels.length; i++){
    			drawnLevels[i] = convertPixelToDrawnPixel(i);
    		}
    	}
    	
    	/**
    	 * Converts the pixel size of visualizer that corresponds exactly to audio level to 
    	 * the pixel level that is drawn so the animation is smoother
    	 * 
    	 * @param arraySlot the # visualizer being drawn (corresponds to levels and drawnLevels arrays)
    	 * @return 
    	 */
    	private int convertPixelToDrawnPixel(int arrayIndex){
    		
    		int pixelDiff = levels[arrayIndex] - drawnLevels[arrayIndex];
    		int drawnPixel = drawnLevels[arrayIndex];
    				
    		if (pixelDiff > 0){
    			if (pixelDiff > BAR_MAXIMUM_OFFSET){
    				drawnPixel = (int) (drawnLevels[arrayIndex] + BAR_MAXIMUM_OFFSET * drawnLevelSpeeds[arrayIndex]);
    			}else{
    				drawnPixel = levels[arrayIndex];
    			}
    		}else if (pixelDiff < 0){
    			if (Math.abs(pixelDiff) > BAR_MAXIMUM_OFFSET){
    				drawnPixel = (int) (drawnLevels[arrayIndex] - BAR_MAXIMUM_OFFSET * (drawnLevelSpeeds[arrayIndex] / 2));
    			}else{
    				drawnPixel = levels[arrayIndex];
    			}
    		}
    		
    		return drawnPixel;
    	}
    	
    	/**
    	 * Randomly generates bar speeds
    	 */
    	private void setBarRandomSpeeds(){
    		if (System.currentTimeMillis() - lastRandomizeTime > 200){
    		
    			Random rand = new Random();
    			randomLevelIndex = rand.nextInt(levels.length);
    		
    			for (int i = 0; i < levels.length; i++){
    				if (i == randomLevelIndex)
    					drawnLevelSpeeds[i] = 1f;
    				else{
    					int speed = rand.nextInt(10);
    					drawnLevelSpeeds[i] = speed/20.0f+.25f;
    				}
    			}
    			lastRandomizeTime = System.currentTimeMillis();

    		}
    		
    	}
    	
    	/**
    	 * Converts raw rms value to an audio level percentage where 100% means the loudest and 0% means no audio	
    	 * @param rms
    	 * @return audio level percentage
    	 */
    	private float convertRmsToPercent(float rms){
    		float percent;
    		
    		if (rms < MINIMUM_RMS_READ)
    			percent = 0;
    		else
    			percent = (rms - MINIMUM_RMS_READ)/(10 - MINIMUM_RMS_READ);
    		
    		percent = Math.max(Math.min(1,percent),0);
    		    		
    		return percent;
    	}
    	
    	/**
    	 * Converts an audio level percentage to pixel size
    	 * @param percent
    	 * @return pixel size of audio feedback visualizer
    	 */
    	private int convertPercentToPixel(float percent){
    		int pixel;
    		pixel = (int) (percent * viewHeight);
    		return pixel;
    	}
 
    	/**
    	 * Updates audio feedback visualization numbers
    	 * @param rms raw rms value from SpeechRecognizer
    	 */
    	private void updateAudioValues(float rms){
    		float percent = convertRmsToPercent(rms);
    		int pixel = convertPercentToPixel(percent);
    		
    		if (shift){
    			shiftAudioLevelArray(pixel);
    		}else{
    			setBarRandomSpeeds();
    			fillAudioLevelArrayRandom(pixel);
    		}
    		convertAllPixelsToDrawnPixels();
    		
    		if (rms > MessageHandler.MINIMUM_REFRESH_RMS)
    			lastMessageTime = System.currentTimeMillis();
    		//drawnLevels[drawnLevels.length-1] = convertPixelToDrawnPixel(drawnLevels.length-1);
    	}
	}
	
	private String TAG = "AudioFeedbackView";

	public boolean shift = false; // if shift is true, we show the left/right moving visualizer, if false, then we show the up/down bars
	
	public int STATE_INACTIVE = 0;
	public int STATE_ACTIVE = 1;
	public boolean busy = false;
	
    /** The thread that actually draws the animation */
    private AudioFeedbackThread thread;
    /** Handle to the application context, used to e.g. fetch Drawables. */
    private Context mContext;
    
	public AudioFeedbackView(Context context, AttributeSet attrs) {
		super(context, attrs);
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        mContext = context;
        
        // create thread only; it's started in surfaceCreated()
        thread = new AudioFeedbackThread(holder, context);

        setFocusable(true); // make sure we get key events
	}
	
    /**
     * Fetches the animation thread corresponding to this LunarView.
     *
     * @return the animation thread
     */
    public AudioFeedbackThread getThread() {
        return thread;
    }

	
    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        thread.viewWidth = xNew;
        thread.viewHeight = yNew;

        Log.v(TAG,"w,h: "+thread.viewWidth+", "+thread.viewHeight);
    }
    
	/**
	 * Updates audio feedback visualization
	 * @param rms raw rms value from SpeechRecognizer
	 */
	public void updateAudioFeedbackView(float rms){
		thread.updateAudioValues(rms);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
		thread.setRunning(true);
        thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }		
	}
	


}