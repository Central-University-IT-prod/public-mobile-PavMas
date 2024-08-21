package com.trifcdr.lifestylehub.domain.models.venue


/**
 * Created by trifcdr.
 */

data class VenueDetailsDataDom(
   val id: String,
   val name: String,
   val address: String,
   val url: String,
   val bestPhoto: String,
   val categories: List<String>,
   val phone: String,
   val hoursStatus: String,
   val photos: List<String>,
   val reasons: List<String>,
) {
}