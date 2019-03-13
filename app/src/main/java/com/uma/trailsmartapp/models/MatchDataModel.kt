package com.uma.trailsmartapp.models

import com.uma.trailsmartapp.models.BattingDataModel
import com.uma.trailsmartapp.models.BowlingDataModel

/**
 * Created by Umapathi on 05/03/19.
 * Copyright Indyzen Inc, @2019
 */
data class MatchDataModel(
    var umpire: String,
    var venue: String,
    var teamA: String,
    var teamB: String,
    var overs:String,
    var toss: String,
    var decision: String

)