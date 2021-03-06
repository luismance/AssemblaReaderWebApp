/**
 * angular-swx-session-storage - $sessionStorage service for use in your AngularJS applications.
 * @author Paul Massey, paul.massey@scriptwerx.io
 * @version v1.0.2
 * @build 31 - Mon Feb 29 2016 12:06:44 GMT+0000 (GMT)
 * @link http://www.scriptwerx.io
 * @license MIT
 */
!function(e){"use strict";function t(t,o,n){var r,s=o.host().substring(0,o.host().indexOf("."))+"_",i=6e4,a=!0,c=n("session-cache"),u=this;u.prefix=function(e){s=e+"_",c.destroy(),c=n(s+"cache")},u.put=function(t,o){var n={data:o};return arguments.length>2&&e.isNumber(arguments[2])&&(n.expires=(new Date).getTime()+arguments[2]*i),c.put(t,n),a&&r.setItem(s+t,e.toJson(n,!1)),o},u.get=function(t){var o;return c.get(t)?o=c.get(t):a&&(o=e.fromJson(r.getItem(s+t))),o?o.expires&&o.expires<(new Date).getTime()?void u.remove(t):(c.put(t,o),o.data):void 0},u.remove=function(e){u.put(e,void 0),a&&r.removeItem(s+e),c.remove(e)},u.empty=function(){a&&r.clear(),c.removeAll()},function(){try{r=t.sessionStorage;var e="swxTest_"+Math.round(1e7*Math.random());r.setItem(e,"test"),r.removeItem(e)}catch(o){a=!1}}()}t.$inject=["$window","$location","$cacheFactory"],e.module("swxSessionStorage",[]).service("$sessionStorage",t)}(window.angular);
//# sourceMappingURL=swx-session-storage.min.js.map