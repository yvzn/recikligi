(function () {
    'use strict';

    window.onload = afterDocumentLoaded;

    function afterDocumentLoaded() {
        hideSubmitButtonIfJavascriptAvailable();
        autoSubmitFormIfImageSelected();
    }

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

    function autoSubmitFormIfImageSelected() {
        var inputs = document.querySelectorAll('input[type="file"]')
        for (var i = 0; i < inputs.length; ++i) {
            inputs[i].onchange = submitForm;
        }
    }

    function submitForm() {
        var forms = document.getElementsByTagName('form');
        if (forms.length) {
            forms[0].submit();
        }
    }
})();
