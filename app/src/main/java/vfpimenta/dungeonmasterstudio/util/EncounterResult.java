package vfpimenta.dungeonmasterstudio.util;

public class EncounterResult {

	private int easyThreshold;
	private int mediumThreshold;
	private int hardThreshold;
	private int deadlyThreshold;

	private int rawExperience;
	private double adjustedExperience;

	private Calculator.Level encounterLevel;

    public EncounterResult() {

    }

	public EncounterResult(int easyThreshold, int mediumThreshold, int hardThreshold, int deadlyThreshold, int rawExperience, double adjustedExperience, String encounterLevel) {
		this.easyThreshold = easyThreshold;
		this.mediumThreshold = mediumThreshold;
		this.hardThreshold = hardThreshold;
		this.deadlyThreshold = deadlyThreshold;
		this.rawExperience = rawExperience;
		this.adjustedExperience = adjustedExperience;
		this.encounterLevel = Calculator.Level.valueOf(encounterLevel);
	}

	public int getEasyThreshold() {
		return easyThreshold;
	}

	public void setEasyThreshold(int easyThreshold) {
		this.easyThreshold = easyThreshold;
	}

	public int getMediumThreshold() {
		return mediumThreshold;
	}

	public void setMediumThreshold(int mediumThreshold) {
		this.mediumThreshold = mediumThreshold;
	}

	public int getHardThreshold() {
		return hardThreshold;
	}

	public void setHardThreshold(int hardThreshold) {
		this.hardThreshold = hardThreshold;
	}

	public int getDeadlyThreshold() {
		return deadlyThreshold;
	}

	public void setDeadlyThreshold(int deadlyThreshold) {
		this.deadlyThreshold = deadlyThreshold;
	}

	public int getRawExperience() {
		return rawExperience;
	}

	public void setRawExperience(int rawExperience) {
		this.rawExperience = rawExperience;
	}

	public double getAdjustedExperience() {
		return adjustedExperience;
	}

	public void setAdjustedExperience(double adjustedExperience) {
		this.adjustedExperience = adjustedExperience;
	}

	public String getEncounterLevel() {
		return "<font color=\""+getColor()+"\"><b>"+encounterLevel+"</b></font>";
	}

	public void setEncounterLevel(String encounterLevel) {
		this.encounterLevel = Calculator.Level.valueOf(encounterLevel);
	}

	private String getColor() {
		switch(this.encounterLevel){
			case Easy:
			    return "#006d17";
            case Medium:
                return "#7c6c00";
            case Hard:
                return "#a34c00";
            case Deadly:
                return "#d80000";
            default:
                return "#000000";
		}
	}
}