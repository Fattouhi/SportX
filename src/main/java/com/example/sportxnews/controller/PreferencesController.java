package com.example.sportxnews.controller;
import com.example.sportxnews.models.UserPreferences;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
public class PreferencesController {
    @FXML
    private CheckBox footballCheckBox;
    @FXML
    private CheckBox basketballCheckBox;
    @FXML
    private CheckBox tennisCheckBox;
    @FXML
    private CheckBox handballCheckBox;
    @FXML
    private CheckBox swimmingcheckbox;
    @FXML
    private CheckBox volleyballCheckBox;
    @FXML
    private CheckBox baseballCheckBox;
    @FXML
    private CheckBox boxingCheckBox;
    @FXML
    private CheckBox gymnasticsCheckBox;
    @FXML
    private CheckBox golfCheckBox;
    @FXML
    private CheckBox wrestlingCheckBox;
    @FXML
    private CheckBox cricketCheckBox;
    @FXML
    private CheckBox rugbyCheckBox;
    @FXML
    private CheckBox tableTennisCheckBox;
    @FXML
    private CheckBox badmintonCheckBox;
    @FXML
    private CheckBox judocheckbox;
    @FXML
    private CheckBox karateCheckBox;
    @FXML
    private CheckBox taekwondoCheckBox;
    @FXML
    private CheckBox mixedMartialArtsCheckBox;
    @FXML
    private CheckBox waterPoloCheckBox;
    @FXML
    private CheckBox divingCheckBox;
    @FXML
    private CheckBox rowingCheckBox;
    @FXML
    private CheckBox surfingCheckBox;
    @FXML
    private CheckBox sailingCheckBox;
    @FXML
    private CheckBox kayakingCheckBox;
    @FXML
    private CheckBox skiingCheckBox;
    @FXML
    private CheckBox snowboardingcheckbox;
    @FXML
    private CheckBox iceHockeyCheckBox;
    @FXML
    private CheckBox figureSkatingCheckBox;
    @FXML
    private CheckBox speedSkatingCheckBox;
    @FXML
    private CheckBox curlingCheckBox;
    @FXML
    private CheckBox formulaOneCheckBox;
    @FXML
    private CheckBox motoGPCheckBox;
    @FXML
    private CheckBox rallyCheckBox;
    @FXML
    private CheckBox nascarCheckBox;
    @FXML
    private CheckBox athleticsCheckBox;
    @FXML
    private CheckBox cyclingCheckBox;
    @FXML
    private CheckBox archerycheckbox;
    @FXML
    private CheckBox equestrianCheckBox;
    @FXML
    private CheckBox fencingCheckBox;
    @FXML
    private CheckBox weightliftingCheckBox;
    @FXML
    private CheckBox shootingCheckBox;
    @FXML
    private CheckBox skateboardingCheckBox;
    @FXML
    private CheckBox climbingCheckBox;
    @FXML
    private CheckBox triathlonCheckBox;
    private UserPreferences userPreferences;
    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
        // Initialize checkboxes with current preferences
        footballCheckBox.setSelected(userPreferences.isFootballSelected());
        basketballCheckBox.setSelected(userPreferences.isBasketballSelected());
        tennisCheckBox.setSelected(userPreferences.isTennisSelected());
        volleyballCheckBox.setSelected(userPreferences.isVolleyballSelected());
        baseballCheckBox.setSelected(userPreferences.isBaseballSelected());
        cricketCheckBox.setSelected(userPreferences.isCricketSelected());
        rugbyCheckBox.setSelected(userPreferences.isRugbySelected());
        golfCheckBox.setSelected(userPreferences.isGolfSelected());
        tableTennisCheckBox.setSelected(userPreferences.isTableTennisSelected());
        badmintonCheckBox.setSelected(userPreferences.isBadmintonSelected());
        handballCheckBox.setSelected(userPreferences.isHandballSelected());
// Combat Sports
        boxingCheckBox.setSelected(userPreferences.isBoxingSelected());
        wrestlingCheckBox.setSelected(userPreferences.isWrestlingSelected());
        judocheckbox.setSelected(userPreferences.isJudoSelected());
        karateCheckBox.setSelected(userPreferences.isKarateSelected());
        taekwondoCheckBox.setSelected(userPreferences.isTaekwondoSelected());

