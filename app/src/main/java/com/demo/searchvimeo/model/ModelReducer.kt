package com.demo.searchvimeo.model

import android.util.Log
import java.util.regex.Pattern

/**
 * @description:
 * @author: forjrking
 * @date: 2021/10/10 5:15 下午
 */
class ModelReducer {

    //<div class="iris_video-vital iris_video-vital--browse">
//    <a class="iris_video-vital__overlay iris_link-box iris_annotation-box iris_chip-box" href="https://vimeo.com/201281043">


    /**
     * <div class="iris_p_infinite__item span-1"><div class="iris_video-vital iris_video-vital--browse"><a class="iris_video-vital__overlay iris_link-box iris_annotation-box iris_chip-box" href="https://vimeo.com/201281043"><div class="iris_thumbnail iris_thumbnail iris_thumbnail--16-9"><div><img src="https://i.vimeocdn.com/video/615027115-5cafa3c77afff366d7ce23b9d99ff939a74dea4b0342f35e991a0c7057912c03-d_590x332" srcset="" class="" data-pin-nopin="false" alt="Promo for Luo Bao Bei"></div><div class="iris_video-vital__chips"></div><div class="iris_video-vital__chips--right"><div class="iris_chip iris_chip-icon iris_chip--watch  iris_chip--browse iris_chip--right" title="Add to Watch Later" data-custom="{&quot;clip_id&quot;:201281043,&quot;user_id&quot;:null,&quot;active&quot;:false}"><svg viewBox="0 0 24 24" class="iris_ic is--16"><path d="M12 0C5.4 0 0 5.4 0 12s5.4 12 12 12 12-5.4 12-12S18.6 0 12 0m0 21c-5 0-9-4-9-9s4-9 9-9 9 4 9 9-4 9-9 9m-1.5-9.4l.7-5.6h1.5l.7 5.4 3.3 4.3-1.1 1.1-4.5-3.5c-.1-.1-.2-.1-.3-.2-.2-.4-.4-1-.3-1.5z"></path></svg></div></div><div class="iris-annotation-layer iris_annotation iris_annotation--videovitals iris_annotation--sm"><div class="iris_annotation__content"><div class="iris_annotation__timecode"><span title=" 5 years ago" class=""> 5 years ago</span></div><span class="iris_annotation__quickstats iris_quickstats iris_quickstats--sm"><span class="iris_quickstats__item"><svg viewBox="0 0 16 16" class="iris_quickstats__icon iris_ic is--12"><path d="M12 2C9.75 2 8 4 8 4S6.25 2 4 2 0 3.75 0 6c0 4.25 5 4.016 8 8 3-3.984 8-3.75 8-8 0-2.25-1.75-4-4-4z"></path></svg><span title="57" class="">57</span></span><span class="iris_quickstats__item"><svg viewBox="0 0 16 16" class="iris_quickstats__icon iris_ic is--12"><path d="M12 1H4a4 4 0 00-4 4v2a4 4 0 004 4h1v4l4-4h3a4 4 0 004-4V5a4 4 0 00-4-4z"></path></svg><span title="0" class="">0</span></span></span><span title="01:16" class="iris_annotation__duration">01:16</span></div></div></div><div class="iris_video-vital__title"><h5 class="l-ellipsis"><span title="Promo for Luo Bao Bei" class="iris_link iris_link--gray-2">Promo for Luo Bao Bei</span></h5><div class="iris_video-vital__title-attributes"><!-- react-empty: 82 --><!-- react-empty: 83 --></div></div></a><div class="iris_attribution_panel iris_attribution_panel--browse l-text-left"><div><div><div class="iris_uservital iris_uservital--browse l-float-left"><a class="iris_portrait is-circle iris_portrait--xxsm iris_video-vital__portrait" href="https://vimeo.com/clothcatanimation"><img src="https://i.vimeocdn.com/portrait/9857353_30x30" alt="Cloth Cat Animation"></a><div class="iris_uservital-content"><a href="https://vimeo.com/clothcatanimation" class="iris_userinfo user-vital-item iris_link iris_link--gray-4"><span>Cloth Cat Animation</span></a><!-- react-empty: 93 --></div></div></div></div></div></div></div>
     *
     * */

    val row = "<div class=\"iris_p_infinite__item span-1\">.*?</div>"
    val reg =
        "<a class=\"iris_video-vital__overlay iris_link-box iris_annotation-box iris_chip-box\".*?</a>"
    val regImg = "src=\".*?\""

    fun reducer(data: String): List<String> {
        val matcher = Pattern.compile(row).matcher(data)
        return mutableListOf<String>().apply {
            while (matcher.find()) {
                val group = matcher.group()
                Log.d("matcher", group)
                val matcherSrc = Pattern.compile(regImg).matcher(group)
                while (matcher.find()) {
                    this.add(matcher.group())
                }
            }
        }
    }

}