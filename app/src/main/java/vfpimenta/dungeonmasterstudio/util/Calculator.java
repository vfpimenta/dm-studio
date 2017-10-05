package vfpimenta.dungeonmasterstudio.util;

import android.support.v4.content.res.TypedArrayUtils;

import java.util.Arrays;
import java.util.Collections;

public class Calculator {
	public enum Level{
		Easy, Medium, Hard, Deadly
	}

	// Defining base values
	private static final int[] XP_Threshold_easy = new int[] {25, 50, 75, 125, 250, 300, 35, 450, 500, 600, 800, 100, 1100, 1250, 1400, 1600, 2000, 2100, 2400, 2800};
	private static final int[] XP_Threshold_medium = new int[] {50, 100, 150, 250, 500, 600, 750, 900, 1100, 1200, 1600, 2000, 2200, 2500, 2800, 3200, 3900, 4200, 4900, 5700};
	private static final int[] XP_Threshold_hard = new int[] {75, 150, 225, 375, 750, 900, 1100, 1400, 1600, 1900, 2400, 3000, 3400, 3800, 4300, 4800, 5900, 6300, 7300, 8500};
	private static final int[] XP_Threshold_deadly = new int[] {100, 200, 400, 500, 1100, 1400, 1700, 2100, 2400, 2800, 3600, 4500, 5100, 5700, 6400, 7200, 8800, 9500, 10900, 12700};
	private static final int [] XP_Monster = new int[] {10, 25, 50, 100, 200, 450, 700, 1100, 1800, 2300, 2900, 3900, 5000, 5900, 7200, 8400, 10000, 11500, 13000, 15000, 18000, 20000, 22000, 25000, 33000, 41000, 50000, 62000, 155000};
	private static final double [] XP_Multiplier = new double[] {0.5, 1, 1.5, 2, 2.5, 3, 4, 5};

	public static EncounterResult calculate(int[] party, int[] enemies) {
		// Calculate encounter adjusted XP
		// --------------------------------------------------------------------
		// Step 1: calculate base XP
		int enconunter_base_XP = 0;
		for(int i = 0; i<enemies.length; i++){
			enconunter_base_XP = enconunter_base_XP + XP_Monster[enemies[i]-1];
		}

		// Step 3: calculate adjusted XP to enemies size
		int enconunter_multiplierIDX;
		if(enemies.length == 1)
			enconunter_multiplierIDX = 2;
		else if(enemies.length == 2)
			enconunter_multiplierIDX = 3;
		else if(enemies.length <= 6)
			enconunter_multiplierIDX = 4;
		else if(enemies.length <= 10)
			enconunter_multiplierIDX = 5;
		else if(enemies.length <= 14)
			enconunter_multiplierIDX = 6;
		else
			enconunter_multiplierIDX = 7;

		// Step 4: calculate adjusted XP to party size
		if(party.length < 3)
			enconunter_multiplierIDX = enconunter_multiplierIDX + 1 ;
		else if(party.length >= 6)
			enconunter_multiplierIDX = enconunter_multiplierIDX - 1 ;

		// Step 5: finish calculation
		double enconunter_adjusted_XP = enconunter_base_XP * XP_Multiplier[enconunter_multiplierIDX-1] ;
		// --------------------------------------------------------------------

		// Calculate party thresholds
		// --------------------------------------------------------------------
		int enconunter_easy = 0 ;
		int enconunter_medium = 0 ;
		int enconunter_hard = 0 ;
		int enconunter_deadly = 0 ;

		for(int i = 0; i<party.length; i++){
			enconunter_easy = enconunter_easy + XP_Threshold_easy[party[i]-1] ;
			enconunter_medium = enconunter_medium + XP_Threshold_medium[party[i]-1] ;
			enconunter_hard = enconunter_hard + XP_Threshold_hard[party[i]-1] ;
			enconunter_deadly = enconunter_deadly + XP_Threshold_deadly[party[i]-1] ;
		}
		// --------------------------------------------------------------------

		// Find closest threshold
		// --------------------------------------------------------------------
		Double[] distances = new Double[] {Math.abs(enconunter_adjusted_XP - enconunter_easy), Math.abs(enconunter_adjusted_XP - enconunter_medium), Math.abs(enconunter_adjusted_XP - enconunter_hard), Math.abs(enconunter_adjusted_XP - enconunter_deadly)};

        double val = Collections.min(Arrays.asList(distances));

        String level;
		if(val == distances[0])
			level = Level.Easy.name() ;
		else if(val == distances[1])
			level = Level.Medium.name() ;
		else if(val == distances[2])
			level = Level.Hard.name() ;
		else
			level = Level.Deadly.name() ;
		// --------------------------------------------------------------------

		// Filling inputs
		EncounterResult result = new EncounterResult();
		result.setEasyThreshold(enconunter_easy);
		result.setMediumThreshold(enconunter_medium);
		result.setHardThreshold(enconunter_hard);
		result.setDeadlyThreshold(enconunter_deadly);

		result.setRawExperience(enconunter_base_XP);
		result.setAdjustedExperience(enconunter_adjusted_XP);
		result.setEncounterLevel(level);

		return result;
	}
}