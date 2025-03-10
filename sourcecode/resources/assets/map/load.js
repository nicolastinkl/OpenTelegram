(function () {
    window.BMap_loadScriptTime = (new Date).getTime();
    document.write('<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&ak=' + ak + '&services=&t=' + window.BMap_loadScriptTime + '" ></script>');
})();