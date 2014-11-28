package com.example.oddcolorout;

import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
{
	Button start;
	TextView timer_TV, score_TV;
	View [] square;
	
	int score, pick;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//getActionBar().setDisplayShowTitleEnabled(false);
		
		start = (Button) findViewById(R.id.start);
		timer_TV = (TextView) findViewById(R.id.timer);
		score_TV = (TextView) findViewById(R.id.score);
		
		square = new View[25];
		
		for(int i = 0; i < 25; i++)
		{
			square[i] = (View) findViewById(R.id.square1 + i);
			square[i].setOnClickListener(square_listener);
			square[i].setTag(i);
		    square[i].setClickable(true);
		}
		
		start.setOnClickListener(start_listener);
	}
	
	public void setColors()
	{
		Random rnd = new Random();
		int red, green, blue;
		red = rnd.nextInt(256);
		green = rnd.nextInt(256);
		blue = rnd.nextInt(256);
		int color = Color.argb(255, red, green, blue);
		pick = rnd.nextInt(25);
		square[pick].setBackgroundColor(color);
		int max = 35;
		
		if(score < max)
		{
			int red_diff = rnd.nextInt(max-score);
			int green_diff = rnd.nextInt(max-score);
			int blue_diff = rnd.nextInt(max-score);
			int sum = blue_diff + green_diff + red_diff;
			
			while(score < 5 && sum < 8)
			{
				red_diff = rnd.nextInt(max-score);
				green_diff = rnd.nextInt(max-score);
				blue_diff = rnd.nextInt(max-score);
				sum = blue_diff + green_diff + red_diff;
			}
			while(score < 10 && sum < 5)
			{
				red_diff = rnd.nextInt(max-score);
				green_diff = rnd.nextInt(max-score);
				blue_diff = rnd.nextInt(max-score);
				sum = blue_diff + green_diff + red_diff;
			}
			while(score < 15 && sum < 3)
			{
				red_diff = rnd.nextInt(max-score);
				green_diff = rnd.nextInt(max-score);
				blue_diff = rnd.nextInt(max-score);
				sum = blue_diff + green_diff + red_diff;
			}
			
			if(red_diff == 0 && blue_diff == 0 && green_diff == 0)
			{
				if(color%3 == 0)
				{
					red_diff = 1;
				}
				
				if(color%2 == 0)
				{
					blue_diff = 1;
				}
				else
				{
					green_diff = 1;
				}
			}
			
			if((color%2 == 0 && red - red_diff > 0) || red + red_diff > 255)	
			{
				red -= red_diff;
			}
			else
			{
				red += red_diff;
			}
			 
			if((color%2 == 0 && green - green_diff > 0) || green + green_diff > 255)
			{
				green -= green_diff;
			}
			else
			{
				green += green_diff;
			}
			 
			if((color%2 == 0 && blue - blue_diff > 0) || blue + blue_diff > 255)
			{
				blue -= blue_diff;
			}
			else
			{
				blue += blue_diff;
			}
			
			color = Color.argb(255, red, green, blue);
		}
		else
		{
			if((color%2 == 0 && color > 0) || color == Color.argb(255, 255, 255, 255))
			{
				color -= 1;
			}
			else
			{
				color += 1;
			}
		}
		
		for(int i = 0; i < 25; i++)
		{
			if(i != pick)
			{
				square[i].setBackgroundColor(color);
			}
		}
	}
	
	public OnClickListener start_listener = new OnClickListener()
	{
		@Override
		public void onClick(View arg0)
		{
			score = 0;
			score_TV.setText("Score: " + score);
			setColors();
			start.setVisibility(View.INVISIBLE);
			timer_TV.setTextColor(Color.BLACK);
			
			for(int i = 0; i < square.length; i++)
		    {
		    	square[i].setClickable(true);
		    }
			
			new CountDownTimer(30100, 1000)
			{
				 public void onTick(long millisUntilFinished)
				 {
					 if(millisUntilFinished <= 6000)
					 {
						 timer_TV.setTextColor(Color.RED);
					 }
				     timer_TV.setText("" + millisUntilFinished / 1000);
				 }

				 public void onFinish()
				 {
					 start.setVisibility(View.VISIBLE);
				     timer_TV.setText("" + 0);
				     score_TV.setText("Final Score: " + score);
				     
				     for(int i = 0; i < square.length; i++)
				     {
				    	 square[i].setClickable(false);
				     }
				 }
			}.start();
		}
	};
	
	public OnClickListener square_listener = new OnClickListener()
	{
		@Override
		public void onClick(View arg0)
		{
			int x = (int) arg0.getTag();
			if(x == pick)
			{
				setColors();
			    ++score;
			    score_TV.setText("Score: " + score);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
