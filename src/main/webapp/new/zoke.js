/**
 * Created by zf on 03/01/17.
 */
var zoke = {
    url: "http://localhost:8080/redRain/",

    getUrlElement: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    }

}
