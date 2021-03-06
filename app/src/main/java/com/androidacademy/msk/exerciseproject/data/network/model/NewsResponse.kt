package com.androidacademy.msk.exerciseproject.data.network.model

import com.google.gson.annotations.SerializedName

@Suppress("unused")
class NewsResponse {

    /**
     * status : OK
     * section : home
     * num_results : 48
     * results : [{"section":"New York","subsection":"","title":"Pipe Bombs Sent to Hillary Clinton, Barack Obama and CNN Offices","abstract":"The similar devices sparked an intense investigation into whether a bomber is going after people who have often faced barbed right-wing criticism.","url":"https://www.nytimes.com/2018/10/24/nyregion/clinton-obama-explosive-device.html","byline":"By WILLIAM K. RASHBAUM","item_type":"Article","updated_date":"2018-10-24T17:45:40-04:00","created_date":"2018-10-24T09:10:56-04:00","published_date":"2018-10-24T09:10:56-04:00","material_type_facet":"","kicker":"","des_facet":["Bombs and Explosives"],"org_facet":[],"per_facet":["Clinton, Hillary Rodham","Soros, George","Clinton, Bill","Obama, Barack"],"geo_facet":[],"multimedia":[{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"},{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-thumbLarge.jpg","format":"thumbLarge","height":150,"width":150,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"},{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-articleInline.jpg","format":"Normal","height":127,"width":190,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"},{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-mediumThreeByTwo210.jpg","format":"mediumThreeByTwo210","height":140,"width":210,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"},{"url":"https://static01.nyt.com/images/2018/10/24/nyregion/25CLINTON3-promo/25CLINTON3-promo-superJumbo.jpg","format":"superJumbo","height":1366,"width":2048,"type":"image","subtype":"photo","caption":"CNN\u2019s offices in Manhattan were evacuated after an explosive device was sent there, a law enforcement official said.","copyright":"Jeenah Moon for The New York Times"}],"short_url":"https://nyti.ms/2JdJI2N"}]
     */
    @SerializedName("status")
    val status: String? = null

    @SerializedName("section")
    val section: String? = null

    @SerializedName("num_results")
    val numResults: Int = 0

    @SerializedName("results")
    val results: List<NetworkNewsItem> = emptyList()
}
