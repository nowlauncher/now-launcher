/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2012 Zhenghong Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.YahooWeather.utils;


public class WeatherInfo {

	String mTitle;
	String mDescription;
	String mLanguage;
	String mLastBuildDate;
	String mLocationCity;
	String mLocationRegion; // region may be null
	String mLocationCountry;

	String mWindChill;
	String mWindDirection;
	String mWindSpeed;

	String mAtmosphereHumidity;
	String mAtmosphereVisibility;
	String mAtmospherePressure;
	String mAtmosphereRising;

	String mAstronomySunrise;
	String mAstronomySunset;

	String mConditionTitle;
	String mConditionLat;
	String mConditionLon;

	/*
	 * information in tag "yweather:condition"
	 */
	int mCurrentCode;
	String mCurrentText;
	int mCurrentTempC;
	int mCurrentTempF;
	String mCurrentConditionIconURL;
	String mCurrentConditionDate;

	/*
	 * information in the first tag "yweather:forecast"
	 */
	String mForecast1Day;
	String mForecast1Date;
	int mForecast1Code;
	String mForecast1Text;
	int mForecast1TempHighC;
	int mForecast1TempLowC;
	int mForecast1TempHighF;
	int mForecast1TempLowF;
	String mForecast1ConditionIconURL;

	/*
	 * information in the second tag "yweather:forecast"
	 */
	String mForecast2Day;
	String mForecast2Date;
	int mForecast2Code;
	String mForecast2Text;
	int mForecast2TempHighC;
	int mForecast2TempLowC;
	int mForecast2TempHighF;
	int mForecast2TempLowF;
	String mForecast2ConditionIconURL;

    /*
	 * information in the third tag "yweather:forecast"
	 */
    String mForecast3Day;
    String mForecast3Date;
    int mForecast3Code;
    String mForecast3Text;
    int mForecast3TempHighC;
    int mForecast3TempLowC;
    int mForecast3TempHighF;
    int mForecast3TempLowF;
    String mForecast3ConditionIconURL;

    /*
	 * information in the fourth tag "yweather:forecast"
	 */
    String mForecast4Day;
    String mForecast4Date;
    int mForecast4Code;
    String mForecast4Text;
    int mForecast4TempHighC;
    int mForecast4TempLowC;
    int mForecast4TempHighF;
    int mForecast4TempLowF;
    String mForecast4ConditionIconURL;

    /*
     * information in the fifth tag "yweather:forecast"
     */
    String mForecast5Day;
    String mForecast5Date;
    int mForecast5Code;
    String mForecast5Text;
    int mForecast5TempHighC;
    int mForecast5TempLowC;
    int mForecast5TempHighF;
    int mForecast5TempLowF;
    String mForecast5ConditionIconURL;

    String weatherurl;

    void setWeatherurl(String weatherurl) {
        this.weatherurl = weatherurl;
    }
    public String getWeatherurl() {
        return weatherurl;
    }

    /************************************************
     * Today - Start
     *
     *
     ************************************************/
    public String getCurrentConditionDate() {
        return mCurrentConditionDate;
    }

    void setCurrentConditionDate(String currentConditionDate) {
        mCurrentConditionDate = currentConditionDate;
    }

    private int turnFtoC(int tempF) {
        return (tempF - 32) * 5 / 9;
    }

    public int getCurrentCode() {
        return mCurrentCode;
    }

    void setCurrentCode(int currentCode) {
        mCurrentCode = currentCode;
        mCurrentConditionIconURL = "http://l.yimg.com/a/i/us/we/52/" + currentCode + ".gif";
    }

    public int getCurrentTempF() {
        return mCurrentTempF;
    }

    void setCurrentTempF(int currentTempF) {
        mCurrentTempF = currentTempF;
        mCurrentTempC = this.turnFtoC(currentTempF);
    }

    public String getCurrentConditionIconURL() {
        return mCurrentConditionIconURL;
    }

    public int getCurrentTempC() {
        return mCurrentTempC;
    }

    public String getTitle() {
        return mTitle;
    }

    void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    void setDescription(String description) {
        mDescription = description;
    }

    public String getLanguage() {
        return mLanguage;
    }