        mixedMartialArtsCheckBox.setSelected(userPreferences.isMixedMartialArtsSelected());
// Water Sports
        swimmingcheckbox.setSelected(userPreferences.isSwimmingSelected());
        waterPoloCheckBox.setSelected(userPreferences.isWaterPoloSelected());
        divingCheckBox.setSelected(userPreferences.isDivingSelected());
        rowingCheckBox.setSelected(userPreferences.isRowingSelected());
        surfingCheckBox.setSelected(userPreferences.isSurfingSelected());
        sailingCheckBox.setSelected(userPreferences.isSailingSelected());
        kayakingCheckBox.setSelected(userPreferences.isKayakingSelected());
// Winter Sports
        skiingCheckBox.setSelected(userPreferences.isSkiingSelected());
        snowboardingcheckbox.setSelected(userPreferences.isSnowboardingSelected());
        iceHockeyCheckBox.setSelected(userPreferences.isiceHockeySelected());
        figureSkatingCheckBox.setSelected(userPreferences.isFigureSkatingSelected());
        speedSkatingCheckBox.setSelected(userPreferences.isSpeedSkatingSelected());
        curlingCheckBox.setSelected(userPreferences.isCurlingSelected());
// Motor Sports
        formulaOneCheckBox.setSelected(userPreferences.isFormulaOneSelected());
        motoGPCheckBox.setSelected(userPreferences.isMotoGPSelected());
        rallyCheckBox.setSelected(userPreferences.isRallySelected());
        nascarCheckBox.setSelected(userPreferences.isNascarSelected());
// Other Sports
        athleticsCheckBox.setSelected(userPreferences.isAthleticsSelected());
        cyclingCheckBox.setSelected(userPreferences.isCyclingSelected());
        gymnasticsCheckBox.setSelected(userPreferences.isGymnasticsSelected());
        archerycheckbox.setSelected(userPreferences.isArcherySelected());
        equestrianCheckBox.setSelected(userPreferences.isEquestrianSelected());
        fencingCheckBox.setSelected(userPreferences.isFencingSelected());
        weightliftingCheckBox.setSelected(userPreferences.isWeightliftingSelected());
        shootingCheckBox.setSelected(userPreferences.isShootingSelected());
        skateboardingCheckBox.setSelected(userPreferences.isSkateboardingSelected());
        climbingCheckBox.setSelected(userPreferences.isClimbingSelected());
        triathlonCheckBox.setSelected(userPreferences.isTriathlonSelected());
    }
    @FXML
    private void savePreferences() {
        userPreferences.setFootballSelected(footballCheckBox.isSelected());
        userPreferences.setBasketballSelected(basketballCheckBox.isSelected());
        userPreferences.setTennisSelected(tennisCheckBox.isSelected());
        userPreferences.setVolleyballSelected(volleyballCheckBox.isSelected());
        userPreferences.setBaseballSelected(baseballCheckBox.isSelected());
        userPreferences.setCricketSelected(cricketCheckBox.isSelected());
        userPreferences.setRugbySelected(rugbyCheckBox.isSelected());
        userPreferences.setGolfSelected(golfCheckBox.isSelected());
        userPreferences.setTableTennisSelected(tableTennisCheckBox.isSelected());
        userPreferences.setBadmintonSelected(badmintonCheckBox.isSelected());
        userPreferences.setHandballSelected(handballCheckBox.isSelected());
// Combat Sports
        userPreferences.setBoxingSelected(boxingCheckBox.isSelected());
        userPreferences.setWrestlingSelected(wrestlingCheckBox.isSelected());
        userPreferences.setJudoSelected(judocheckbox.isSelected());
        userPreferences.setKarateSelected(karateCheckBox.isSelected());
        userPreferences.setTaekwondoSelected(taekwondoCheckBox.isSelected());

        userPreferences.setMixedMartialArtsSelected(mixedMartialArtsCheckBox.isSelected());
// Water Sports
        userPreferences.setSwimmingSelected(swimmingcheckbox.isSelected());
        userPreferences.setWaterPoloSelected(waterPoloCheckBox.isSelected());
        userPreferences.setDivingSelected(divingCheckBox.isSelected());
        userPreferences.setRowingSelected(rowingCheckBox.isSelected());
        userPreferences.setSurfingSelected(surfingCheckBox.isSelected());
        userPreferences.setSailingSelected(sailingCheckBox.isSelected());
        userPreferences.setKayakingSelected(kayakingCheckBox.isSelected());
// Winter Sports
        userPreferences.setSkiingSelected(skiingCheckBox.isSelected());
        userPreferences.setSnowboardingSelected(snowboardingcheckbox.isSelected());
        userPreferences.seticeHockeySelected(iceHockeyCheckBox.isSelected());
        userPreferences.setFigureSkatingSelected(figureSkatingCheckBox.isSelected());
        userPreferences.setSpeedSkatingSelected(speedSkatingCheckBox.isSelected());
        userPreferences.setCurlingSelected(curlingCheckBox.isSelected());
// Motor Sports
        userPreferences.setFormulaOneSelected(formulaOneCheckBox.isSelected());
        userPreferences.setMotoGPSelected(motoGPCheckBox.isSelected());
        userPreferences.setRallySelected(rallyCheckBox.isSelected());
        userPreferences.setNascarSelected(nascarCheckBox.isSelected());
// Other Sports
        userPreferences.setAthleticsSelected(athleticsCheckBox.isSelected());
        userPreferences.setCyclingSelected(cyclingCheckBox.isSelected());
        userPreferences.setGymnasticsSelected(gymnasticsCheckBox.isSelected());
        userPreferences.setArcherySelected(archerycheckbox.isSelected());
        userPreferences.setEquestrianSelected(equestrianCheckBox.isSelected());
        userPreferences.setFencingSelected(fencingCheckBox.isSelected());
        userPreferences.setWeightliftingSelected(weightliftingCheckBox.isSelected());
        userPreferences.setShootingSelected(shootingCheckBox.isSelected());
        userPreferences.setSkateboardingSelected(skateboardingCheckBox.isSelected());
        userPreferences.setClimbingSelected(climbingCheckBox.isSelected());
        userPreferences.setTriathlonSelected(triathlonCheckBox.isSelected());
        // Close the preferences window
        footballCheckBox.getScene().getWindow().hide();
    }
}