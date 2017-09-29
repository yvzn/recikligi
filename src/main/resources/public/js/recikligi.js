(function () {
    'use strict';

    window.addEventListener("load", hideSubmitButtonIfJavascriptAvailable);
    window.addEventListener("load", activateLoaderOnFormSubmit);
    window.addEventListener("load", submitFormIfImageSelected);

    function hideSubmitButtonIfJavascriptAvailable() {
        try {
            var submitButtons = document.querySelectorAll('form input[type="submit"]');
            for (var i = 0, len = submitButtons.length; i < len; ++i) {
                submitButtons[i].style.left = '100vw';
            }
        } catch (e) {
            console.log(e);
        }
    }

    function submitFormIfImageSelected() {
        var inputs = document.querySelectorAll('input[type="file"]')
        for (var i = 0; i < inputs.length; ++i) {
            inputs[i].addEventListener("change", submitFirstForm);
        }
    }

    function submitFirstForm() {
        var forms = document.getElementsByTagName('form');
        if (forms.length) {
            showLoader()
            forms[0].submit();
        }
    }

    function activateLoaderOnFormSubmit() {
        var forms = document.getElementsByTagName('form');
        for(var i = 0; i < forms.length; i++) {
            forms[i].addEventListener("submit", showLoader);
        }
    }

    function showLoader() {
        var loader = document.querySelector('.loader');
        if (loader) {
            loader.style.display = 'flex';
        }
    }
})();