    void setLanguage(String language) {
        mLanguage = language;
    }

    public String getLastBuildDate() {
        return mLastBuildDate;
    }

    void setLastBuildDate(String lastBuildDate) {
        mLastBuildDate = lastBuildDate;
    }

    public String getLocationCity() {
        return mLocationCity;
    }

    void setLocationCity(String locationCity) {
        mLocationCity = locationCity;
    }

    public String getLocationRegion() {
        return mLocationRegion;
    }

    void setLocationRegion(String locationRegion) {
        mLocationRegion = locationRegion;
    }

    public String getLocationCountry() {
        return mLocationCountry;
    }

    void setLocationCountry(String locationCountry) {
        mLocationCountry = locationCountry;
    }

    public String getWindChill() {
        return mWindChill;
    }

    void setWindChill(String windChill) {
        mWindChill = windChill;
    }

    public String getWindDirection() {
        return mWindDirection;
    }

    void setWindDirection(String windDirection) {
        mWindDirection = windDirection;
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    void setWindSpeed(String windSpeed) {
        mWindSpeed = windSpeed;
    }

    public String getAtmosphereHumidity() {
        return mAtmosphereHumidity;
    }

    void setAtmosphereHumidity(String atmosphereHumidity) {
        mAtmosphereHumidity = atmosphereHumidity;
    }

    public String getAtmosphereVisibility() {
        return mAtmosphereVisibility;
    }

    void setAtmosphereVisibility(String atmosphereVisibility) {
        mAtmosphereVisibility = atmosphereVisibility;
    }

    public String getAtmospherePressure() {
        return mAtmospherePressure;
    }

    void setAtmospherePressure(String atmospherePressure) {
        mAtmospherePressure = atmospherePressure;
    }

    public String getAtmosphereRising() {
        return mAtmosphereRising;
    }

    void setAtmosphereRising(String atmosphereRising) {
        mAtmosphereRising = atmosphereRising;
    }

    public String getAstronomySunrise() {
        return mAstronomySunrise;
    }

    void setAstronomySunrise(String astronomySunrise) {
        mAstronomySunrise = astronomySunrise;
    }

    public String getAstronomySunset() {
        return mAstronomySunset;
    }

    void setAstronomySunset(String astronomySunset) {
        mAstronomySunset = astronomySunset;
    }

    public String getConditionTitle() {
        return mConditionTitle;
    }

    void setConditionTitle(String conditionTitle) {
        mConditionTitle = conditionTitle;
    }

    public String getConditionLat() {
        return mConditionLat;
    }

    void setConditionLat(String conditionLat) {
        mConditionLat = conditionLat;
    }

    public String getConditionLon() {
        return mConditionLon;
    }

    void setConditionLon(String conditionLon) {
        mConditionLon = conditionLon;
    }

    public String getCurrentText() {
        return mCurrentText;
    }

    void setCurrentText(String currentText) {
        mCurrentText = currentText;
    }

    void setCurrentTempC(int currentTempC) {
        mCurrentTempC = currentTempC;
    }

    void setCurrentConditionIconURL(String currentConditionIconURL) {
        mCurrentConditionIconURL = currentConditionIconURL;
    }
    /************************************************
     * Today - End
     *
     *
     ************************************************/
    
    /************************************************
     * First Forecast - Start
     *
     *
     ************************************************/
    public String getForecast1Date() {
        return mForecast1Date;
    }

    void setForecast1Date(String forecast1Date) {
        mForecast1Date = forecast1Date;
    }
    public String getForecast1Day() {
        return mForecast1Day;
    }

    void setForecast1Day(String forecast1Day) {
        mForecast1Day = forecast1Day;
    }

    public int getForecast1Code() {
        return mForecast1Code;
    }

    void setForecast1Code(int forecast1Code) {
        mForecast1Code = forecast1Code;
        mForecast1ConditionIconURL = "http://l.yimg.com/a/i/us/we/52/" + forecast1Code + ".gif";
    }

    public String getForecast1Text() {
        return mForecast1Text;
    }

    void setForecast1Text(String forecast1Text) {
        mForecast1Text = forecast1Text;
    }

    public int getForecast1TempHighF() {
        return mForecast1TempHighF;
    }
    void setForecast1TempHighF(int forecast1TempHighF) {
        mForecast1TempHighF = forecast1TempHighF;
        mForecast1TempHighC = this.turnFtoC(forecast1TempHighF);
    }

    public int getForecast1TempLowF() {
        return mForecast1TempLowF;
    }

    void setForecast1TempLowF(int forecast1TempLowF) {
        mForecast1TempLowF = forecast1TempLowF;
        mForecast1TempLowC = this.turnFtoC(forecast1TempLowF);
    }
    public String getForecast1ConditionIconURL() {
        return mForecast1ConditionIconURL;
    }
    public int getForecast1TempHighC() {
        return mForecast1TempHighC;
    }

    public int getForecast1TempLowC() {
        return mForecast1TempLowC;
    }
    /************************************************
     * First Forecast - End
     *
     *
     ************************************************/



    /************************************************
     * Second Forecast - Start
     *
     *
     ************************************************/
    public String getForecast2Date() {
        return mForecast2Date;
    }

    void setForecast2Date(String forecast2Date) {
        mForecast2Date = forecast2Date;
    }
    public String getForecast2Day() {
        return mForecast2Day;
    }

    void setForecast2Day(String forecast2Day) {
        mForecast2Day = forecast2Day;
    }

    public int getForecast2Code() {
        return mForecast2Code;
    }

    void setForecast2Code(int forecast2Code) {
        mForecast2Code = forecast2Code;
        mForecast2ConditionIconURL = "http://l.yimg.com/a/i/us/we/52/" + forecast2Code + ".gif";
    }
    public String getForecast2ConditionIconURL() {
        return mForecast2ConditionIconURL;
    }

    public String getForecast2Text() {
        return mForecast2Text;
    }

    void setForecast2Text(String forecast2Text) {
        mForecast2Text = forecast2Text;
    }

    public int getForecast2TempHighF() {
        return mForecast2TempHighF;
    }

    void setForecast2TempHighF(int forecast2TempHighF) {
        mForecast2TempHighF = forecast2TempHighF;
        mForecast2TempHighC = this.turnFtoC(forecast2TempHighF);
    }

    public int getForecast2TempLowF() {
        return mForecast2TempLowF;
    }

    void setForecast2TempLowF(int forecast2TempLowF) {
        mForecast2TempLowF = forecast2TempLowF;
        mForecast2TempLowC = this.turnFtoC(forecast2TempLowF);
    }

    public int getForecast2TempHighC() {
        return mForecast2TempHighC;
    }

    public int getForecast2TempLowC() {
        return mForecast2TempLowC;
    }

    /************************************************
     * Second Forecast - End
     *
     *
     ************************************************/

    /************************************************
     * Third Forecast - Start
     *
     *
     ************************************************/
    public String getForecast3Date() {
        return mForecast3Date;
    }

    void setForecast3Date(String Forecast3Date) {
        mForecast3Date = Forecast3Date;
    }
    public String getForecast3Day() {
        return mForecast3Day;
    }

    void setForecast3Day(String Forecast3Day) {
        mForecast3Day = Forecast3Day;
    }

    public int getForecast3Code() {
        return mForecast3Code;
    }

    void setForecast3Code(int Forecast3Code) {
        mForecast3Code = Forecast3Code;
        mForecast3ConditionIconURL = "http://l.yimg.com/a/i/us/we/52/" + Forecast3Code + ".gif";
    }

    public String getForecast3Text() {
        return mForecast3Text;
    }

    void setForecast3Text(String Forecast3Text) {
        mForecast3Text = Forecast3Text;
    }

    public int getForecast3TempHighF() {
        return mForecast3TempHighF;
    }
    void setForecast3TempHighF(int Forecast3TempHighF) {
        mForecast3TempHighF = Forecast3TempHighF;
        mForecast3TempHighC = this.turnFtoC(Forecast3TempHighF);
    }

    public int getForecast3TempLowF() {
        return mForecast3TempLowF;
    }

    void setForecast3TempLowF(int Forecast3TempLowF) {
        mForecast3TempLowF = Forecast3TempLowF;
        mForecast3TempLowC = this.turnFtoC(Forecast3TempLowF);
    }
    public String getForecast3ConditionIconURL() {
        return mForecast3ConditionIconURL;
    }
    public int getForecast3TempHighC() {
        return mForecast3TempHighC;
    }

    public int getForecast3TempLowC() {
        return mForecast3TempLowC;
    }
    /************************************************
     * Third Forecast - End
     *
     *
     ************************************************/
    
    /************************************************
     * Fourth Forecast - Start
     *
     *
     ************************************************/
    public String getForecast4Date() {
        return mForecast4Date;
    }

    void setForecast4Date(String Forecast4Date) {
        mForecast4Date = Forecast4Date;
    }
    public String getForecast4Day() {
        return mForecast4Day;
    }

    void setForecast4Day(String Forecast4Day) {
        mForecast4Day = Forecast4Day;
    }

    public int getForecast4Code() {
        return mForecast4Code;
    }

    void setForecast4Code(int Forecast4Code) {
        mForecast4Code = Forecast4Code;
        mForecast4ConditionIconURL = "http://l.yimg.com/a/i/us/we/52/" + Forecast4Code + ".gif";
    }

    public String getForecast4Text() {
        return mForecast4Text;
    }

    void setForecast4Text(String Forecast4Text) {
        mForecast4Text = Forecast4Text;
    }

    public int getForecast4TempHighF() {
        return mForecast4TempHighF;
    }
    void setForecast4TempHighF(int Forecast4TempHighF) {
        mForecast4TempHighF = Forecast4TempHighF;
        mForecast4TempHighC = this.turnFtoC(Forecast4TempHighF);
    }

    public int getForecast4TempLowF() {
        return mForecast4TempLowF;
    }

    void setForecast4TempLowF(int Forecast4TempLowF) {
        mForecast4TempLowF = Forecast4TempLowF;
        mForecast4TempLowC = this.turnFtoC(Forecast4TempLowF);
    }
    public String getForecast4ConditionIconURL() {
        return mForecast4ConditionIconURL;
    }
    public int getForecast4TempHighC() {
        return mForecast4TempHighC;
    }

    public int getForecast4TempLowC() {
        return mForecast4TempLowC;
    }
    /************************************************
     * Fourth Forecast - End
     *
     *
     ************************************************/

    /************************************************
     * Fifth Forecast - Start
     *
     *
     ************************************************/
    public String getForecast5Date() {
        return mForecast5Date;
    }

    void setForecast5Date(String Forecast5Date) {
        mForecast5Date = Forecast5Date;
    }
    public String getForecast5Day() {
        return mForecast5Day;
    }

    void setForecast5Day(String Forecast5Day) {
        mForecast5Day = Forecast5Day;
    }

    public int getForecast5Code() {
        return mForecast5Code;
    }

    void setForecast5Code(int Forecast5Code) {
        mForecast5Code = Forecast5Code;
        mForecast5ConditionIconURL = "http://l.yimg.com/a/i/us/we/52/" + Forecast5Code + ".gif";
    }

    public String getForecast5Text() {
        return mForecast5Text;
    }

    void setForecast5Text(String Forecast5Text) {
        mForecast5Text = Forecast5Text;
    }

    public int getForecast5TempHighF() {
        return mForecast5TempHighF;
    }
    void setForecast5TempHighF(int Forecast5TempHighF) {
        mForecast5TempHighF = Forecast5TempHighF;
        mForecast5TempHighC = this.turnFtoC(Forecast5TempHighF);
    }

    public int getForecast5TempLowF() {
        return mForecast5TempLowF;
    }

    void setForecast5TempLowF(int Forecast5TempLowF) {
        mForecast5TempLowF = Forecast5TempLowF;
        mForecast5TempLowC = this.turnFtoC(Forecast5TempLowF);
    }
    public String getForecast5ConditionIconURL() {
        return mForecast5ConditionIconURL;
    }
    public int getForecast5TempHighC() {
        return mForecast5TempHighC;
    }

    public int getForecast5TempLowC() {
        return mForecast5TempLowC;
    }
    /************************************************
     * Fifth Forecast - End
     *
     *
     ************************************************/

}
