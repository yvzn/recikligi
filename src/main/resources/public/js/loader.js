var loader = (function () {
    'use strict';

    function showLoader() {
        setLoaderDisplay('flex');
    }

    function hideLoader() {
        setLoaderDisplay('none');
    }

    function setLoaderDisplay(display) {
        var loader = document.querySelector('.loader');
        if (loader) {
            loader.style.display = display;
        }
    }

    return {
        show: showLoader,
        hide: hideLoader
    }
})();
